package adt;

// All methods that must be implemented by Queue
interface QueueADT<T>{
    void enqueue(T element); // Pushes to end
    T dequeue();
    T peek();
    boolean isEmpty();
    int size();
    void printQueue();
}


// Implements a Queue using a Linked List and generics
public class Queue<T> implements QueueADT<T> {

    class Node<T>{
        T value;
        Node<T> next;

        Node(T value){
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Queue(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void enqueue(T item ){
        Node<T> newNode = new Node<>(item);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        }else {
            tail.next = newNode;
            tail = newNode;
        }
        // Recording Size of Queue
        size++;
    }

    public T dequeue(){
        if (isEmpty()) {
            throw new IllegalStateException("The queue is empty, cannot Dequeue from Empty Queue!");
        }


        // Store node removed so that it can be returned
        Node<T> tempNode = head;
        head = head.next;

        // if head is null, then tail becomes null (Queue is empty)
        if(head == null){
            tail = null;
        }

        size--;

        return tempNode.value;
    }

    public T peek(){
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty, nothing to peek!");
        }
        // returns value of last element in queue
        return head.value;
    }

    public boolean isEmpty(){
        return head == null;
    }

    @Override
    public int size(){
        return size;

    }

    public void printQueue() {

        if (isEmpty()) {
            throw new IllegalStateException("Cannot print empty Queue");
        }

        Node<T> currentNode = head;
        while (currentNode != null) {
            System.out.println(currentNode.value);
            currentNode = currentNode.next;
        }
        System.out.println();

    }
}
