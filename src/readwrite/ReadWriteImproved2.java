package readwrite;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Improved version of file read/write methods with benchmarking.
 */
public class ReadWriteImproved2 {

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

    public static void writeBinaryUsingFiles(String filename, byte[] data) throws IOException {
        Files.write(Path.of(filename), data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static byte[] readBinaryUsingFiles(String filename) throws IOException {
        return Files.readAllBytes(Path.of(filename));
    }

    public static void bufferedBinaryWrite(String filename, byte[] data) throws IOException {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename))) {
            bufferedOutputStream.write(data);
        }
    }

    public static byte[] bufferedBinaryRead(String filename) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filename))) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    public static void writeUsingFileChannel(String filename, byte[] data) throws IOException {
        try (FileChannel channel = new FileOutputStream(filename).getChannel()) {
            channel.write(ByteBuffer.wrap(data));
        }
    }

    public static byte[] readUsingFileChannel(String filename) throws IOException {
        try (FileChannel channel = new FileInputStream(filename).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            return buffer.array();
        }
    }

    public static void multiThreadedWrite(String filename, byte[] data) throws InterruptedException {
        int numThreads = 4;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int chunkSize = data.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? data.length : start + chunkSize;
            executor.submit(() -> {
                try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
                    raf.seek(start);
                    raf.write(data, start, end - start);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String textFilename = "file.txt";
        String binaryFilename = "file.bin";
        String text = "This is a benchmark test for file read/write operations in Java.";
        byte[] binaryData = new byte[1024 * 1024]; // Example binary data (1MB)
        for (int i = 0; i < binaryData.length; i++) {
            binaryData[i] = (byte) (i % 256);
        }

        long start, end;

        // Test Files.writeString and Files.readString
        start = System.nanoTime();
        writeUsingFiles(textFilename, text);
        end = System.nanoTime();
        System.out.println("Files.writeString: " + (end - start) + " ns");

        start = System.nanoTime();
        System.out.println(readUsingFiles(textFilename));
        end = System.nanoTime();
        System.out.println("Files.readString: " + (end - start) + " ns");

        // Test BufferedWriter and BufferedReader
        start = System.nanoTime();
        bufferedFileWriterImproved(textFilename, text);
        end = System.nanoTime();
        System.out.println("BufferedWriter: " + (end - start) + " ns");

        start = System.nanoTime();
        System.out.println(bufferedFileReaderImproved(textFilename));
        end = System.nanoTime();
        System.out.println("BufferedReader: " + (end - start) + " ns");

        // Test Files.write and Files.read for binary data
        start = System.nanoTime();
        writeBinaryUsingFiles(binaryFilename, binaryData);
        end = System.nanoTime();
        System.out.println("Files.write (binary): " + (end - start) + " ns");

        start = System.nanoTime();
        readBinaryUsingFiles(binaryFilename);
        end = System.nanoTime();
        System.out.println("Files.read (binary): " + (end - start) + " ns");

        // Test BufferedOutputStream and BufferedInputStream for binary data
        start = System.nanoTime();
        bufferedBinaryWrite(binaryFilename, binaryData);
        end = System.nanoTime();
        System.out.println("BufferedOutputStream: " + (end - start) + " ns");

        start = System.nanoTime();
        bufferedBinaryRead(binaryFilename);
        end = System.nanoTime();
        System.out.println("BufferedInputStream: " + (end - start) + " ns");

        // Test FileChannel for binary data
        start = System.nanoTime();
        writeUsingFileChannel(binaryFilename, binaryData);
        end = System.nanoTime();
        System.out.println("FileChannel write: " + (end - start) + " ns");

        start = System.nanoTime();
        readUsingFileChannel(binaryFilename);
        end = System.nanoTime();
        System.out.println("FileChannel read: " + (end - start) + " ns");

        // Test Multi-threaded write for binary data
        start = System.nanoTime();
        multiThreadedWrite(binaryFilename, binaryData);
        end = System.nanoTime();
        System.out.println("Multi-threaded write: " + (end - start) + " ns");
    }
}