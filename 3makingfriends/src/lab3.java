import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class lab3 {
    private PriorityQueue<Triplet> Edges;
    private int[] fathers;
    private int numberOgFathers;

    private lab3() {
        Edges = new PriorityQueue<>(Comparator.comparingInt(Triplet::getWeight));
        numberOgFathers = 0;
    }

    private void load() throws IOException {
  
        Scanner sc = new Scanner(System.in);
        fathers = new int[sc.nextInt() + 1];
        for (int i = 0; i < fathers.length - 1; i++) fathers[i] = i;
        int numberOfPairs = sc.nextInt();
        sc.nextLine();
        while (sc.hasNextLine()) {
            int start = sc.nextInt();
            int end = sc.nextInt();
            int weight = sc.nextInt();
            sc.nextLine();
            Edges.add(new Triplet(start, end, weight));
        }


    }

    private int Kruskal() {
        int t = 0;
        while (!Edges.isEmpty()) {
            Triplet e = Edges.poll();
            int parent = e.getStart();
            int child = e.getEnd();
            if (find(parent) != find(child)) {
                union(parent, child);
                t += e.getWeight();
            }
        }
        return t;
    }

    private int find(int x) {
        if (fathers[x] != x) {
            fathers[x] = find(fathers[x]);
        }
        return fathers[x];
    }

    private void union(int parent, int child) {
        int parentFathers = find(parent);
        int childFathers = find(child);
        fathers[parentFathers] = childFathers;
    }


    public static void main(String[] args) throws IOException {
        System.out.println("hej");
        lab3 l = new lab3();
        double t3 = System.nanoTime();
        l.load();
        double t1 = System.nanoTime();
        System.out.println(l.Kruskal());
        double t2 = System.nanoTime();
       // System.out.println("Loading time: " + (t1 - t3) / 1000000000);
        //System.out.println("Kruskal time: " + (t2 - t1) / 1000000000);
       // System.out.println("Total time:   " + (t2 - t3) / 1000000000);
    }
}
