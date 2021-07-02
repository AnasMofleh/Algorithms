public class Edge {

    private Node start;
    private Node end;


    public Edge(Node start, Node end, int cost) {
        this.start    = start;
        this.end      = end;


    }

    public Node getEnd() {
        return end;
    }



    @Override
    public String toString() {
        return start +" -> "+ end +" (cost is zero )";
    }
}