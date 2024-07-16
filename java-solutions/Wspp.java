import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
//import java.util.Scanner;

public class Wspp {
    public static void main(String[] args) throws IOException {
        Map<String, ArrayList<Integer>> map = new LinkedHashMap<>();
        Scanner in = null;
        try {
//            in = new Scanner(new InputStreamReader(new FileInputStream((args[0])), StandardCharsets.UTF_8));
//            String word;
//            String[] words = new String[1];
//            int wordslen = 0;
//            while (in.hasNextWord()) {
//                word = in.nextWord();
//                if (Objects.equals(word, System.lineSeparator())) {
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
//                }
//            }
//            words = Arrays.copyOf(words, wordslen);
//            for (int i = 0; i < words.length; i++) {
//                //map.put(words[i], map.getOrDefault(words[i], 0) + 1);
//                if (!map.containsKey(words[i])) {
//                    map.put(words[i], new ArrayList<Integer>());
//                    map.get(words[i]).add(i + 1);
//                } else {
//                    map.get(words[i]).add(i + 1);
//                }
//            }
            in = new Scanner(new InputStreamReader(new FileInputStream((args[0])), StandardCharsets.UTF_8));
            String line;
            String word;
            String[] words = new String[1];
            int wordslen = 0;
            while (in.hasNextLine()) {
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
                //map.put(words[i], map.getOrDefault(words[i], 0) + 1);
                if (!map.containsKey(words[i])) {
                    map.put(words[i], new ArrayList<Integer>());
                    map.get(words[i]).add(i + 1);
                } else {
                    map.get(words[i]).add(i + 1);
                }
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
                out.write(map1.getKey() + " " + map1.getValue().size());
                for (int j = 0; j < map1.getValue().size(); j++) {
                    out.write(" " + map1.getValue().get(j));
                }
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println("Output file not found " + e.getMessage());
        }
    }
}