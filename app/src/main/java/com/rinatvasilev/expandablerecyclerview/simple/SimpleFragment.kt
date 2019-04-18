package com.rinatvasilev.expandablerecyclerview.simple

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rinatvasilev.expandablerecyclerview.Child
import com.rinatvasilev.expandablerecyclerview.Item
import com.rinatvasilev.expandablerecyclerview.Parent
import com.rinatvasilev.expandablerecyclerview.R

class SimpleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        //some predefined values:
        val parent1 = Parent(0)
        val childItems1 = ArrayList<Child>()
        childItems1.add(Child(parent1, 0, "test 1"))
        childItems1.add(Child(parent1, 1, "test 2"))
        childItems1.add(Child(parent1, 2, "test 3"))
        childItems1.add(Child(parent1, 3, "test 4"))
        parent1.childItems.clear()
        parent1.childItems.addAll(childItems1)

        val parent2 = Parent(1)
        val childItems2 = ArrayList<Child>()
        childItems2.add(Child(parent2, 9, "test 5"))
        childItems2.add(Child(parent2, 10, "test 6"))
        childItems2.add(Child(parent2, 11, "test 7"))
        parent2.childItems.clear()
        parent2.childItems.addAll(childItems2)

        val parent3 = Parent(2)
        val childItems3 = ArrayList<Child>()
        childItems3.add(Child(parent3, 12, "test 8"))
        childItems3.add(Child(parent3, 13, "test 9"))
        childItems3.add(Child(parent3, 14, "test 10"))
        parent3.childItems.clear()
        parent3.childItems.addAll(childItems3)

        val itemList = ArrayList<Item>()
        itemList.add(parent1)
        itemList.add(parent2)
        itemList.add(parent3)

        val list = rootView.findViewById<RecyclerView>(R.id.list)
        list.adapter = SimpleAdapter(itemList)

        return rootView
    }

    companion object {
        fun getInstance() = SimpleFragment()
    }
}
