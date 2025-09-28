data class Resource(
    val name: String,
    val maxVolume: Int,
    val parent: Resource? = null
) {
    fun fullPath(): String {
        return parent?.let { "${it.fullPath()}.$name" } ?: name
    }
    fun findByPath(path: List<String>): Resource? {
        return if (path.first() == name) {
            if (path.size == 1) this
            else {
                val next = children().find { it.name == path[1] }
                next?.findByPath(path.drop(1))
            }
        } else null
    }
    fun children(): List<Resource> = resources.filter { it.parent == this }
}

val resources: List<Resource> by lazy {
    val resourceA = Resource("A", 10)
    val resourceB = Resource("B", 50, parent = resourceA)
    val resourceC = Resource("C", 45, parent = resourceB)
    val resourceD = Resource("D", 15, parent = resourceC)
    val resourceX = Resource("X", 100, parent = resourceA)
    val resourceY = Resource("Y", 200, parent = resourceX)

    listOf(resourceA, resourceB, resourceC, resourceD, resourceX, resourceY)
}

// регулярка, проверяющая на строку не более чем из 20 из символов латинского алфавита любого регистра, цифр и символа нижнего подчеркивания
fun findResourceByPath(path: String): Resource? {
    val parts = path.split(".")
    if (!parts.all { it.matches(Regex("^[A-Za-z0-9_]{1,20}$")) }) return null
    return resources.firstOrNull { it.name == parts.first() }?.findByPath(parts)
}