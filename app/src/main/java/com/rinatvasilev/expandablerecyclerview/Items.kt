package com.rinatvasilev.expandablerecyclerview

interface Item {
    fun getItemType(): Int
}

data class Parent(val id: Long) : Item {
    val childItems = ArrayList<Child>()
    var isExpanded = false

    override fun getItemType() = RecyclerAdapter.PARENT
}

data class Child(val parent: Parent, val id: Long, val title: String) : Item {
    override fun getItemType() = RecyclerAdapter.CHILD
}
