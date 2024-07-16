import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatCountPrefixL {
    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<>();
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
                if (words[i].length() >= 3) {
                    String str = words[i].toLowerCase().substring(0, 3);
                    map.put(str, map.getOrDefault(str, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Input file not found " + e.getMessage());
        }
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            String[] words = new String[1];
            int[] val = new int[1];
            int vallen = 0;
            int wordslen = 0;
            for (Map.Entry<String, Integer> map1 : map.entrySet()) {
                if (val.length == vallen) {
                    int[] brr;
                    brr = Arrays.copyOf(val, val.length * 2);
                    val = brr;
                }
                if (words.length == wordslen) {
                    String[] crr;
                    crr = Arrays.copyOf(words, words.length * 2);
                    words = crr;
                }
                val[vallen] = map1.getValue();
                words[wordslen] = map1.getKey();
                vallen++;
                wordslen++;
            }
            val = Arrays.copyOf(val, vallen);
            words = Arrays.copyOf(words, wordslen);
            int len = val.length;
            while (len > 0) {
                int newlen = 0;
                for (int i = 1; i < val.length; i++) {
                    if (val[i - 1] > val[i]) {
                        int d = val[i];
                        val[i] = val[i - 1];
                        val[i - 1] = d;
                        String str = words[i];
                        words[i] = words[i - 1];
                        words[i - 1] = str;
                        newlen = i;
                    }
                }
                if (newlen == 0) {
                    break;
                }
                len = newlen;
            }
            for (int i = 0; i < val.length; i++) {
                out.write(words[i] + " " + val[i]);
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("Output file not found " + e.getMessage());
        }
    }
}