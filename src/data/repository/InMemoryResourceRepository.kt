package data.repository

import domain.model.Resource
import domain.repository.ResourceRepository

class InMemoryResourceRepository(
    private val resources: List<Resource>
) : ResourceRepository {
    override fun findResourceByPath(path: String): Resource? {
        val parts = path.split(".")
        if (!parts.all { it.matches(Regex("^[A-Za-z0-9_]{1,20}$")) }) return null
        return resources.firstOrNull { it.name == parts.first() }?.findByPath(parts, resources)
    }
}