import java.io.IOException;
import java.util.Arrays;

public class ReverseMinRAbc {
    public static void main(String[] args) throws IOException {
        int i = 0;
        int c;
        long[][] arr = new long[1000000][];
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            if (i == arr.length) {
                long[][] brr = new long[arr.length * 2][];
                brr = Arrays.copyOf(arr, arr.length * 2);
                arr = brr;
            }
            c = 0;
            long[] newArr = new long[10];
            Scanner scanner2 = new Scanner(sc.nextLine());
            while (scanner2.hasNextWord()) {
                if (c == newArr.length) {
                    newArr = Arrays.copyOf(newArr, newArr.length * 2);
                }
                newArr[c] = scanner2.swap(scanner2.nextWord());
                c++;
            }
            arr[i] = Arrays.copyOf(newArr, c);
            i++;
        }
        arr = Arrays.copyOf(arr, i);
        for (i = 0; i < arr.length; i++) {
            long mini = Long.MAX_VALUE;
            for (c = 0; c < arr[i].length; c++) {
                if (arr[i][c] < mini) {
                    System.out.print(sc.reSwap(arr[i][c]) + " ");
                    mini = arr[i][c];
                } else {
                    System.out.print(sc.reSwap(mini) + " ");
                }
            }
            System.out.println();
        }
    }
}