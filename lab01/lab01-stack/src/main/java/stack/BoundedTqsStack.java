package stack;

public class BoundedTqsStack<T> extends TqsStack<T>{

    private int MAXITEMS = 20;

    @Override
    public void push(T item) {
        /**
         * this is a bounded stack
         * ans its max size is 20
         * more than that, it throws an IllegalStateException
         */
        if (super.size() == MAXITEMS)
            throw new IllegalStateException();
        super.push(item);
    }
}
