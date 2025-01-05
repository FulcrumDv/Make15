package adtTest;

import adt.Queue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    @Test
    void testEnqueueAndDequeue() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue(), "Dequeue did not return the first element.");
        assertEquals(2, queue.dequeue(), "Dequeue did not return the second element.");
        assertEquals(3, queue.dequeue(), "Dequeue did not return the third element.");
    }

    @Test
    void testPeek() {
        Queue<String> queue = new Queue<>();
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.peek(), "Peek did not return the first element.");
        queue.dequeue();
        assertEquals("B", queue.peek(), "Peek did not return the updated first element after dequeue.");
    }

    @Test
    void testIsEmpty() {
        Queue<Double> queue = new Queue<>();
        assertTrue(queue.isEmpty(), "Queue should be empty initially.");

        queue.enqueue(1.1);
        assertFalse(queue.isEmpty(), "Queue should not be empty after enqueue.");

        queue.dequeue();
        assertTrue(queue.isEmpty(), "Queue should be empty after dequeuing all elements.");
    }

    @Test
    void testSize() {
        Queue<Character> queue = new Queue<>();
        assertEquals(0, queue.size(), "Initial size should be 0.");

        queue.enqueue('X');
        queue.enqueue('Y');
        queue.enqueue('Z');
        assertEquals(3, queue.size(), "Size should be 3 after enqueuing 3 elements.");

        queue.dequeue();
        assertEquals(2, queue.size(), "Size should be 2 after one dequeue.");
    }

    @Test
    void testDequeueOnEmptyQueue() {
        Queue<Integer> queue = new Queue<>();
        Exception exception = assertThrows(IllegalStateException.class, queue::dequeue);
        assertEquals("The queue is empty, cannot Dequeue from Empty Queue!", exception.getMessage());
    }

    @Test
    void testPeekOnEmptyQueue() {
        Queue<Integer> queue = new Queue<>();
        Exception exception = assertThrows(IllegalStateException.class, queue::peek);
        assertEquals("Queue is empty, nothing to peek!", exception.getMessage());
    }
}