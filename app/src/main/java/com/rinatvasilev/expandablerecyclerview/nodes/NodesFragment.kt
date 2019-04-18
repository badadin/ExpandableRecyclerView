package com.rinatvasilev.expandablerecyclerview.nodes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rinatvasilev.expandablerecyclerview.Node
import com.rinatvasilev.expandablerecyclerview.R

class NodesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        val list = rootView.findViewById<RecyclerView>(R.id.list)
        list.adapter = NodesAdapter(getPredefinedData())

        return rootView
    }

    private fun getPredefinedData(): ArrayList<Node> {
        val n1 = Node(1, null)
        val n2 = Node(2, null)
        val n3 = Node(3, null)

        val n4 = Node(4, n1)
        val n5 = Node(5, n1)
        val n6 = Node(6, n1)

        val n7 = Node(7, n4)
        val n8 = Node(8, n4)

        val n9 = Node(9, n7)

        n1.childList.add(n4)
        n1.childList.add(n5)
        n1.childList.add(n6)

        n4.childList.add(n7)
        n4.childList.add(n8)

        n7.childList.add(n9)

        return arrayListOf(n1, n2, n3)
    }

    companion object {
        fun getInstance() = NodesFragment()
    }
}
