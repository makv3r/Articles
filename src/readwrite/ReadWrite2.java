package readwrite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Optimized ways to read and write a string to a file using standard Java IO APIs.
 */

public class ReadWrite2 {

    // Write to a file using the NIO Files and Path API
    public static void writeFileUsingFiles(String filename, String text) throws IOException {
        Files.writeString(Path.of(filename), text);
    }

    // Read from a file using the NIO Files and Path API
    public static String readFileUsingFiles(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }

    // Write binary to a file using the NIO Files and Path API
    public static void writeBinaryUsingFiles(String filename, String text) throws IOException {
        Files.write(Path.of(filename), text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // Read binary from a file using the NIO Files and Path API
    public static byte[] readBinaryUsingFiles(String filename) throws IOException {
        return Files.readAllBytes(Path.of(filename));
    }

    // Write to a file using BufferedWriter and FileWriter
    public static void writeFileBuffered(String filename, String text) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            bufferedWriter.write(text);
        }
    }

    // Read from a file line by line using BufferedReader and FileReader
    public static String readFileBuffered(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return sb.toString().trim(); // Remove trailing newline
    }

    // Write to a file using OutputStream
    public static void writeFileUsingOutputStream(String filename, String text) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(filename)) {
            outputStream.write(text.getBytes());
        }
    }

    // Read from a file using InputStream
    public static String readFileUsingInputStream(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(filename)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, bytesRead));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String filename = "file.txt";
        String text = "This is a test for file read/write operations in Java.";

        try {
            // Using Files API
            writeFileUsingFiles(filename, text);
            System.out.println("Files API Read:\n" + readFileUsingFiles(filename));

            // Using BufferedWriter and BufferedReader
            writeFileBuffered(filename, text);
            System.out.println("Buffered Read:\n" + readFileBuffered(filename));

            // Using OutputStream and InputStream
            writeFileUsingOutputStream(filename, text);
            System.out.println("Stream Read:\n" + readFileUsingInputStream(filename));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}