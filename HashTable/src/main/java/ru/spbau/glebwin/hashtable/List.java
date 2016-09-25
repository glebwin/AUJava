package ru.spbau.glebwin.hashtable;

/**
 * List class for the needs of string-to-string Hash table
 */
public class List {
    private ListEntry head;

    public ListEntry getHead() {
        return head;
    }

    public boolean empty() {
        return head == null;
    }

    public void clear() {
        head = null;
    }

    /**
     * Insert key-value pair in list overwriting keys old value if the key already existed
     * @return Old value if the key already existed, null otherwise
     */
    public String insertUniqueKey(String key, String value) {
        for (ListEntry node = head; node != null; node = node.getNext()) {
            if (node.getKey().equals(key)) {
                String oldValue = node.getValue();
                node.setValue(value);
                return oldValue;
            }
        }

        head = new ListEntry(key, value, head);
        return null;
    }

    /**
     * @return Corresponding value or null if the key doesn't exist
     */
    public String findByKey(String key) {
        for (ListEntry node = head; node != null; node = node.getNext()) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }

        return null;
    }

    /**
     * Remove entry with the key
     * @return Keys value or null if the key doesn't exist
     */
    public String removeByKey(String key) {
        if (empty()) {
            return null;
        }

        if (head.getKey().equals(key)) {
            String retValue = head.getValue();
            head = head.getNext();
            return retValue;
        }

        for (ListEntry node = head; node.hasNext(); node = node.getNext()) {
            if(node.getNext().getKey().equals(key)) {
                String retValue = node.getNext().getValue();
                node.removeNext();
                return retValue;
            }
        }

        return null;
    }

    public static class ListEntry {
        private final String key;
        private String value;
        private ListEntry next;

        public ListEntry(String key, String value, ListEntry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public ListEntry getNext() {
            return next;
        }

        public boolean hasNext() {
            return next != null;
        }

        public void removeNext() {
            if (hasNext()) {
                next = next.next;
            }
        }
    }
}
