import java.util.LinkedList
import java.util.Queue

class LRUCache<T>(private val size: Int) {
    private val list: Queue<Int> = LinkedList()
    private val map: HashMap<Int, T> = hashMapOf()

    init {
        assert(size > 0) { "Size must be more than 0" }
    }

    private fun mainAssert() {
        check(list.size <= size) { "Cache overflow" }
        check(list.size == map.size) { "Cache out of sync" }
    }

    fun put(key: Int, value: T) {
        mainAssert()

        if (map.containsKey(key)) {
            map[key] = value
        } else {
            if (list.size == size) {
                val old = list.poll()
                map.remove(old)
                check(list.size == map.size) {
                    "Deleting went wrong"
                }
            }
            list.add(key)
            map[key] = value
        }

        mainAssert()
    }

    fun get(key: Int): T? {
        mainAssert()
        return map[key]
    }

    fun size(): Int = list.size
}