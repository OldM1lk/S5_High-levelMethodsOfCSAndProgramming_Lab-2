package main.kotlin.domain.model

data class Permission(
    val userLogin: String,
    val resourceName: String,
    val actions: Set<ResourceAction>
)
