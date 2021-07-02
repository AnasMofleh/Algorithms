public class Triplet {
    private int start;
    private int end;
    private int weight;
    private Boolean StartVisited;
    private Boolean EndVisited;

    public Triplet(int start, int end, int z){
        this.start = start;
        this.end = end;
        this.weight = z;
        this.StartVisited = false;
        this.EndVisited = false;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    public void setEndVisited(Boolean endVisited) {
        EndVisited = endVisited;
    }

    public void setStartVisited(Boolean startVisited) {
        StartVisited = startVisited;
    }

    public Boolean getEndVisited() {
        return EndVisited;
    }

    public Boolean getStartVisited() {
        return StartVisited;
    }

    @Override
    public String toString() {
        return "(" + start +", "+ end + ", "+ weight + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return getWeight() == (((Triplet) obj).getWeight());
    }
}
