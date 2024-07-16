public class SumDouble {
    public static void main(String[] args) {
        double sum = 0;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < args.length; i++) {
            int start = -1;
            int end = -1;
            for (int j = 0; j < args[i].length(); j++) {
                char chr = args[i].charAt(j);
                if (Character.isWhitespace(chr) == false) {
                    if (start == -1) {
                        start = j;
                    }
                    end = j;
                } else if (start != -1) {
                    builder = new StringBuilder(args[i].substring(start,end + 1));
                    sum += Double.parseDouble(builder.toString());
                    start = -1;
                    builder = new StringBuilder();
                }
            }
            if (start != -1) {
                builder = new StringBuilder(args[i].substring(start,end + 1));
                sum += Double.parseDouble(builder.toString());
            }
        }
        System.out.println(sum);
    }
}