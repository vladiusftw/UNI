public class Node {
    private int label;
    private int infectionTime;
    private String infectionSequence;

    public Node(int label, int infectionTime) {
        this.label = label;
        this.infectionTime = infectionTime;
        this.infectionSequence = "";
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getInfectionTime() {
        return infectionTime;
    }

    public void setInfectionTime(int infectionTime) {
        this.infectionTime = infectionTime;
    }

    public String getInfectionSequence() {
        return infectionSequence;
    }

    public void setInfectionSequence(String infectionSequence) {
        this.infectionSequence = infectionSequence;
    }

    public String toString(){
        return label + "";
    }
}
