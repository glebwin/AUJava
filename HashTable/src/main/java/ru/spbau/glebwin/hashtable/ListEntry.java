package ru.spbau.glebwin.hashtable;

/**
 * Created by glebwin on 9/11/16.
 */
public class ListEntry {
    private HashTableEntry data;
    private ListEntry next;

    public ListEntry(HashTableEntry data, ListEntry next) {
        this.data = data;
        this.next = next;
    }

    public ListEntry(HashTableEntry data) {
        this(data, null);
    }

    public HashTableEntry getData() {
        return data;
    }

    public ListEntry getNext() {
        return next;
    }

    public boolean isLast() {
        return next == null;
    }

    public void removeNext() {
        if (next != null) {
            next = next.next;
        }
    }
}
