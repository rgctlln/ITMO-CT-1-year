public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String arg : args) {
            int start = -1;
            int end = -1;
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < arg.length(); j++) {
                char chr = arg.charAt(j);
                if (Character.isDigit(chr) || chr == '-') {
                    if (start == -1) {
                        start = j;
                    }
                    end = j;
                } else if (start != -1) { 
                    builder = new StringBuilder(arg.substring(start, end + 1));
                    sum += Integer.parseInt(builder.toString());
                    start = -1; 
                    builder = new StringBuilder();
                }
            }
            if (start != -1) {
                builder = new StringBuilder(arg.substring(start, end + 1));
                sum += Integer.parseInt(builder.toString());
            }
        }
        System.out.println(sum);
    }
}