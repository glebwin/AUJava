package ru.spbau.glebwin.hashtable;

/**
 * Created by glebwin on 9/11/16.
 */
public class HashTableEntry {
    private String key;
    private String value;

    public HashTableEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
