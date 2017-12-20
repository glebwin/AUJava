package ru.spbau.glebwin.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Zip {
    /**
     * Recursively finds all zip archives at the specified path
     * and extract all contained files that match the specified regular expression.
     */
    public static void unzipAtPathWithRegex(String path, String regex) {
        Pattern pattern = Pattern.compile(regex);
        try {
            Files.walk(Paths.get(path))
                    .filter(p -> p.toString().endsWith(".zip"))
                    .forEach(p -> unzipArchiveContentsMatchingRegex(p, pattern));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Extract all files from the zip archive that match the specified pattern.
     */
    private static void unzipArchiveContentsMatchingRegex(Path zipPath, Pattern pattern) {
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(zipPath.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String relativePathInZip = entry.getName();
            String fileName = new File(relativePathInZip).getName();
            if (!entry.isDirectory() && pattern.matcher(fileName).matches()) {
                try {
                    InputStream inputStream = zipFile.getInputStream(entry);
                    Path absolutePath = zipPath.getParent().resolve(relativePathInZip);
                    Files.createDirectories(absolutePath.getParent());
                    Files.copy(inputStream, absolutePath);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
