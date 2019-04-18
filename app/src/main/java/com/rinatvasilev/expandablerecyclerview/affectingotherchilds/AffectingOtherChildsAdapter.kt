package com.rinatvasilev.expandablerecyclerview.affectingotherchilds

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rinatvasilev.expandablerecyclerview.*


class AffectingOtherChildsAdapter(
    private val balance: Int,
    private val itemList: ArrayList<Item>,
    private val selectedListener: (sum: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                } else {
                    itemList.addAll(startPosition, parentItem.childItems)
                    notifyItemRangeInserted(startPosition, count)
                    parentItem.isExpanded = true
                }
                updateViewState()
            }
        }

        lateinit var parentItem: Parent

        private val title: TextView = itemView.findViewById(R.id.title)
        private val price: TextView = itemView.findViewById(R.id.price)

        fun bind() {
            updateViewState()
        }

        private fun updateViewState() {
            if (parentItem.selectedChild != null) {
                title.text = parentItem.selectedChild?.title
                price.text = parentItem.selectedChild?.price.toString()
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

                selectedListener(calculateSelected())
            }
        }

        lateinit var childItem: Child

        private val title: TextView = itemView.findViewById(R.id.title)
        private val price: TextView = itemView.findViewById(R.id.price)

        fun bind() {
            title.text = childItem.title
            price.text = childItem.price.toString()

            //todo доделать
            //todo 1. чтобы в рамках одного парента можно было свапать чайлдов, если они <= текущего выбранного по прайсу
            //todo 2. аффектить всех открытых чайлдов, чтобы менялся их стэйт доступности

            val textColor: Int
            if (balance - calculateSelected() <= childItem.price) {
                itemView.isEnabled = false
                textColor = ContextCompat.getColor(itemView.context, R.color.disabled_text)
            } else {
                itemView.isEnabled = true
                textColor = ContextCompat.getColor(itemView.context, R.color.title_text)
            }
            title.setTextColor(textColor)
            price.setTextColor(textColor)
        }

        private fun calculateSelected(): Int {
            var sum = 0
            itemList.forEach { item ->
                if (item.getItemType() == PARENT) {
                    val selectedChild = (item as Parent).selectedChild
                    if (selectedChild != null) {
                        sum += selectedChild.price
                    }
                }
            }
            return sum
        }
    }
}
