package data.source

import domain.model.Resource

val resources: List<Resource> by lazy {
    val resourceA = Resource("A", 10)
    val resourceB = Resource("B", 50, parent = resourceA)
    val resourceC = Resource("C", 45, parent = resourceB)
    val resourceD = Resource("D", 15, parent = resourceC)
    val resourceX = Resource("X", 100, parent = resourceA)
    val resourceY = Resource("Y", 200, parent = resourceX)

    listOf(resourceA, resourceB, resourceC, resourceD, resourceX, resourceY)
}