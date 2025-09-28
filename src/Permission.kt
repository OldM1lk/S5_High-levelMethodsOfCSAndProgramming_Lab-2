data class Permission(
    val userLogin: String,
    val resourceName: String,
    val actions: Set<ResourceAction>)

val permissions = listOf(
    Permission(
        "alice",
        "B",
        setOf(ResourceAction.READ, ResourceAction.WRITE, ResourceAction.EXECUTE)),
    Permission(
        "bob",
        "X",
        setOf(ResourceAction.READ))
)

fun hasPermission(user: String, resource: Resource, action: ResourceAction): Boolean {
    var current: Resource? = resource
    while (current != null) {
        val perm = permissions.find { it.userLogin == user && it.resourceName == current.name }
        if (perm != null && action in perm.actions) return true
        current = current.parent
    }
    return false
}