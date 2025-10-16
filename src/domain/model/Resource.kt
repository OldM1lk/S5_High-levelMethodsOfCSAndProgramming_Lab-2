package domain.model

data class Resource(
    val name: String,
    val maxVolume: Int,
    val parent: Resource? = null
) {
    fun fullPath(): String {
        return parent?.let { "${it.fullPath()}.$name" } ?: name
    }

    fun findByPath(path: List<String>, resources: List<Resource>): Resource? {
        return if (path.first() == name) {
            if (path.size == 1) this
            else {
                val next = children(resources).find { it.name == path[1] }
                next?.findByPath(path.drop(1), resources)
            }
        } else null
    }

    fun children(resources: List<Resource>): List<Resource> = resources.filter { it.parent == this }
}
