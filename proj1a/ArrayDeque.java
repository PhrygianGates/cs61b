public class ArrayDeque<T> {
    private int size;
    private int capacity;
    private int start;
    private int end;
    private T[] items;

    public ArrayDeque() {
        items = (T[])new Object[8];
        start = end = size = 0;
        capacity = 8;

    }
    public void addFirst(T item) {
        if (size == capacity) {
            resize();
        }
        start--;
        if (start == -1) {
            start = capacity - 1;
        }
        items[start] = item;
        size++;
    }
    public void addLast(T item) {
        if (size == capacity) {
            resize();
        }
        end++;
        if (end == capacity) {
            end = 0;
        }
        items[end] = item;
        size++;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    private void resize() {
        T[] temp;
        if (size == capacity) {
            temp = (T[]) new Object[capacity * 2];
        }else if (size < capacity / 4) {
            temp = (T[]) new Object[capacity / 2];
        }else {
            return;
        }
        int i = 0, j = 0;
        if (start != 0) {
            for(i = 0; i < capacity - start; i++) {
                temp[i] = items[i + start];
            }
        }
        for(j = 0; j < end; j++) {
            temp[j + i + 1] = items[j];
        }
        if (size == capacity) {
            capacity *= 2;
        }else {
            capacity /= 2;
        }
        start = 0;
        end = j + i + 1;
        items = temp;
    }
    public T removeFirst() {
        if (size > 0) {
            T temp = items[start];
            start++;
            if (start == capacity) {
                start = 0;
            }
            size--;
            if (size < capacity / 4) {
                resize();
            }
            return temp;
        }
        return null;
    }
    public T removeLast() {
        if (size > 0) {
            T temp = items[end];
            end--;
            if (end == -1) {
                end = capacity - 1;
            }
            size--;
            if (size < capacity / 4) {
                resize();
            }
            return temp;
        }
        return null;
    }
    public T get(int index) {
        return items[(start + index) % capacity];
    }
    public void printDeque() {
        int i = 0, j = 0;
        if (start != 0) {
            for(i = 0; i < capacity - start; i++) {
                System.out.print(items[i + start]);
            }
        }
        for(j = 0; j < end; j++) {
            System.out.print(items[j]);
        }
    }
}