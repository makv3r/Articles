package prettyhtml;

import util.FileUtils;

import java.io.IOException;

public class TestPrettyHTML {
    public static void main(String[] args) {
        try {
            String html = FileUtils.readFile("input.txt");
            FileUtils.writeFile("output.txt", PrettyHTML.applyTidy(html));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
