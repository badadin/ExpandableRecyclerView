package com.rinatvasilev.expandablerecyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val parent1 = Parent(0)
        val childItems1 = ArrayList<Child>()
        childItems1.add(Child(parent1, 0, "test 1"))
        childItems1.add(Child(parent1, 1, "test 2"))
        childItems1.add(Child(parent1, 2, "test 3"))
        childItems1.add(Child(parent1, 3, "test 4"))
        childItems1.add(Child(parent1, 4, "test 5"))
        childItems1.add(Child(parent1, 5, "test 6"))
        childItems1.add(Child(parent1, 6, "test 7"))
        childItems1.add(Child(parent1, 7, "test 8"))
        childItems1.add(Child(parent1, 8, "test 9"))
        parent1.childItems.clear()
        parent1.childItems.addAll(childItems1)

        val parent2 = Parent(1)
        val childItems2 = ArrayList<Child>()
        childItems2.add(Child(parent2, 9, "test 10"))
        childItems2.add(Child(parent2, 10, "test 11"))
        childItems2.add(Child(parent2, 11, "test 12"))
        parent2.childItems.clear()
        parent2.childItems.addAll(childItems2)

        val parent3 = Parent(2)
        val childItems3 = ArrayList<Child>()
        childItems3.add(Child(parent3, 12, "test 11"))
        childItems3.add(Child(parent3, 13, "test 12"))
        childItems3.add(Child(parent3, 14, "test 13"))
        parent3.childItems.clear()
        parent3.childItems.addAll(childItems3)

        val itemList = ArrayList<Item>()
        itemList.add(parent1)
        itemList.add(parent2)
        itemList.add(parent3)

        val list = findViewById<RecyclerView>(R.id.list)
        list.adapter = RecyclerAdapter(itemList)
    }
}
