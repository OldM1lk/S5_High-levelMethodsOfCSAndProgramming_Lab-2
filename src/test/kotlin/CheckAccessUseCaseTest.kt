package test.kotlin

import app.data.repository.memory.InMemoryPermissionRepository
import app.data.repository.memory.InMemoryResourceRepository
import app.domain.model.ResourceAction
import app.domain.use_case.CheckAccessUseCase
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CheckAccessUseCaseTest {
    @Test
    fun shouldDelegateCallToPermissionRepositoryAndReturnTrue() {
        val permissionRepo = InMemoryPermissionRepository()
        val resourceRepo = InMemoryResourceRepository()
        val useCase = CheckAccessUseCase(permissionRepo)

        val resourceD = resourceRepo.findResourceByPath("A.B.C.D")!!

        val result = useCase("alice", resourceD, ResourceAction.READ)

        assertTrue(result)
    }

    @Test
    fun shouldDelegateCallToPermissionRepositoryAndReturnFalse() {
        val permissionRepo = InMemoryPermissionRepository()
        val resourceRepo = InMemoryResourceRepository()
        val useCase = CheckAccessUseCase(permissionRepo)

        val resourceY = resourceRepo.findResourceByPath("A.X.Y")!!

        val result = useCase("bob", resourceY, ResourceAction.WRITE)

        assertFalse(result)
    }
}