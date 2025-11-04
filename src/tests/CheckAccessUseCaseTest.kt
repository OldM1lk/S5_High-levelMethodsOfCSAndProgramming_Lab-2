package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import domain.use_case.AuthenticateUserUseCase
import data.repository.InMemoryUserRepository
import data.repository.InMemoryResourceRepository
import data.repository.InMemoryPermissionRepository
import domain.model.ResourceAction
import domain.model.Resource
import domain.use_case.CheckAccessUseCase

class CheckAccessUseCaseTest {
    @Test
    fun shouldDelegateCallToPermissionRepositoryAndReturnTrue() {
        val permissionRepo = InMemoryPermissionRepository()
        val resourceRepo = InMemoryResourceRepository()
        val useCase = CheckAccessUseCase(permissionRepo)

        val resourceD = resourceRepo.findResourceByPath("A.B.C.D")!!

        val result = useCase.execute("alice", resourceD, ResourceAction.READ)

        assertTrue(result)
    }

    @Test
    fun shouldDelegateCallToPermissionRepositoryAndReturnFalse() {
        val permissionRepo = InMemoryPermissionRepository()
        val resourceRepo = InMemoryResourceRepository()
        val useCase = CheckAccessUseCase(permissionRepo)

        val resourceY = resourceRepo.findResourceByPath("A.X.Y")!!

        val result = useCase.execute("bob", resourceY, ResourceAction.WRITE)

        assertFalse(result)
    }
}