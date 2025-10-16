package domain.service

import domain.model.Resource

object ResourceNavigator {
    fun fullPath(resource: Resource): String {
        return resource.parent?.let { "${fullPath(it)}.${resource.name}" } ?: resource.name
    }

    fun findByPath(resource: Resource, path: List<String>, resources: List<Resource>): Resource? {
        return if (path.first() == resource.name) {
            if (path.size == 1) resource
            else {
                val next = children(resources).find { it.name == path[1] }
                next?.let { findByPath(it, path.drop(1), resources) }
            }
        } else null
    }

    fun children(resources: List<Resource>): List<Resource> = resources.filter { it.parent == this }
}