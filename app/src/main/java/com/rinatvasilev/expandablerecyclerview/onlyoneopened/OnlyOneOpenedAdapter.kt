package com.rinatvasilev.expandablerecyclerview.onlyoneopened

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rinatvasilev.expandablerecyclerview.*

class OnlyOneOpenedAdapter(private val itemList: ArrayList<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentOpenedParent: Parent? = null

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        return itemList[position].getItemType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHILD -> ChildViewHolder(
                parent.inflate(
                    R.layout.item_child, false
                )
            )
            else -> ParentViewHolder(parent.inflate(R.layout.item_parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            CHILD -> {
                val childViewHolder = (holder as ChildViewHolder)
                childViewHolder.childItem = itemList[position] as Child
                childViewHolder.bind()
            }
            else -> {
                val parentViewHolder = holder as ParentViewHolder
                parentViewHolder.parentItem = itemList[position] as Parent
                parentViewHolder.bind()
            }
        }
    }

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val startPosition = adapterPosition + 1
                val count = parentItem.childItems.size

                if (parentItem.isExpanded) {
                    itemList.removeAll(parentItem.childItems)
                    notifyItemRangeRemoved(startPosition, count)
                    parentItem.isExpanded = false
                    currentOpenedParent = null
                } else {
                    itemList.addAll(startPosition, parentItem.childItems)
                    notifyItemRangeInserted(startPosition, count)
                    parentItem.isExpanded = true

                    if (currentOpenedParent != null) {
                        itemList.removeAll(currentOpenedParent!!.childItems)
                        notifyItemRangeRemoved(
                            itemList.indexOf(currentOpenedParent!!) + 1,
                            currentOpenedParent!!.childItems.size
                        )
                        currentOpenedParent?.isExpanded = false
                        notifyItemChanged(itemList.indexOf(currentOpenedParent!!))
                    }

                    currentOpenedParent = parentItem
                }
                updateViewState()
            }
        }

        lateinit var parentItem: Parent

        private val title: TextView = itemView.findViewById(R.id.title)

        fun bind() {
            updateViewState()
        }

        private fun updateViewState() {
            if (parentItem.selectedChild != null) {
                title.text = parentItem.selectedChild?.title
                return
            }

            if (parentItem.isExpanded) {
                title.text = itemView.context.getString(R.string.click_to_collapse)
            } else {
                title.text = itemView.context.getString(R.string.click_to_expand)
            }
        }
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val parentPosition = itemList.indexOf(childItem.parent)
                val startPosition = parentPosition + 1
                val count = childItem.parent.childItems.size

                itemList.removeAll(childItem.parent.childItems)
                notifyItemRangeRemoved(startPosition, count)
                childItem.parent.isExpanded = false

                childItem.parent.selectedChild = childItem

                notifyItemChanged(parentPosition)
            }
        }

        lateinit var childItem: Child

        private val title: TextView = itemView.findViewById(R.id.title)

        fun bind() {
            title.text = childItem.title
        }
    }
}
