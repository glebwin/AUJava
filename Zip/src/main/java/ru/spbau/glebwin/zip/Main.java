package ru.spbau.glebwin.zip;

/**
 * Program accepts path and regular expression.
 * It recursively finds all zip archives at the specified path
 * and extract all contained files that match the specified regular expression.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Incorrect number of arguments");
            return;
        }
        Zip.unzipAtPathWithRegex(args[0], args[1]);
    }
}
