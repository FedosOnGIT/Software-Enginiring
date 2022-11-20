import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LRUCacheTest {
    @Test
    fun creatingCacheTest() {
        assertFailsWith<AssertionError>("Size must be more than 0", block = { LRUCache<Int>(-1) })
        val normalCache = LRUCache<Int>(1)
        assertEquals(0, normalCache.size())
    }

    @Test
    fun executionTest() {
        val cache = LRUCache<String>(2)
        // adding
        cache.put(1, "1")
        assertEquals(1, cache.size())
        assertEquals("1", cache.get(1))
        assertEquals(null, cache.get(2))

        // adding second
        cache.put(2, "2")
        assertEquals(2, cache.size())
        assertEquals("1", cache.get(1))
        assertEquals("2", cache.get(2))

        // adding third
        cache.put(3, "3")
        assertEquals(2, cache.size())
        assertEquals(null, cache.get(1))
        assertEquals("2", cache.get(2))
        assertEquals("3", cache.get(3))

        // replacing
        cache.put(2, "it is not 2")
        assertEquals(2, cache.size())
        assertEquals("it is not 2", cache.get(2))
        assertEquals("3", cache.get(3))
    }

    @Test
    fun getMustPutToEndTest() {
        val cache = LRUCache<String>(2)
        val answer = "Always find it"

        cache.put(1, answer)
        cache.put(2, "2")
        assertEquals(2, cache.size())
        assertEquals(answer, cache.get(1))

        cache.put(3, "3")
        assertEquals(2, cache.size())
        assertEquals(answer, cache.get(1))
        assertEquals(null, cache.get(2))
    }
}
