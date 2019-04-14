package com.rinatvasilev.expandablerecyclerview.simple

interface Item {
    fun getItemType(): Int
}

data class Parent(val id: Long) : Item {
    val childItems = ArrayList<Child>()
    var isExpanded = false

    override fun getItemType() = SimpleRecyclerAdapter.PARENT
}

data class Child(val parent: Parent, val id: Long, val title: String) :
    Item {
    override fun getItemType() = SimpleRecyclerAdapter.CHILD
}
