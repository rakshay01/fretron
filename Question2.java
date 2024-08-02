import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Question2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> weights = new ArrayList<>();
        
        System.out.println("Enter apple weight in grams (-1 to stop): ");
        while (true) {
            int w = sc.nextInt();
            if (w == -1) break;
            weights.add(w);
        }

        func(weights);

        sc.close();
    }

    public static void func(ArrayList<Integer> weights) {
        int total = 0;
        for (int w : weights) {
            total += w;
        }

        double rShare = total * 0.50;
        double sShare = total * 0.30;
        double hShare = total * 0.20;

        ArrayList<Integer> r = new ArrayList<>();
        ArrayList<Integer> s = new ArrayList<>();
        ArrayList<Integer> h = new ArrayList<>();

        double rCur = 0, sCur = 0, hCur = 0;

        Collections.sort(weights, Collections.reverseOrder());

        for (int w : weights) {
            if (rCur + w <= rShare) {
                r.add(w);
                rCur += w;
            } else if (sCur + w <= sShare) {
                s.add(w);
                sCur += w;
            } else {
                h.add(w);
                hCur += w;
            }
        }

        System.out.println("Distribution Result:");
        System.out.println("Ram: " + r);
        System.out.println("Sham: " + s);
        System.out.println("Rahim: " + h);
    }
}
