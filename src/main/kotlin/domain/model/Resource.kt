package app.domain.model

data class Resource(
    val name: String,
    val maxVolume: Int,
    val parent: Resource? = null
)
