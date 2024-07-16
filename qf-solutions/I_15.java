import java.util.Scanner;

public class I {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int xl = Integer.MAX_VALUE;
        int xr = Integer.MIN_VALUE;
        int yl = Integer.MAX_VALUE;
        int yr = Integer.MIN_VALUE;
        int h = 0;
        for (int i = 0; i < n; i++) {
            int xi = scanner.nextInt();
            int yi = scanner.nextInt();
            int hi = scanner.nextInt();
            if (xi - hi <= xl) {
                xl = xi - hi;
            }
            if (xi + hi >= xr) {
                xr = xi + hi;
            }
            if (yi - hi <= yl) {
                yl = yi - hi;
            }
            if (yi + hi >= yr) {
                yr = yi + hi;
            }
        }
        int x = (int) Math.ceil((double) (xl + xr) / 2);
        int y = (int) Math.ceil((double) (yl + yr) / 2);
        h = (int) Math.ceil((double) Math.max((xr - xl), (yr - yl)) / 2);
        System.out.println(x + " " + y + " " + h);
    }
}
