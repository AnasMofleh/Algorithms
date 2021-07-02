import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class lab2 {
    private ArrayList<Tuple> Tests;
    private ArrayList<Node> Graph;
    private ArrayList<String> graphString;
    private HashMap<String, Integer> graphMap;

    private lab2(){
        Graph = new ArrayList<Node>();
        Tests = new ArrayList<Tuple>();
        graphString = new ArrayList<String>();
        graphMap = new HashMap<>();
    }


    private void load(File file) throws FileNotFoundException {
        int Words = 0;
        int Pairs = 0;
        ArrayList<Node> InputToWords = new ArrayList<>();
        Scanner sc = new Scanner(file);
        Words = sc.nextInt();
        Pairs = sc.nextInt();
        int Counter = 0;
        while(sc.hasNextLine()){
            if(Counter <= Words){
                String s = sc.nextLine();
                if(s.length() != 0){
                    InputToWords.add(new Node(s));
                    graphString.add(s);
                    graphMap.put(s,Counter-1);
                }} else if (Counter <= (Words+Pairs)){
                String[] testLine = sc.nextLine().split(" ");
                Tests.add(new Tuple(testLine[0],testLine[1]));
            }
            Counter++;
        }
        Graph(InputToWords);
    }



    private void Graph(ArrayList<Node> listOfWords){
        for (int i = 0; i < listOfWords.size(); i++) {
            Node firstWord = listOfWords.get(i);
            for (Node n : listOfWords) {
                if (containsChars(firstWord.getId(), n.getId())) {
                    Edge edge1 = new Edge(firstWord, n, 1);
                    firstWord.getNeighbors().add(edge1);
                }
            }
        }
        Graph = listOfWords;
    }


    public void BFS(ArrayList<Node> graph ,String start, String target) {
        if (start.compareTo(target) == 0) System.out.println(0);
        else {

            LinkedList<Node> que = new LinkedList<>();
            Node st = graph.get(graphMap.get(start));
            for (Node n : graph) {
                n.setVisited(false);
                n.setCost(0);
            }

            st.setVisited(true);
            que.add(st);

            while (!que.isEmpty()) {
                Node firstElement = que.removeFirst();
                for (Edge neighbor : firstElement.getNeighbors()) {
                    if (!neighbor.getEnd().getVisited()) {
                        neighbor.getEnd().setVisited(true);
                        que.addLast(neighbor.getEnd());
                        neighbor.getEnd().setCost(firstElement.getCost() + 1);
                        if (neighbor.getEnd().getId().compareTo(target) == 0) {
                            System.out.println(neighbor.getEnd().getCost());
                            return;
                        }
                    }
                }
            }
            System.out.println("Impossible");
        }
    }


    private boolean containsChars(String st1, String st2){
        if(st1.equals(st2)){
            return false;
        } else {
            String last4Letters = st1.substring(1);
            StringBuilder sb = new StringBuilder(st2);
            for (int i = 0; i < last4Letters.length(); i++) {
                Character c = last4Letters.charAt(i);
                if (sb.toString().indexOf(c) == -1) {
                    return false;
                } else sb.deleteCharAt(sb.toString().indexOf(c));
            }
        }
         return true;
    }


    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.nanoTime();
        lab2 l = new lab2();
        l.load(new File("C:\\Users\\DELL\\Desktop\\algodat\\EDAF05-labs-public-master\\2wordladders\\data\\secret\\6large2.in"));
        for (Tuple t: l.Tests) l.BFS(l.Graph,(String) t.getX(),(String) t.getY());
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);


    }
}
