package adt;

// All methods that must be implemented by Stack
interface stackADT<T>{
        void push(T element);
        T pop();
        T peek();
        boolean isEmpty();
        int size();
        void printStack();
}


public class Stack<T> implements stackADT<T>{
    // Class to represent the Nodes within the queue
    class Node<T>{
        T value;
        Node<T> next;

        Node(T value){
            this.value = value;
        }
    }

    public void push(T element){


    }
    T pop(){}
    T peek(){}
    boolean isEmpty(){}
    int size(){}
    void printStack(){}



}
