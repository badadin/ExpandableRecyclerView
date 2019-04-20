package com.rinatvasilev.expandablerecyclerview.nodes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rinatvasilev.expandablerecyclerview.Node
import com.rinatvasilev.expandablerecyclerview.R

class NodesAdapter(private val dataset: ArrayList<Node>) : RecyclerView.Adapter<NodesAdapter.ViewHolder>() {

    override fun getItemCount() = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.node_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.node = dataset[position]
        holder.bind()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var node: Node
        private val text: TextView = itemView.findViewById(R.id.text)
        private val arrow: ImageView = itemView.findViewById(R.id.arrow)

        init {
            itemView.setOnClickListener {
                if (node.childList.isEmpty()) return@setOnClickListener

                val startPosition = dataset.indexOf(node) + 1

                if (node.isExpanded) {
                    var count = 0
                    val itemsToRemove = arrayListOf<Node>()

                    for (i in startPosition until dataset.size) {
                        if (dataset[i].nestingLevel == node.nestingLevel) break
                        dataset[i].isExpanded = false
                        itemsToRemove.add(dataset[i])
                        count++
                    }

                    dataset.removeAll(itemsToRemove)
                    notifyItemRangeRemoved(startPosition, count)
                    node.isExpanded = false
                } else {
                    dataset.addAll(startPosition, node.childList)
                    val count = node.childList.size
                    notifyItemRangeInserted(startPosition, count)
                    node.isExpanded = true
                }

                updateArrow()
            }
        }

        fun bind() {
            text.text = "Node: ${node.id}, Level: ${node.nestingLevel}"
            text.setPadding(node.nestingLevel * text.context.dpToPx(16), 0, 0, 0)

            updateArrow()

            if (node.childList.isEmpty()) {
                arrow.visibility = View.GONE
            } else {
                arrow.visibility = View.VISIBLE
            }
        }

        private fun updateArrow() {
            if (node.isExpanded) {
                arrow.setImageResource(R.drawable.ic_arrow_drop_up)
            } else {
                arrow.setImageResource(R.drawable.ic_arrow_drop_down)
            }
        }

        private fun Context.dpToPx(dipValue: Int) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue.toFloat(), resources.displayMetrics).toInt()
    }
}
