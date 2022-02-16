public class Node {
    private String label;

    public Node(String label){
        this.label = label;
    }

    public String getLabel(){return label;}
    public void setLabel(String label){this.label = label;}

    public String toString(){return "Label: " + label;}
}
