package readwrite;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ThreadNIOExample {

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

        // Test Multi-threaded write for binary data
        start = System.nanoTime();
        multiThreadedWrite(binaryFilename, binaryData);
        end = System.nanoTime();
        System.out.println("Multi-threaded write: " + (end - start) + " ns");
    }
}