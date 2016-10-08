package ru.spbau.glebwin.maybe;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class StreamSquarerTest {
    @Test
    public void process() throws Exception {
        ByteArrayOutputStream squarerInput = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(squarerInput);
        String[] inputData = {"542", "-14", "", "0", "412x1", "51", "253", "253-"};
        String[] expectedOutput = {String.valueOf(542 * 542), String.valueOf((-14) * (-14)), "null", "0",
                "null", String.valueOf(51 * 51), String.valueOf(253 * 253), "null"};

        for (String string : inputData) {
            printWriter.println(string);
        }
        printWriter.close();

        ByteArrayOutputStream squareOutput = new ByteArrayOutputStream();
        StreamSquarer.process(new ByteArrayInputStream(squarerInput.toByteArray()), squareOutput);

        BufferedReader checkReader = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(squareOutput.toByteArray())));
        for (String string : expectedOutput) {
            assertEquals(string, checkReader.readLine());
        }
    }
}