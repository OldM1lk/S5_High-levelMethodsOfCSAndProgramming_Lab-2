package domain.use_case

import domain.model.Resource
import domain.model.ResourceAction
import domain.repository.PermissionRepository

class CheckAccessUseCase(
    private val repository: PermissionRepository
) {
    operator fun invoke(user: String, resource: Resource, action: ResourceAction): Boolean =
        repository.hasPermission(user, resource, action)
}