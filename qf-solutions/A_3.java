import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long a = scanner.nextInt();
        long b = scanner.nextInt();
        long n = scanner.nextInt();
        float temp = (float) (n - b) / (b - a);
        if (temp - (long) temp >= (float) 0.5) {
            temp = (long) temp + 1;
        } else {
            temp = (long) temp;
        }
        long result = (long) temp * 2 + 1;
        System.out.println(result);
    }
}