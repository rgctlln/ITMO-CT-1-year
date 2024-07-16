import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long a = scanner.nextInt();
        long b = scanner.nextInt();
        long n = scanner.nextInt();
        double temp = ((double) (n - b)) / (b - a);
        if (temp < 1) {
            temp = 1;
        } else {
            if (temp - (long) temp >= 0.5) {
                temp = (long) temp + 1;
            } else {
                temp = (long) temp;
            }
        }
        long result = (long) temp * 2 + 1;
        System.out.println(result);
    }
}