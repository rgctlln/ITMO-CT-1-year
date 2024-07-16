import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int multiplier1 = -710;
        int multiplier2 = 25000;
        for (int i = 0; i < n; i++) {
            System.out.println(multiplier1 * multiplier2);
            multiplier2--;
        }
    }
}
