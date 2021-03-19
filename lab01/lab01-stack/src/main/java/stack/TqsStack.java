package stack;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

    private LinkedList<T> stack = new LinkedList<T>();

    public void push(T item) {
        stack.push(item);
    }

    public T pop() {
        if (stack.isEmpty())
            throw new NoSuchElementException();
        return stack.removeFirst();
    }

    public T peek() {
        if (stack.isEmpty())
            throw new NoSuchElementException();
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
