import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReverseMinR {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        List<List<Integer>> list = new ArrayList<>();
        while (sc.hasNextLine()) {
            Scanner scanner2 = new Scanner(sc.nextLine());
            List<Integer> line = new ArrayList<>();
            while (scanner2.hasNextInt()) {
                line.add(scanner2.nextInt());
            }
            list.add(line);
            scanner2.close();
        }
        for (int j = 0; j < list.size(); j++) {
            int mini = Integer.MAX_VALUE;
            for (int k = 0; k < list.get(j).size(); k++) {
                mini = Math.min(mini, list.get(j).get(k));
                System.out.print(mini + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}
