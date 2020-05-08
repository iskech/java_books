package com.iskech.thread.basic;

public class Cell<E> {
    private Cell<E> next;
    private E element;
    
    public Cell(E element) {
        super();
        this.element = element;
    }
    
    public Cell(Cell<E> next, E element) {
        super();
        this.next = next;
        this.element = element;
    }

    public Cell<E> getNext() {
        return next;
    }

    public void setNext(Cell<E> next) {
        this.next = next;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }
}