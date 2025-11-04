package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import data.repository.InMemoryResourceRepository
import data.repository.InMemoryPermissionRepository
import domain.model.ResourceAction
import domain.model.Resource
import domain.service.ResourceNavigator
import util.hashPassword

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
