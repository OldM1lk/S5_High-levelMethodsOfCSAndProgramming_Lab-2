package app.domain.repository

import app.domain.model.Resource

interface ResourceRepository {
    fun findResourceByPath(path: String): Resource?
}