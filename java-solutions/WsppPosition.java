import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
//import java.util.Scanner;

public class WsppPosition {
    public static void main(String[] args) throws IOException {
        Map<String, ArrayList<Integer>> map = new LinkedHashMap<>();
        Scanner in = null;
        int lineNumber = 1;
        try {
//            in = new Scanner(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
//            String word;
//            int lineNumber = 1;
//            String[] words = new String[1];
//            int wordslen = 0;
//            int wordNumber = 0;
//            while (in.hasNextWord()) {
//                word = in.nextWord();
//                if (Objects.equals(word, System.lineSeparator())) {
//                    words = Arrays.copyOf(words, wordslen);
//                    for (int i = 0; i < words.length; i++) {
//                        if (!word.isEmpty()) {
//                            if (!map.containsKey(words[i])) {
//                                map.put(words[i], new ArrayList<Integer>());
//                                map.get(words[i]).add(lineNumber);
//                                map.get(words[i]).add(wordNumber - i);
//                            } else {
//                                map.get(words[i]).add(lineNumber);
//                                map.get(words[i]).add(wordNumber - i);
//                            }
//                        }
//                    }
//                    words = new String[1];
//                    wordslen = 0;
//                    wordNumber = 0;
//                    lineNumber++;
//                    continue;
//                }
//                if (words.length == wordslen) {
//                    String[] brr;
//                    brr = Arrays.copyOf(words, words.length * 2);
//                    words = brr;
//                }
//                if (!word.isEmpty()) {
//                    words[wordslen] = word.toLowerCase().trim();
//                    wordslen++;
//                    wordNumber++;
//                }
//            }
//            words = Arrays.copyOf(words, wordslen);
//            for (int i = 0; i < words.length; i++) {
//                if (!map.containsKey(words[i])) {
//                    map.put(words[i], new ArrayList<Integer>());
//                    map.get(words[i]).add(lineNumber);
//                    map.get(words[i]).add(wordNumber - i);
//                } else {
//                    map.get(words[i]).add(lineNumber);
//                    map.get(words[i]).add(wordNumber - i);
//                }
//            }
            int wordNumber = 0;
            in = new Scanner(new InputStreamReader(new FileInputStream((args[0])), StandardCharsets.UTF_8));
            String line;
            String word;
            String[] words = new String[1];
            int wordslen = 0;
            while (in.hasNextLine()) {
                wordNumber = 0;
                line = in.nextLine();
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
                            wordNumber++;
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
                    wordNumber++;
                    wordslen++;
                }
                words = Arrays.copyOf(words, wordslen);
                for (int i = 0; i < words.length; i++) {
                    if (!map.containsKey(words[i])) {
                        map.put(words[i], new ArrayList<Integer>());
                        map.get(words[i]).add(lineNumber);
                        map.get(words[i]).add(wordNumber - i);
                    } else {
                        map.get(words[i]).add(lineNumber);
                        map.get(words[i]).add(wordNumber - i);
                    }
                }
                words = new String[1];
                wordslen = 0;
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Input file not found");
            throw new RuntimeException(e);
        } finally {
            in.close();
        }
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            for (Map.Entry<String, ArrayList<Integer>> map1 : map.entrySet()) {
                out.write(map1.getKey() + " " + map1.getValue().size() / 2);
                if (map1.getValue().size() >= 2) {
                    for (int j = 0; j < map1.getValue().size() - 1; j += 2) {
                        out.write(" " + map1.getValue().get(j) + ":" + map1.getValue().get(j + 1));
                    }
                } else {
                    out.write(" " + map1.getValue().get(0) + ":" + map1.getValue().get(1));
                }
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("Output file not found " + e.getMessage());
        }
    }
}