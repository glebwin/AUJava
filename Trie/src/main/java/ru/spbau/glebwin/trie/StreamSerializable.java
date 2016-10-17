package ru.spbau.glebwin.trie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for objects that can be serialized/deserialized to/from stream.
 */
public interface StreamSerializable {
    /**
     * Serializes to stream.
     */
    void serialize(OutputStream out) throws IOException;

    /**
     * Deserializes from stream.
     */
    void deserialize(InputStream in) throws IOException, ClassNotFoundException;
}
