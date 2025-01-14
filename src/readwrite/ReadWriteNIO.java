package readwrite;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ReadWriteNIO {
    // Write to a file using the NIO Files.writeString and Path API
    public static void writeFileUsingFiles(String filename, String text) throws IOException {
        Files.writeString(Path.of(filename), text);
    }

    // Read from a file using the NIO Files.readString and Path API
    public static String readFileUsingFiles(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }

    // Write to a file by lines using Files.write (text-based)
    public static void writeFileByLineUsingFiles(String filename, List<String> lines) throws IOException {
        Files.write(Path.of(filename), lines);
    }

    // Read from a file as lines using Files.readAllLines (text-based)
    public static List<String> readFileUsingFilesReadAllLines(String filename) throws IOException {
        return Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8);
    }

    // Write binary to a file using the NIO Files and Path API
    public static void writeBinaryUsingFiles(String filename, String text) throws IOException {
        Files.write(Path.of(filename), text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // Read binary from a file using the NIO Files and Path API
    public static byte[] readBinaryUsingFiles(String filename) throws IOException {
        return Files.readAllBytes(Path.of(filename));
    }

    // Write to a file using NIO FileChannel (binary or text-based)
    public static void writeUsingFileChannel(String filename, String text) throws IOException {
        try (FileChannel fileChannel = FileChannel.open(Path.of(filename), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            // Split the content into chunks and write each chunk
            final int CHUNK_SIZE = 4096;
            byte[] contentBytes = text.getBytes(StandardCharsets.UTF_8);
            int totalSize = contentBytes.length;
            int offset = 0;

            while (offset < totalSize) {
                int remaining = totalSize - offset;
                int writeSize = Math.min(CHUNK_SIZE, remaining);
                ByteBuffer buffer = ByteBuffer.wrap(contentBytes, offset, writeSize);
                fileChannel.write(buffer);
                offset += writeSize;
            }
        }
    }

    // Read from a file using NIO FileChannel (binary or text-based)
    public static String readUsingFileChannel(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileChannel fileChannel = FileChannel.open(Path.of(filename), StandardOpenOption.READ)) {
            final int BUFFER_SIZE = 4096; // Buffer size 4 KB
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                sb.append(new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8));
                buffer.clear();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String filename = "nio-file.txt";
        String text = "This is a test of Java NIO file read/write operations.";
        List<String> lines = List.of("Line 1", "Line 2", "Line 3");

        try {
            // Using Files API for text-based operations
            writeFileUsingFiles(filename, text);
            System.out.println("Read using Files.readString: " + readFileUsingFiles(filename));

            writeFileByLineUsingFiles(filename, lines);
            System.out.println("Read using Files.readAllLines: " + readFileUsingFilesReadAllLines(filename));

            // Using Files API for binary data
            writeBinaryUsingFiles(filename, text);
            System.out.println("Read using Files.readAllBytes: " + new String(readBinaryUsingFiles(filename), StandardCharsets.UTF_8));

            // Using FileChannel for advanced read/write
            writeUsingFileChannel(filename, text);
            System.out.println("Read using FileChannel: " + readUsingFileChannel(filename));

        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
