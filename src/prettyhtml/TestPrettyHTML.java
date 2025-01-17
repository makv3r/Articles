package prettyhtml;

import util.FileUtils;

import java.io.IOException;

public class TestPrettyHTML {
    private final static String html = """
            
            """;

    public static void main(String[] args) {
        try {
            FileUtils.writeFile("output1.txt", html);
            FileUtils.writeFile("output2.txt", PrettyHTML.applyTidy(html));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
