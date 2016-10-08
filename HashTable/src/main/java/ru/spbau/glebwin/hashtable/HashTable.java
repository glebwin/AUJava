package ru.spbau.glebwin.hashtable;

/**
 * Hash table from string to string.
 * Implemented with linked lists.
 */
public class HashTable {
    private List table[];
    private int keysNum;

    public HashTable() {
        table = new List[10];
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
        return get(key) != null;
    }

    /**
     * @return Corresponding value or null if the key doesn't exist
     */
    public String get(String key) {
        List list = getTargetList(key);
        return list.findByKey(key);
    }

    /**
     * Add pair key-value to hash table
     * If the number of keys reaches the number of lists
     * it doubles the number of lists and reallocate all data
     * @return If the key already exists return its previous value or null otherwise
     */
    public String put(String key, String value) {
        List list = getTargetList(key);

        String oldValue = list.insertUniqueKey(key, value);

        if (oldValue == null) {
            keysNum++;
            if (keysNum >= table.length) {
                resize(table.length * 2);
            }
        }

        return oldValue;
    }

    /**
     * Remove the key from hash table and return its value
     * @param key the key to remove
     * @return Corresponding value or null if the key doesn't exists
     */
    public String remove(String key) {
        List list = getTargetList(key);

        String deletedValue = list.removeByKey(key);

        if (deletedValue != null) {
            keysNum--;
        }

        return deletedValue;
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
            for (List.ListEntry node = list.getHead(); node != null; node = node.getNext()) {
                String key = node.getKey();
                String value = node.getValue();
                getTargetList(key).insertUniqueKey(key, value);
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
    private List getTargetList(String key) {
        return table[(key.hashCode() % table.length + table.length) % table.length];
    }
}
