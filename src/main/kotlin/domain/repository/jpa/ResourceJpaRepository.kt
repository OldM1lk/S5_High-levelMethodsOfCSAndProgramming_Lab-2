package app.domain.repository.jpa

import app.domain.model.Resource
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResourceJpaRepository : JpaRepository<Resource, Long> {
    fun findByName(name: String): Resource?
    fun existsByName(name: String): Boolean
}