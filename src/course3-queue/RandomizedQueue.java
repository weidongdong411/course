import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        ;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (N == s.length) {
            addResize(2 * s.length);
        }

        s[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int rand = StdRandom.uniformInt(N);
        Item item = s[rand];
        s[rand] = null;

        delResize(N);
        N--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int rand = StdRandom.uniformInt(N);
        Item item = s[rand];

        return item;
    }

    private void addResize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    private void delResize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity - 1];
        int i = 0;
        for (Item item : this) {
            if (item != null) {
                copy[i++] = item;
            }
        }

        s = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < N;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return s[i++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        StdOut.println(queue.isEmpty());
        queue.enqueue("a");
        StdOut.println(queue.isEmpty());
        queue.dequeue();
        StdOut.println(queue.isEmpty());
    }
}
