package domain.repository

import domain.model.Resource

interface ResourceRepository {
    fun findResourceByPath(path: String): Resource?
}