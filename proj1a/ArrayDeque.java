public class ArrayDeque<T> {
    private int size;
    private int capacity;
    private int start;
    private int end;
    private T[] items;
    private int next(int n) {
        n++;
        if (n == capacity) {
            n = 0;
        }
        return n;
    }
    private int previous(int n) {
        n--;
        if (n == -1) {
            n = capacity - 1;
        }
        return n;
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        start = end = size = 0;
        capacity = 8;
    }
    public void addFirst(T item) {
        if (size == capacity) {
            resize();
        }
        start = previous(start);
        items[start] = item;
        size++;
    }
    public void addLast(T item) {
        if (size == capacity) {
            resize();
        }
        end = next(end);
        items[previous(end)] = item;
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
        } else if (size < capacity / 4) {
            temp = (T[]) new Object[capacity / 2];
        } else {
            return;
        }
        for (int i = 0; i < size; i++) {
            temp[i] = items[start];
            start = next(start);
        }
        start = 0;
        end = size;
        items = temp;
        if (size == capacity) {
            capacity *= 2;
        } else if (size < capacity / 4) {
            capacity /= 2;
        }
    }
    public T removeFirst() {
        if (size > 0) {
            T temp = items[start];
            start = next(start);
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
            end = previous(end);
            T temp = items[end];
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
        int j = start;
        for (int i = 0; i < size; i++) {
            System.out.print(items[j]);
            j = next(j);
        }
    }
}
