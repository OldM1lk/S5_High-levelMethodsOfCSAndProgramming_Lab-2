package main.kotlin.domain.repository

import main.kotlin.domain.model.Resource

interface ResourceRepository {
    fun findResourceByPath(path: String): Resource?
}