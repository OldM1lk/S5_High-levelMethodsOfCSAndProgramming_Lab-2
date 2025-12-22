package app.domain.repository.jpa

import app.domain.model.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionJpaRepository : JpaRepository<Permission, Long> {
    fun findAllByLoginAndResource(
        login: String,
        resourceName: String
    ): List<Permission>
}