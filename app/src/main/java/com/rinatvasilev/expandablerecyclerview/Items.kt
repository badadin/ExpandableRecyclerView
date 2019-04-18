package com.rinatvasilev.expandablerecyclerview

interface Item {
    fun getItemType(): Int
}

const val PARENT = 0
const val CHILD = 1

data class Parent(val id: Long) : Item {
    val childItems = ArrayList<Child>()
    var isExpanded = false
    var selectedChild: Child? = null

    override fun getItemType() = PARENT
}

data class Child(
    val parent: Parent,
    val id: Long,
    val title: String,
    var price: Int = 0) : Item {

    override fun getItemType() = CHILD
}

data class Node(val id: Long, val parent: Node?) {

    //var parent: Node? = null
    var isExpanded = false
    //var nestingLevel = 0
    val childItems = ArrayList<Node>()

    fun isRoot() = parent == null

    //fixme оптимизировать, чтобы посчитать один раз
    fun calculateNestingLevel(): Int {
        var level = 0
        var nextParent = parent
        while (nextParent != null) {
            nextParent = nextParent.parent
            level++
        }
        return level
    }
}
