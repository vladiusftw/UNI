public class Edge {
    private Node n1,n2;
    private int connectionTime;

    public Edge(Node n1, Node n2, int connectionTime) {
        this.n1 = n1;
        this.n2 = n2;
        this.connectionTime = connectionTime;
    }

    public Node getN1() {
        return n1;
    }

    public void setN1(Node n1) {
        this.n1 = n1;
    }

    public Node getN2() {
        return n2;
    }

    public void setN2(Node n2) {
        this.n2 = n2;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public String toString(){
        return "(" + n1 + "," + n2 + "):" + n2.getInfectionTime();
    }
}
