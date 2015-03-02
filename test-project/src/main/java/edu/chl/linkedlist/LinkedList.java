package edu.chl.linkedlist;

/**
 * Created by davida on 20.1.2015.
 */
public class LinkedList<T> {

    private Node head;

    public LinkedList() {
        head = new Node();
    }

    public class Node {
        private Node next;
        private Node prev;
        private T value;

        Node getNext() {
            return next;
        }

        void setNext(Node next) {
            this.next = next;
        }

        Node getPrev() {
            return prev;
        }

        void setPrev(Node prev) {
            this.prev = prev;
        }

        void setValue(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }

    public void insert(T value) {
        Node newNode = new Node();
        newNode.setValue(value);
        Node endNode = getEndNode();
        linkNodes(endNode, newNode);
    }

    private Node getEndNode() {
        Node curr = head;

        while (curr.getNext() != null) {
            curr = curr.getNext();
        }

        return curr;
    }

    public T get(int i) {

        Node curr = head;

        while(curr.getNext() != null && (i--) > -1) {
            curr = curr.getNext();
        }

        if (i > 0) {
            return null;
        }

        return curr.getValue();
    }

    public void removeAt(int i) {
        Node curr = head;

        while ((curr = curr.getNext()) != null && (i--) > -1) {
            linkNodes(curr.getPrev(), curr.getNext());
        }
    }

    public void remove(T value) {
        Node curr = head;

        do {
            T nodeValue = curr.getValue();

            if (value == null && null == nodeValue || (value != null && value.equals(nodeValue))) {
                linkNodes(curr.getPrev(), curr.getNext());
            }
        } while ((curr = curr.getNext()) != null);
    }

    private void linkNodes(Node prev, Node next) {
        if (prev != null) {
            prev.setNext(next);
        }

        if (next != null) {
            next.setPrev(prev);
        }
    }

    public int firstIndexOf(T value) {
        Node curr = head;
        int i = 0;

        while ((curr = curr.getNext()) != null && !curr.getValue().equals(value)) {
            ++i;
        }

        return i;
    }

    public int size() {
        int count = 0;
        Node curr = head;

        while ((curr = curr.getNext()) != null) {
            count++;
        }

        return count;
    }
}
