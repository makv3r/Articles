package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {

    private FileUtils() {
    }

    public static void writeFile(String filename, String text) throws IOException {
        Files.writeString(Path.of(filename), text);
    }

    public static String readFile(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }
}
