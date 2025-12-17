package app.domain.use_case

import app.domain.model.Resource
import app.domain.model.ResourceAction
import app.domain.repository.PermissionRepository
import org.springframework.stereotype.Service

@Service
class CheckAccessUseCase(
    private val repository: PermissionRepository
) {
    operator fun invoke(user: String, resource: Resource, action: ResourceAction): Boolean =
        repository.hasPermission(user, resource, action)
}