public class LinkedListDeque<T> {
    private class Node {
        T data;
        Node previous;
        Node next;
        Node(T d, Node p, Node n) {
            data = d;
            previous = p;
            next = n;
        }
    }
    private int size;
    private Node sentinel;
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }
    /*public LinkedListDeque(T d) {
        sentinel = new Node(null, null, null);
        Node temp = new Node(d, sentinel, sentinel);
        sentinel.previous = temp;
        sentinel.next = temp;
        size = 1;
    }*/
    public void addFirst(T item) {
        Node temp = new Node(item, sentinel, sentinel.next);
        sentinel.next.previous = temp;
        sentinel.next = temp;
        size++;
    }
    public void addLast(T item) {
        Node temp = new Node(item, sentinel.previous, sentinel);
        sentinel.previous.next = temp;
        sentinel.previous = temp;
        size++;
    }
    public T removeFirst() {
        if (size > 0) {
            T item = sentinel.next.data;
            sentinel.next = sentinel.next.next;
            sentinel.next.previous = sentinel;
            size--;
            return item;
        }
        return null;
    }
    public T removeLast() {
        if (size > 0) {
            T item = sentinel.previous.data;
            sentinel.previous = sentinel.previous.previous;
            sentinel.previous.next = sentinel;
            size--;
            return item;
        }
        return null;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public T get(int index) {
        if (index >= 0 && index < size) {
            int i = 0;
            Node curr = sentinel;
            while (i <= index) {
                curr = curr.next;
                i++;
            }
            return curr.data;
        }
        return null;
    }
    public void printDeque() {
        if (size > 0) {
            Node curr = sentinel.next;
            while (curr != sentinel) {
                System.out.print(curr.data + " ");
                curr = curr.next;
            }
        }
    }
    public T getRecursive(int index) {
        if (index >= 0 && index < size) {
            return helper(index, sentinel.next);
        }
        return null;
    }
    private T helper(int index, Node start) {
        if (index == 0) {
            return start.data;
        } else {
            return helper(index - 1, start.next);
        }
    }
}
