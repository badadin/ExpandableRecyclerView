package com.rinatvasilev.expandablerecyclerview.nodes

data class Node(val id: Long) {
    val childItems = ArrayList<Node>()
    var isExpanded = false
}
