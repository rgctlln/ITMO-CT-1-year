import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

public class Scanner {
    private Reader reader;
    private char[] buffer = new char[1024];
    private int currentPosition = 0;
    private int val = 0;
    private StringBuilder builderForInts = new StringBuilder();
    private StringBuilder builderForStrings = new StringBuilder();
    private StringBuilder builderForWords = new StringBuilder();
    private String value;
    private StringBuilder word = new StringBuilder();
    private String result;

    public Scanner(Reader reader) {
        this.reader = reader;
    }

    public Scanner(InputStream in) throws IOException {
        this.reader = new InputStreamReader(in);
    }

    public Scanner(File file, Charset charsetName) throws IOException {
        try {
            this.reader = new InputStreamReader(new FileInputStream(file), charsetName);
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found");
        }
    }

    public Scanner(String str) {
        this.reader = new StringReader(str);
    }

    public void close() throws IOException {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("IOException while closing scanner");
        }
    }

    public boolean readBuffer() throws IOException {
        if (currentPosition >= val) {
            currentPosition = 0;
            val = reader.read(buffer);
        }
        return val != -1;
    }

    public boolean hasNextInt() throws IOException {
        builderForInts.setLength(0);
        while (readBuffer()) {
            if ((Character.isDigit(buffer[currentPosition])) || (builderForInts.isEmpty() &&
                    Objects.equals(buffer[currentPosition], '-'))) {
                builderForInts.append(buffer[currentPosition]);
            } else if (!(builderForInts.isEmpty()) && !(Character.isDigit(buffer[currentPosition]))) {
                this.value = String.valueOf(builderForInts);
                currentPosition++;
                return true;
            }
            currentPosition++;
        }
        if (!builderForInts.isEmpty()) {
            this.value = builderForInts.toString();
            return true;
        }
        return false;
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(value);
    }

    //    public boolean hasNextLine() throws IOException {
//        builderForStrings.setLength(0);
//        while (readBuffer()) {
//            if (buffer[currentPosition] == System.lineSeparator().charAt(System.lineSeparator().length() - 1)) {
//                this.result = String.valueOf(builderForStrings);
//                currentPosition++;
//                return true;
//            }
//            builderForStrings.append(buffer[currentPosition]);
//            currentPosition++;
//        }
//        return false;
//    }
    public boolean hasNextLine() throws IOException {
        builderForStrings.setLength(0);
        StringBuilder separator = new StringBuilder(System.lineSeparator());
        StringBuilder check = new StringBuilder();
        int index = 0;
        while (readBuffer()) {
            if (index != System.lineSeparator().length() && buffer[currentPosition] == separator.charAt(index)) {
                index++;
                check.append(buffer[currentPosition]);
            }
            if (check.toString().contentEquals(separator)) {
                this.result = String.valueOf(builderForStrings);
                currentPosition++;
                check.setLength(0);
                return true;
            }
            builderForStrings.append(buffer[currentPosition]);
            currentPosition++;
        }
        if (!builderForStrings.isEmpty()) {
            this.result = builderForStrings.toString();
            return true;
        }
        return false;
    }

    public String nextLine() throws IOException {
        return result;
    }

    public boolean hasNextWord() throws IOException {
        builderForWords.setLength(0);
        StringBuilder separator = new StringBuilder(System.lineSeparator());
        StringBuilder check = new StringBuilder();
        int index = 0;
        while (readBuffer()) {
            if (index != System.lineSeparator().length() && buffer[currentPosition] == separator.charAt(index)) {
                index++;
                check.append(buffer[currentPosition]);
            }
            if (check.toString().contentEquals(separator)) {
                builderForWords.append(check);
                this.word = new StringBuilder(String.valueOf(builderForWords));
                currentPosition++;
                check.setLength(0);
                return true;
            }
            if ((Character.isLetter(buffer[currentPosition])) || buffer[currentPosition] == '\''
                    || Character.getType(buffer[currentPosition]) == Character.DASH_PUNCTUATION
                    || (builderForWords.isEmpty() &&
                    Objects.equals(buffer[currentPosition], '-'))) {
//                index = 0;
//                check.setLength(0);
                builderForWords.append(buffer[currentPosition]);
            } else if (!(builderForWords.isEmpty()) && !(Character.isLetter(buffer[currentPosition]))) {
                this.word = new StringBuilder(String.valueOf(builderForWords));
                //currentPosition++;
                return true;
            }
            currentPosition++;
        }
        if (!builderForWords.isEmpty()) {
            this.word = new StringBuilder(String.valueOf(builderForWords));
            return true;
        }
        return false;
    }
//        builderForWords.setLength(0);
//        StringBuilder separator = new StringBuilder(System.lineSeparator());
//        StringBuilder check = new StringBuilder();
//        int index = 0;
//        while (readBuffer()) {
//            if (index != System.lineSeparator().length() && buffer[currentPosition] == separator.charAt(index)) {
//                index++;
//                check.append(buffer[currentPosition]);
//            }
//            if (check.toString().contentEquals(separator)) {
//                this.word = builderForWords;
//                currentPosition++;
//                check.setLength(0);
//                return true;
//            }
//            if ((Character.isLetter(buffer[currentPosition])) || Character.getType(buffer[currentPosition]) == Character.DASH_PUNCTUATION
//                    || buffer[currentPosition] == '\'' || (builderForWords.isEmpty() &&
//                    Objects.equals(buffer[currentPosition], '-'))) {
//                builderForWords.append(buffer[currentPosition]);
//            } else if (!(builderForWords.isEmpty()) && (Character.isWhitespace(buffer[currentPosition]))) {
//                this.word = builderForWords;
//                currentPosition++;
//                return true;
//            }
//            currentPosition++;
//        }
//        if (!builderForWords.isEmpty()) {
//            this.word = builderForWords;
//            return true;
//        }
//        return false;
//    }

    public String nextWord() throws IOException {
        String temp = String.valueOf(word);
        word.setLength(0);
        return temp;
    }

    public long swap(String str) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '-') {
                newString.append('-');
            } else if (ch >= 'a' && ch <= 'j') {
                int digit = ch - 'a';
                newString.append(digit);
            }
        }
        return Long.parseLong(String.valueOf(newString));
    }

    public String reSwap(long shorty) {
        StringBuilder anotherString = new StringBuilder();
        String str = String.valueOf(shorty);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '-') {
                anotherString.append("-");
            } else if (Character.isDigit(c)) {
                char newChar = (char) (c - '0' + 'a');
                anotherString.append(newChar);
            }
        }
        return String.valueOf(anotherString);
    }
}


