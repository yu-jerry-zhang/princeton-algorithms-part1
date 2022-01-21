import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        double i = 1;
        while (!StdIn.isEmpty()) {
            double probability = 1 / i;
            String string = StdIn.readString();
            if (StdRandom.bernoulli((probability))) {
                champion = string;
            }
            i++;
        }
        StdOut.println(champion);
    }
}