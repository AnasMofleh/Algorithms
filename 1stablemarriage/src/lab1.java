import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class lab1 {
    private Map<Integer, ArrayList<Integer>> men;
    private Map<Integer, ArrayList<Integer>> women;
    private LinkedList<Integer> que;
    private Map<Integer,Integer> couples;
    private int numberOfCouples;
    private Map<Integer, int[]> indexReverse;


    private lab1() {
        this.men     = new HashMap<>();
        this.women   = new HashMap<>();
        this.que     = new LinkedList<Integer>();
        this.indexReverse   = new HashMap<>();
        this.couples = new HashMap<>(); // women, men
    }

    private void setNumberOfCouples(int numberOfCouples) {
        this.numberOfCouples = numberOfCouples;
    }

    private void load(File file) throws Exception {
        Scanner sc = new Scanner(file);
        setNumberOfCouples(Integer.parseInt(sc.nextLine()));
        int[] data = new int[(numberOfCouples * 2) * (numberOfCouples + 1)];
        int Counter = 0 ;
        while (sc.hasNextLine()){
            String[] line = sc.nextLine().split(" ");
            for (String s: line) {
                data[Counter] = Integer.parseInt(s);
                Counter++;
            }
        }
        addd(data);
    }


    private void addd(int[] data){
        int womanOrMan = 0;
        ArrayList<Integer> preferencesQue = new ArrayList<>(numberOfCouples);
        int[] prefIndex = new int[numberOfCouples];

        for(int Counter = 0; Counter < data.length; Counter++) {
            if (Counter % (numberOfCouples + 1) == 0) {
                womanOrMan = data[Counter];
                prefIndex = new int[numberOfCouples + 1];
            } else {
                preferencesQue.add(data[Counter]);
                prefIndex[data[Counter]-1] =  preferencesQue.indexOf(data[Counter]);
            }


            if(preferencesQue.size() == numberOfCouples){
                ArrayList<Integer> womenOrManPreferences = women.get(womanOrMan);
                if(womenOrManPreferences == null) {
                    women.put(womanOrMan, preferencesQue);
                    indexReverse.put(womanOrMan, prefIndex);
                } else {
                    men.put(womanOrMan, preferencesQue);
                    que.addFirst(womanOrMan);
                }
                preferencesQue = new ArrayList<>(numberOfCouples);
            }

        }

    }


    private void GaleShapleyAlgorithm(){  // n^2 * (2* O(n))
        while (!que.isEmpty()){ // kommer att köras n/2 = number of men
            Integer futureHusband = que.removeFirst(); //   O(1)
            Integer woman = men.get(futureHusband).get(0); //  2 * O(1)
            if(!couples.containsKey(woman)){   // n/2 = number of women
                couples.put(woman,futureHusband); // O(1)
                     //women.get(woman).indexOf(couples.get(woman)) > women.get(woman).indexOf(futureHusband)
            } else if(indexReverse.get(woman)[(couples.get(woman)-1)] > indexReverse.get(woman)[(futureHusband-1)]) {  // 2 * O(n)
                int oldHusband = couples.remove(woman);   //  O(1)
                couples.put(woman,futureHusband);    //  O(1)
                men.get(oldHusband).remove(0);   //  2 * O(1)
                que.addFirst(oldHusband);    //  O(1)
            } else{
                que.add(futureHusband); // O(1) kan bytas mot addFirst
                men.get(futureHusband).remove(0);  //  O(1)
            }
        }
    }


    public static void main(String[] args) throws Exception {
        lab1 lab = new lab1();
        lab.load(new File("C:\\Users\\DELL\\Desktop\\algodat\\EDAF05-labs-public-master\\1stablemarriage\\data\\secret\\5testhugemessy.in"));

        long startTime = System.nanoTime();
        lab.GaleShapleyAlgorithm();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        for (Map.Entry e: lab.couples.entrySet()) System.out.println(e.getValue());
    }
}
