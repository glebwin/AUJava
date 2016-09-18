package ru.spbau.glebwin.hashtable;

/**
 * Created by glebwin on 9/11/16.
 */
public class List {
    private ListEntry head;

    public ListEntry getHead() {
        return head;
    }

    public void removeHead() {
        if (head != null) {
            head = head.getNext();
        }
    }

    public void insert(HashTableEntry newData) {
        head = new ListEntry(newData, head);
    }

    public boolean empty() {
        return head == null;
    }

    public void clear() {
        head = null;
    }
}
