import java.io.IOException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Reverse {
    public static void main(String[] args) throws IOException {
        int i = 0;
        int c;
        int lineNumber = 0;
        int[][] arr = new int[100][];
        Scanner sc = new Scanner(new InputStreamReader(System.in));
        while (sc.hasNextLine()) {
            if (lineNumber == arr.length) {
                arr = Arrays.copyOf(arr, arr.length * 2);
            }
            lineNumber++;
            c = 0;
            Scanner scanner2 = new Scanner(sc.nextLine());
            int[] newArr = new int[1];
            while (scanner2.hasNextInt()) {
                if (c == newArr.length) {
                    int[] brr = new int[newArr.length * 2];
                    brr = Arrays.copyOf(newArr, newArr.length * 2);
                    newArr = brr;
                }
                newArr[c] = scanner2.nextInt();
                c++;
            }
            arr[i] = Arrays.copyOf(newArr, c);
            i++;
            scanner2.close();
        }
        arr = Arrays.copyOf(arr, i);
        for (i = (arr.length - 1); i >= 0; i--) {
            for (c = (arr[i].length - 1); c >= 0; c--) {
                System.out.print(arr[i][c] + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}