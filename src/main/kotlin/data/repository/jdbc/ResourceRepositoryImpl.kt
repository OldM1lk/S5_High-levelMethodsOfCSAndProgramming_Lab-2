package app.data.repository.jdbc

import app.domain.model.Resource
import app.domain.repository.ResourceRepository
import app.domain.service.ResourceNavigator
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.ResultSet

@Repository
class ResourceRepositoryImpl(private val connection: Connection) : ResourceRepository {
    override fun findResourceByPath(path: String): Resource? {
        val parts = path.split(".")
        if (!parts.all { it.matches(Regex("^[A-Za-z0-9_]{1,20}$")) }) return null

        val rootName = parts.first()
        val rootResource = getResourceByName(rootName) ?: return null
        return ResourceNavigator.findByPath(rootResource, parts, getAllResources())
    }

    private fun getAllResources(): List<Resource> {
        val sql = "SELECT name, max_volume, parent_name FROM resources"
        val resources = mutableListOf<Resource>()
        val stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
        stmt.use { statement ->
            val rs = statement.executeQuery(sql)
            rs.use { resultSet ->
                val resourceMap = mutableMapOf<String, Resource>()

                while (resultSet.next()) {
                    val name = resultSet.getString("name")
                    val maxVolume = resultSet.getInt("max_volume")
                    resourceMap[name] = Resource(name, maxVolume)
                }

                resultSet.beforeFirst()
                while (resultSet.next()) {
                    val name = resultSet.getString("name")
                    val parentName = resultSet.getString("parent_name")
                    if (parentName != null) {
                        val child = resourceMap[name]!!
                        val parent = resourceMap[parentName]
                        resourceMap[name] = child.copy(parent = parent)
                    }
                }

                resources.addAll(resourceMap.values)
            }
        }
        return resources
    }

    private fun getResourceByName(name: String): Resource? {
        val sql = "SELECT name, max_volume, parent_name FROM resources WHERE name = ?"
        val stmt = connection.prepareStatement(sql)
        stmt.use { statement ->
            statement.setString(1, name)
            val rs = statement.executeQuery()
            rs.use { resultSet ->
                if (resultSet.next()) {
                    val maxVolume = resultSet.getInt("max_volume")
                    val parentName = resultSet.getString("parent_name")
                    val parent = parentName?.let { getResourceByName(it) }
                    return Resource(name, maxVolume, parent)
                }
                return null
            }
        }
    }
}
