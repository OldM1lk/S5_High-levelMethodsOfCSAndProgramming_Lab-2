package app.data.repository.jpa

import app.domain.model.Resource
import app.domain.model.ResourceAction
import app.domain.repository.PermissionRepository
import app.domain.repository.jpa.PermissionJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PermissionJpaRepositoryImpl(
    private val permissionJpaRepository: PermissionJpaRepository
) : PermissionRepository {

    override fun hasPermission(
        user: String,
        resource: Resource,
        action: ResourceAction
    ): Boolean {

        var current: Resource? = resource

        while (current != null) {
            val permissions =
                permissionJpaRepository.findAllByLoginAndResource(
                    user,
                    current.name
                )

            if (permissions.any { it.action == action }) {
                return true
            }

            current = current.parent
        }

        return false
    }
}
