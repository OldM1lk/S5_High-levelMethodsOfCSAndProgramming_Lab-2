package app.domain.service

import app.domain.model.Resource

object ResourceNavigator {
    fun fullPath(resource: Resource): String {
        return resource.parent?.let { "${fullPath(it)}.${resource.name}" } ?: resource.name
    }

    fun findByPath(resource: Resource, path: List<String>, resources: List<Resource>): Resource? {
        if (path.first() != resource.name) return null

        if (path.size == 1) return resource

        val next = children(resource, resources).find { it.name == path[1] } ?: return null
        return findByPath(next, path.drop(1), resources)
    }

    fun children(parent: Resource, resources: List<Resource>): List<Resource> =
        resources.filter { it.parent == parent }
}