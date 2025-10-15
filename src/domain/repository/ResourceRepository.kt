package domain.repository

interface ResourceRepository {
    fun findResourceByPath(path: String)
}