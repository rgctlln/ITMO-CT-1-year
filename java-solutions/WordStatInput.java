import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8))) {
            String line;
            String word;
            String[] words = new String[1];
            int wordslen = 0;
            while ((line = in.readLine()) != null) {
                int start = 0;
                int end = 0;
                for (int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);
                    if (Character.isLetter(ch) || ch == '\'' || Character.getType(ch) == Character.DASH_PUNCTUATION) {
                        end++;
                    } else {
                        if (start != end) {
                            word = line.substring(start, end).trim().toLowerCase();
                            if (words.length == wordslen) {
                                String[] brr;
                                brr = Arrays.copyOf(words, words.length * 2);
                                words = brr;
                            }
                            words[wordslen] = word;
                            wordslen++;
                        }
                        start = i + 1;
                        end = i + 1;
                    }
                }
                if (start != end) {
                    word = line.substring(start, end).trim().toLowerCase();
                    if (words.length == wordslen) {
                        String[] brr;
                        brr = Arrays.copyOf(words, words.length * 2);
                        words = brr;
                    }
                    words[wordslen] = word;
                    wordslen++;
                }
                words = Arrays.copyOf(words, wordslen);
            }
            for (int i = 0; i < words.length; i++) {
                map.put(words[i], map.getOrDefault(words[i], 0) + 1);
            }
        } catch (IOException e) {
            System.out.println("Input file not found " + e.getMessage());
        }
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            for (Map.Entry<String, Integer> map1 : map.entrySet()) {
                out.write(map1.getKey() + " " + map1.getValue());
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("Output file not found " + e.getMessage());
        }
    }
}