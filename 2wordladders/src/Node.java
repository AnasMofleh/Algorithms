import java.util.ArrayList;

public class Node {

    private String Id;
    private ArrayList<Edge> Neighbors;
    private Boolean IsVisited;
    private int cost = 0;

    public Node(String id) {
        this.Id = id;
        this.Neighbors = new ArrayList<>();
        IsVisited = false;
    }

    public String getId() {
        return Id;
    }


    public ArrayList<Edge> getNeighbors() {
        return Neighbors;
    }


    public Boolean getVisited() {
        return IsVisited;
    }

    public void setVisited(Boolean visited) {
        IsVisited = visited;
    }

    @Override
    public String toString() {
        return "(" + Id +", "+ Neighbors.size() + ")";
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}