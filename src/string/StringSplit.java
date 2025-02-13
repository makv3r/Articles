package string;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringSplit {

    // Split using String split method with regex.
    public static String[] splitM1(String input, String delimiter) {
        if (input == null || delimiter == null) return new String[0];

        return input.split(Pattern.quote(delimiter));
    }

    //  Split using StringTokenizer
    public static String[] splitM2(String input, String delimiter) {
        if (input == null || delimiter == null) return new String[0];

        StringTokenizer tokenizer = new StringTokenizer(input, delimiter);
        List<String> list = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            list.add(tokenizer.nextToken());
        }

        return list.toArray(new String[0]); // Convert an ArrayList of String to a String Array
    }

    // Split using String methods: indexOf and substring. (Manual approach)
    public static String[] splitM3(String input, String delimiter) {
        if (input == null || delimiter == null) return new String[0];

        List<String> list = new ArrayList<>();
        int index = 0;
        do {
            int lastIndex = index;
            index = input.indexOf(delimiter, index + 1);
            if (lastIndex != index) {
                String element = input.substring(
                        lastIndex == 0 ? 0 : lastIndex + delimiter.length(),
                        index > 0 ? index : input.length()
                );
                if (!element.equals(delimiter) && !element.isEmpty()) {
                    list.add(element);
                }
            }
        } while (index >= 0);

        return list.toArray(new String[0]); // Convert an ArrayList of String to a String Array
    }

    // Split using Pattern split method with regex.
    public static String[] splitM4(String input, String delimiter) {
        if (input == null || delimiter == null) return new String[0];

        return Pattern.compile(Pattern.quote(delimiter)).split(input);
    }

    // Split using Scanner and Pattern quote.
    public static String[] splitM5(String input, String delimiter) {
        if (input == null || delimiter == null) return new String[0];

        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter(Pattern.quote(delimiter));
            while (scanner.hasNext()) {
                String element = scanner.next();
                if (!element.isEmpty()) {
                    list.add(element);
                }
            }
        }

        return list.toArray(new String[0]); // Convert an ArrayList of String to a String Array
    }

    public static String[] splitM6(String input, String delimiter) {
        if (input == null || delimiter == null) return new String[0];

        return Stream.of(input.split(Pattern.quote(delimiter)))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        String input = "apple,banana,orange";
        String delimiter = ",";

        System.out.println("Method splitM1 " + Arrays.toString(StringSplit.splitM1(input, delimiter)));
        System.out.println("Method splitM2 " + Arrays.toString(StringSplit.splitM2(input, delimiter)));
        System.out.println("Method splitM3 " + Arrays.toString(StringSplit.splitM3(input, delimiter)));
        System.out.println("Method splitM4 " + Arrays.toString(StringSplit.splitM4(input, delimiter)));
        System.out.println("Method splitM5 " + Arrays.toString(StringSplit.splitM5(input, delimiter)));
        System.out.println("Method splitM6 " + Arrays.toString(StringSplit.splitM6(input, delimiter)));
    }
}
