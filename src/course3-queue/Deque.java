import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node pre;
        private Node next;
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if (!isEmpty()) {
            node.next = first;
            first.pre = node;
        } else {
            last = node;
        }

        first = node;

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;

        if (!isEmpty()) {
            Node oldLast = last;
            oldLast.next = node;
            node.pre = oldLast;
        } else {
            first = node;
        }

        last = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node f = first;
        first = f.next;
        f.next = null;

        if (size() > 1) {
            first.pre = null;
        } else {
            first = null;
        }

        size--;

        return f.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node l = last;
        last = l.pre;
        l.pre = null;

        if (size() > 1) {
            last.next = null;
        } else {
            last = null;
        }

        size--;

        return l.item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        StdOut.println(deque.isEmpty());
        deque.addFirst("a");
        StdOut.println(deque.isEmpty());
        deque.addLast("b");
        StdOut.println(deque.isEmpty());
        deque.removeFirst();
        StdOut.println(deque.isEmpty());
        deque.removeLast();
        StdOut.println(deque.isEmpty());
        deque.addFirst("a");
        deque.addLast("b");
        deque.removeLast();
        StdOut.println(deque.isEmpty());
        deque.removeFirst();
        StdOut.println(deque.isEmpty());
    }
}
