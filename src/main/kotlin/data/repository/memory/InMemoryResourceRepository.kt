package app.data.repository.memory

import app.domain.model.Resource
import app.domain.repository.ResourceRepository
import app.domain.service.ResourceNavigator

class InMemoryResourceRepository() : ResourceRepository {
    private val resources: List<Resource> by lazy {
        val resourceA = Resource("A", 10)
        val resourceB = Resource("B", 50, parent = resourceA)
        val resourceC = Resource("C", 45, parent = resourceB)
        val resourceD = Resource("D", 15, parent = resourceC)
        val resourceX = Resource("X", 100, parent = resourceA)
        val resourceY = Resource("Y", 200, parent = resourceX)

        listOf(resourceA, resourceB, resourceC, resourceD, resourceX, resourceY)
    }

    override fun findResourceByPath(path: String): Resource? {
        val parts = path.split(".")
        if (!parts.all { it.matches(Regex("^[A-Za-z0-9_]{1,20}$")) }) return null
        val resource = resources.firstOrNull { it.name == parts.first() } ?: return null
        return ResourceNavigator.findByPath(resource, parts, resources)
    }
}