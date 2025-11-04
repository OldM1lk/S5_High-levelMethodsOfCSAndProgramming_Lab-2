package tests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import util.hashPassword

class HashUtilTest {
    @Test
    fun shouldGenerateSameHashForSameInput() {
        val password = "mySecret"
        val salt = "salt123"

        val hash1 = hashPassword(password, salt)
        val hash2 = hashPassword(password, salt)

        assertEquals(hash1, hash2)
    }

    @Test
    fun shouldGenerateDifferentHashForDifferentSalt() {
        val password = "mySecret"
        val hash1 = hashPassword(password, "saltA")
        val hash2 = hashPassword(password, "saltB")

        assertNotEquals(hash1, hash2)
    }
}