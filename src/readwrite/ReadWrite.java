package readwrite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * All possible way to read and write to a string file with standard Java IO api.
 */

public class ReadWrite {

    public static void fileWriterUsingFiles(String filename, String text) throws IOException {
        Files.writeString(Path.of(filename), text);
    }

    public static String fileReaderUsingFiles(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }

    public static void fileWriter(String filename, String text) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(text);
        }
    }

    public static String fileReader(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileReader fileReader = new FileReader(filename)) {
            int ch = fileReader.read();
            while (ch != -1) {
                sb.append((char) ch);
                ch = fileReader.read();
            }
        }
        return sb.toString();
    }

    public static void bufferedFileWriter(String filename, String text) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            bufferedWriter.write(text);
        }
    }

    public static String bufferedFileReader(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                sb.append(line);
                line = bufferedReader.readLine();
            }
        }
        return sb.toString();
    }

    public static void fileOutputStream(String filename, String text) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            fileOutputStream.write(text.getBytes());
        }
    }

    public static String fileInputStream(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            int ch = fileInputStream.read();
            while (ch != -1) {
                sb.append((char) ch);
                ch = fileInputStream.read();
            }
        }
        return sb.toString();
    }

    public static void bufferedFileOutputStream(String filename, String text) throws IOException {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename))) {
            bufferedOutputStream.write(text.getBytes());
        }
    }

    public static String bufferedFileInputStream(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, bytesRead));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String filename = "file.txt";
        String text = "This is a test for file read/write operations in Java.";

        fileWriterUsingFiles(filename, text);
        System.out.println(fileReaderUsingFiles(fileReader(filename)));

        fileWriter(filename, text);
        System.out.println(fileReader(filename));

        bufferedFileWriter(filename, text);
        System.out.println(bufferedFileReader(filename));

        fileOutputStream(filename, text);
        System.out.println(fileInputStream(filename));

        bufferedFileOutputStream(filename, text);
        System.out.println(bufferedFileInputStream(filename));
    }
}