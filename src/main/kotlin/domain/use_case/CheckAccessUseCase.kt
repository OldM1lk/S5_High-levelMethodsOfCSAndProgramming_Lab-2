package main.kotlin.domain.use_case

import main.kotlin.domain.model.Resource
import main.kotlin.domain.model.ResourceAction
import main.kotlin.domain.repository.PermissionRepository

class CheckAccessUseCase(
    private val repository: PermissionRepository
) {
    operator fun invoke(user: String, resource: Resource, action: ResourceAction): Boolean =
        repository.hasPermission(user, resource, action)
}