package test.kotlin

import app.domain.model.Resource
import app.domain.service.ResourceNavigator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ResourceNavigatorTest {
    @Test
    fun shouldBuildFullPathCorrectly() {
        val resourceA = Resource("A", 10)
        val resourceB = Resource("B", 20, parent = resourceA)
        val resourceC = Resource("C", 30, parent = resourceB)

        val path = ResourceNavigator.fullPath(resourceC)

        assertEquals("A.B.C", path)
    }

    @Test
    fun shouldFindResourceByPath() {
        val resourceA = Resource("A", 10)
        val resourceB = Resource("B", 20, parent = resourceA)
        val resourceC = Resource("C", 30, parent = resourceB)
        val resourceD = Resource("D", 40, parent = resourceB)
        val allResources = listOf(resourceA, resourceB, resourceC, resourceD)

        val found = ResourceNavigator.findByPath(resourceA, listOf("A", "B", "C"), allResources)

        assertNotNull(found)
        assertEquals("C", found?.name)
        assertEquals(30, found?.maxVolume)
    }

    @Test
    fun shouldReturnNullForNonexistentPath() {
        val resourceA = Resource("A", 10)
        val resourceB = Resource("B", 20, parent = resourceA)
        val allResources = listOf(resourceA, resourceB)

        val found = ResourceNavigator.findByPath(resourceA, listOf("A", "X"), allResources)

        assertNull(found)
    }
}