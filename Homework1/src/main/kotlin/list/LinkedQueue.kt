package list

class LinkedQueue(val capacity: Int) {
    private val head: Node
    private val tail: Node
    var size: Int

    init {
        head = Node(previous = null, next = null, key = 0)
        tail = Node(previous = null, next = null, key = 0)
        head.next = tail
        tail.previous = head
        size = 0
    }

    fun add(key: Int): Node {
        assert(size < capacity) { "Size must be less than capacity = $capacity" }
        val last = tail.previous!!
        val node = Node(previous = last, next = tail, key = key)
        last.next = node
        tail.previous = node
        size++
        return node
    }

    fun delete(node: Node): Int {
        assert(size > 0) {
            "Can't delete node from empty queue"
        }
        assert(node != head && node != tail) {
            "Invalid node"
        }
        val previous = node.previous!!
        val next = node.next!!
        previous.next = next
        next.previous = previous
        size--
        return node.key
    }

    fun poll(): Int {
        assert(size > 0) { "Can't poll from empty queue" }
        return delete(head.next!!)
    }
}