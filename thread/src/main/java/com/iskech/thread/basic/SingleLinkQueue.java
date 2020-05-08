package com.iskech.thread.basic;

public class SingleLinkQueue<E> {
    private Cell<E> head;
    private Cell<E> tail;

    public void add(E item) {

        Cell<E> cell = new Cell<>(item);
        if (tail == null) {
            tail = cell;
            head = tail;
        } else {
            if (head == null) {
                tail.setNext(head);
            } else {
                head.setNext(cell);
            }
            head = cell;
        }
    }

    public E remove() {
        if (tail == null)
            throw new RuntimeException("SingleLinkQueue is empty");

        Cell<E> cell = null;
        if (tail != null && head != null && !head.equals(tail)) {
            cell = head;
            Cell<E> preHead = findPreHead(tail);
            preHead.setNext(null);
            head = preHead;
        } else {
            cell = tail;
            tail = null;
        }

        return cell.getElement();
    }

    private Cell<E> findPreHead(Cell<E> cell) {
        Cell<E> preHead = null;
        if (cell == null) {
            preHead = null;
        } else {
            Cell<E> next = cell.getNext();
            if (next == null)
                preHead = cell;
            else {
                if (next.getElement().equals(head.getElement()))
                    preHead = cell;
                else
                    preHead = findPreHead(cell.getNext());
            }
        }

        return preHead;
    }

    public boolean isEmpty() {
        return tail == null;
    }

    public String toString() {
        if (tail == null)
            return "[]";

        String[] elements = elements(tail).split(",");
        String desc = "";
        for (int i = elements.length - 1; i >= 0; i--)
            desc += elements[i] + "\n";
        return desc;
    }

    private String elements(Cell<E> cell) {
        if (cell == null)
            return "";

        String desc;
        if (cell.getElement() != null)
            desc = (String) cell.getElement() + ",";
        else
            desc = "";

        if (cell.getNext() != null)
            desc += elements(cell.getNext());

        return desc;
    }

    public static void main(String[] args) {

        SingleLinkQueue<String> singleLinkQueue = new SingleLinkQueue<>();
        singleLinkQueue.add("Tom");
        singleLinkQueue.add("Dick");
        singleLinkQueue.add("Harry");
        singleLinkQueue.add("Johnny");
        singleLinkQueue.add("Mickey");

        System.out.println(singleLinkQueue);

        while (!singleLinkQueue.isEmpty()) {
            System.out.println("Removing element " + singleLinkQueue.remove());
            System.out.println("Single Linked Queue is now: ");
            System.out.println(singleLinkQueue);
        }
    }
}