import list.LinkedQueue
import list.Node

class LRUCache<T>(private val size: Int) {
    private val list: LinkedQueue
    private val map: HashMap<Int, Pair<T, Node>>

    init {
        assert(size > 0) { "Size must be more than 0" }
        list = LinkedQueue(size)
        map = hashMapOf()
    }

    private fun mainAssert() {
        check(list.size <= size) { "Cache overflow" }
        check(list.size == map.size) { "Cache out of sync" }
    }

    private fun move(key: Int, value: T?) : T? {
        val old = map[key] ?: return null
        list.delete(old.second)
        val node: Node = list.add(key = key)
        val input = Pair(value ?: old.first, second = node)
        map[key] = input
        return input.first
    }

    fun put(key: Int, value: T) {
        mainAssert()

        if (map.containsKey(key)) {
            move(key = key, value)
        } else {
            if (list.size == size) {
                val old = list.poll()
                map.remove(old)
                check(list.size == map.size) {
                    "Deleting went wrong"
                }
            }
            map[key] = Pair(value, list.add(key))
        }

        mainAssert()
    }

    fun get(key: Int): T? {
        mainAssert()
        return move(key = key, null)
    }

    fun size(): Int = list.size
}