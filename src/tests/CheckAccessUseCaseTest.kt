package tests

import data.repository.InMemoryPermissionRepository
import data.repository.InMemoryResourceRepository
import domain.model.ResourceAction
import domain.use_case.CheckAccessUseCase
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