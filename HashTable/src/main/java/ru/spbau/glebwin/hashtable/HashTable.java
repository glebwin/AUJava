package ru.spbau.glebwin.hashtable;

/**
 * Created by glebwin on 9/11/16.
 */
public class HashTable {
    private List table[];
    private int keysNum;

    public HashTable() {
        table = new List[1];
        for (int i = 0; i < table.length; i++) {
            table[i] = new List();
        }
    }

    /**
     * @return Number of keys in hash table
     */
    public int size() {
        return keysNum;
    }

    /**
     * Check whether the key exists in hash table
     * @return true if the key exists, false otherwise
     */
    public boolean contains(String key) {
        List list = getTargList(key);
        for (ListEntry node = list.getHead(); node != null; node = node.getNext()) {
            if (node.getData().getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Corresponding value or null if the key doesn't exist
     */
    public String get(String key) {
        List list = getTargList(key);
        for (ListEntry node = list.getHead(); node != null; node = node.getNext()) {
            if (node.getData().getKey().equals(key)) {
                return node.getData().getValue();
            }
        }
        return null;
    }

    /**
     * Add pair key-value to hash table
     * @return If the key already exists return its previous value or null otherwise
     */
    public String put(String key, String value) {
        List list = getTargList(key);
        for (ListEntry node = list.getHead(); node != null; node = node.getNext()) {
            if (node.getData().getKey().equals(key)) {
                String oldValue = node.getData().getValue();
                node.getData().setValue(value);
                return oldValue;
            }
        }

        keysNum++;
        list.insert(new HashTableEntry(key, value));

        if (keysNum >= table.length) {
           resize(table.length * 2);
        }

        return null;
    }

    /**
     * Remove the key from hash table and return its value
     * @param key the key to remove
     * @return Corresponding value or null if the key doesn't exists
     */
    public String remove(String key) {
        List list = getTargList(key);

        // Check special case: lists head
        if (!list.empty() && list.getHead().getData().getKey().equals(key)) {
            keysNum--;
            String retValue = list.getHead().getData().getValue();
            list.removeHead();
            return retValue;
        }

        for (ListEntry node = list.getHead(); !node.isLast(); node = node.getNext()) {
            if (node.getNext().getData().getKey().equals(key)) {
                keysNum--;
                String retValue = node.getNext().getData().getValue();
                node.removeNext();
                return retValue;
            }
        }

        return null;
    }

    /**
     * Change number of lists in hash table
     * @param newSize the number of lists after resize
     */
    private void resize(int newSize) {
        List oldTable[] = table;
        table = new List[newSize];
        for (int i = 0; i < table.length; i++) {
            table[i] = new List();
        }

        for (List list : oldTable) {
            for (ListEntry node = list.getHead(); node != null; node = node.getNext()) {
                HashTableEntry data = node.getData();
                getTargList(data.getKey()).insert(data);
            }
        }
    }

    /**
     * Remove all keys from hash table
     */
    public void clear() {
        for (List list : table) {
            list.clear();
        }
        keysNum = 0;
    }

    /**
     * @return Hash tables list corresponding to the keys hash
     */
    private List getTargList(String key) {
        return table[(key.hashCode() % table.length + table.length) % table.length];
    }
}
