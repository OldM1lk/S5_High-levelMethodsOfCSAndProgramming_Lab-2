package test.kotlin

import app.data.repository.memory.InMemoryPermissionRepository
import app.data.repository.memory.InMemoryResourceRepository
import app.domain.model.ResourceAction
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class InMemoryPermissionRepositoryTest {
    @Test
    fun shouldInheritPermissionFromParent() {
        val resourceRepo = InMemoryResourceRepository()
        val permRepo = InMemoryPermissionRepository()

        val resourceD = resourceRepo.findResourceByPath("A.B.C.D")!!

        val hasRead = permRepo.hasPermission("alice", resourceD, ResourceAction.READ)
        val hasWrite = permRepo.hasPermission("alice", resourceD, ResourceAction.WRITE)
        val hasExecute = permRepo.hasPermission("alice", resourceD, ResourceAction.EXECUTE)

        assertTrue(hasRead)
        assertTrue(hasWrite)
        assertTrue(hasExecute)
    }

    @Test
    fun shouldReturnFalseForUserWithoutAnyPermissions() {
        val resourceRepo = InMemoryResourceRepository()
        val permRepo = InMemoryPermissionRepository()

        val resourceA = resourceRepo.findResourceByPath("A")!!
        assertFalse(permRepo.hasPermission("charlie", resourceA, ResourceAction.READ))
    }
}
