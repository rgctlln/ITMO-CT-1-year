package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class Md2Html {
    private static final StringBuilder builder = new StringBuilder();

    public static int isHeader(String str) {
        int count = 0;
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) != '#') {
                return count > 0 && Character.isWhitespace(str.charAt(i)) ? count : -1;
            }
            count++;
        }
        return -1;
    }

    public static String makeString(String str) {
        if (isHeader(str) != -1) {
            str = specialSymbols(str);
            str = quotes(str);
            str = code(str);
            str = toMarkdown(str);
            str = headerToHTML(str, isHeader(str));
        } else if (!str.isEmpty()) {
            str = specialSymbols(str);
            str = quotes(str);
            str = code(str);
            str = toMarkdown(str);
            str = "<p>" + str + "</p>";
        }
        return str;
    }

    public static String headerToHTML(String str, int level) {
        builder.setLength(0);
        builder.append("<h").append(level).append(">");
        builder.append(str, level + 1, str.length());
        builder.append("</h").append(level).append(">");
        return builder.toString();
    }

    public static String specialSymbols(String str) {
        builder.setLength(0);
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case '>':
                    builder.append("&gt;");
                    break;
                case '<':
                    builder.append("&lt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                default:
                    builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String code(String str) {
        boolean inCode = false;
        builder.setLength(0);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\\') {
                str = str.replace("\\", "");
            }
        }
        for (int i = 0; i < str.length(); i++) {
            if (i < str.length() - 1 && str.charAt(i) == '`' && str.charAt(i + 1) != '`') {
                if (!inCode) {
                    builder.append("<code>");
                } else {
                    builder.append("</code>");
                }
                inCode = !inCode;
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String toMarkdown(String str) {
        int startForStrikeout = -1;
        int startForStrong = -1;
        char memoryForStrikeout = 0;
        char memoryForStrong = 0;
        builder.setLength(0);
        for (int i = 0; i < str.length(); i++) {
            if (((str.charAt(i) == '*' && str.charAt(i + 1) != '*' && str.charAt(i + 1) != ' ' && str.charAt(i - 1) != '*') ||
                    (str.charAt(i) == '_' && str.charAt(i + 1) != '_' && str.charAt(i - 1) != '_')) && startForStrikeout == -1) {
                memoryForStrikeout = str.charAt(i);
                startForStrikeout = i;
            } else if (str.charAt(i) == memoryForStrikeout && str.charAt(i - 1) != '_'
                    && str.charAt(i - 1) != '*' && str.charAt(i + 1) != memoryForStrikeout && startForStrikeout != -1) {
                builder.append(str, 0, startForStrikeout).append("<em>").append(
                        str, startForStrikeout + 1, i).append("</em>").append(str, i + 1, str.length());
                str = builder.toString();
                builder.setLength(0);
                memoryForStrikeout = 0;
                startForStrikeout = -1;
            }
            if (((str.charAt(i) == '*' && str.charAt(i + 1) == '*') ||
                    (str.charAt(i) == '_' && str.charAt(i + 1) == '_')) && startForStrong == -1) {
                memoryForStrong = str.charAt(i);
                startForStrong = i;
            } else if (str.charAt(i) == memoryForStrong && str.charAt(i + 1) == memoryForStrong && startForStrong != -1) {
                builder.append(str, 0, startForStrong).append("<strong>").append(
                        str, startForStrong + 2, i).append("</strong>").append(str, i + 2, str.length());
                str = builder.toString();
                builder.setLength(0);
                memoryForStrong = 0;
                startForStrong = -1;
            }
        }
        return str;
    }

    public static String quotes(String str) {
        builder.setLength(0);
        boolean quoteStart = false;
        boolean EmphasisStart = false;
        for (int i = 0; i < str.length(); i++) {
            if (i != 0 && str.charAt(i) == '\'' && str.charAt(i - 1) == '\\') {
                builder.append("'");
            } else if (i < str.length() - 1 && str.charAt(i) == '\'' && str.charAt(i + 1) == '\'') {
                if (!quoteStart) {
                    builder.append("<q>");
                } else {
                    builder.append("</q>");
                }
                quoteStart = !quoteStart;
                i++;
            } else if (i < str.length() - 1 && str.charAt(i) == '-' && str.charAt(i + 1) == '-') {
                if (!EmphasisStart) {
                    builder.append("<s>");
                } else {
                    builder.append("</s>");
                }
                EmphasisStart = !EmphasisStart;
                i++;
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> buffer = new ArrayList<>();
        StringBuilder builderForFile = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(args[0]), StandardCharsets.UTF_8))) {
            String str;
            while ((str = reader.readLine()) != null) {
                if (Objects.equals(str, "")) {
                    str = builderForFile.toString();
                    builderForFile.setLength(0);
                    str = makeString(str);
                    buffer.add(str);
                } else {
                    if (!builderForFile.isEmpty()) {
                        builderForFile.append(System.lineSeparator());
                    }
                    builderForFile.append(str);
                }
            }
            if (!builderForFile.isEmpty()) {
                str = builderForFile.toString();
                str = makeString(str);
                buffer.add(str);
            }
        }
        try (BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            for (String line : buffer) {
                if (!line.isEmpty()) {
                    out.write(line);
                    out.newLine();
                }
            }
        }
    }
}
