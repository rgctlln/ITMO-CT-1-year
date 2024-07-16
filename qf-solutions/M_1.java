import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class M {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int f = 0; f < t; f++) {
            int n = scanner.nextInt();
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) {
                arr[j] = scanner.nextInt();
            }
            int result = 0;
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = n - 1; j >= 0; j--) {
                for (int i = 0; i < j; i++) {
                    result += map.getOrDefault(2 * arr[j] - arr[i], 0);
                }
                map.put(arr[j], map.getOrDefault(arr[j], 0) + 1);
            }
            System.out.println(result);
        }
    }
}
