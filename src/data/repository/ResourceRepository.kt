package data.repository

import domain.model.Resource
import domain.repository.ResourceRepository
import domain.service.ResourceNavigator
import java.sql.Connection

class ResourceRepository(private val connection: Connection) : ResourceRepository {
    override fun findResourceByPath(path: String): Resource? {
        val parts = path.split(".")
        if (!parts.all { it.matches(Regex("^[A-Za-z0-9_]{1,20}$")) }) return null

        val rootName = parts.first()
        val rootResource = getResourceByName(rootName) ?: return null
        return ResourceNavigator.findByPath(rootResource, parts, getAllResources())
    }

    private fun getAllResources(): List<Resource> {
        val resources = mutableListOf<Resource>()
        val stmt = connection.prepareStatement("SELECT name, max_volume, parent_name FROM resources")
        val rs = stmt.executeQuery()
        val resourceMap = mutableMapOf<String, Resource>()

        while (rs.next()) {
            val name = rs.getString("name")
            val maxVolume = rs.getInt("max_volume")
            resourceMap[name] = Resource(name, maxVolume)
        }

        rs.beforeFirst()
        while (rs.next()) {
            val name = rs.getString("name")
            val parentName = rs.getString("parent_name")
            if (parentName != null) {
                val child = resourceMap[name]!!
                val parent = resourceMap[parentName]
                resourceMap[name] = child.copy(parent = parent)
            }
        }

        resources.addAll(resourceMap.values)
        return resources
    }

    private fun getResourceByName(name: String): Resource? {
        val stmt = connection.prepareStatement("SELECT name, max_volume, parent_name FROM resources WHERE name = ?")
        stmt.setString(1, name)
        val rs = stmt.executeQuery()
        if (rs.next()) {
            val maxVolume = rs.getInt("max_volume")
            val parentName = rs.getString("parent_name")
            val parent = parentName?.let { getResourceByName(it) }
            return Resource(name, maxVolume, parent)
        }
        return null
    }
}
