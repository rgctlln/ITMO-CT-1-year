import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        double temp = (double) (n - b) / (b - a);
        if (temp - (int) temp >= 0.5) {
            temp = (int) temp + 1;
        } else {
            temp = (int) temp;
        }
        int result = (int) temp * 2 + 1;
        System.out.println(result);
    }
}