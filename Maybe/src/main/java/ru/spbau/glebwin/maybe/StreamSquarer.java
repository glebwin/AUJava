package ru.spbau.glebwin.maybe;

import java.io.*;

/**
 * Reads integers from stream and writes them squared to another stream.
 */
public class StreamSquarer {
    /**
     * Tries to parse inputStream's lines as integers.
     * Writes to outputStream their squared values or "null" for non-integer lines.
     */
    public static void process(InputStream inputStream, OutputStream outputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            for (String line; (line = reader.readLine()) != null; ) {
                Maybe<Integer> lineData = parseLine(line);
                lineData = lineData.map(x -> x * x);

                if (lineData.isPresent()) {
                    writer.write(lineData.get().toString());
                } else {
                    writer.write("null");
                }
                writer.newLine();
            }
        }
    }

    /**
     * @return Instance of Maybe that contains Integer if line is a valid integer.
     */
    private static Maybe<Integer> parseLine(String line) {
        try {
            return Maybe.just(Integer.parseInt(line));
        } catch (NumberFormatException exception) {
            return Maybe.nothing();
        }
    }
}
