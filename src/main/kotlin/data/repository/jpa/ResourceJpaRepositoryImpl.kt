package app.data.repository.jpa

import app.domain.model.Resource
import app.domain.repository.ResourceRepository
import app.domain.repository.jpa.ResourceJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ResourceJpaRepositoryImpl(
    private val resourceJpaRepository: ResourceJpaRepository
) : ResourceRepository {

    override fun findResourceByPath(path: String): Resource? {
        val parts = path.split(".")
        if (parts.isEmpty()) return null

        var current = resourceJpaRepository.findByName(parts.first()) ?: return null

        for (part in parts.drop(1)) {
            current = resourceJpaRepository.findAll()
                .firstOrNull { it.name == part && it.parent?.id == current.id }
                ?: return null
        }

        return current.toDomain()
    }

    private fun Resource.toDomain(): Resource =
        Resource(
            name = name,
            maxVolume = maxVolume,
            parent = parent?.toDomain()
        )
}