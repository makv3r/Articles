package readwrite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Improved version of file read/write methods with benchmarking.
 */

public class ReadWriteImproved {

    public static void writeUsingFiles(String filename, String text) throws IOException {
        Files.writeString(Path.of(filename), text, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static String readUsingFiles(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }

    public static void bufferedFileWriterImproved(String filename, String text) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            bufferedWriter.write(text);
        }
    }

    public static String bufferedFileReaderImproved(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            char[] buffer = new char[1024];
            int charsRead;
            while ((charsRead = bufferedReader.read(buffer)) != -1) {
                sb.append(buffer, 0, charsRead);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String filename = "file.txt";
        String text = "This is a benchmark test for file read/write operations in Java.";

        long start, end;

        // Test Files.writeString and Files.readString
        start = System.nanoTime();
        writeUsingFiles(filename, text);
        end = System.nanoTime();
        System.out.println("Files.writeString: " + (end - start) + " ns");

        start = System.nanoTime();
        System.out.println(readUsingFiles(filename));
        end = System.nanoTime();
        System.out.println("Files.readString: " + (end - start) + " ns");

        // Test BufferedWriter and BufferedReader
        start = System.nanoTime();
        bufferedFileWriterImproved(filename, text);
        end = System.nanoTime();
        System.out.println("BufferedWriter: " + (end - start) + " ns");

        start = System.nanoTime();
        System.out.println(bufferedFileReaderImproved(filename));
        end = System.nanoTime();
        System.out.println("BufferedReader: " + (end - start) + " ns");
    }
}
