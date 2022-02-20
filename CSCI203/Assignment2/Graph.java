import java.security.spec.EdDSAParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Graph {
    private ArrayList<Edge>[] map;
    private Node[] nodes;

    public Graph(int size){
        nodes = new Node[size+1];
        map = new ArrayList[size+1];
        for(int i = 0; i < nodes.length;i++){
            nodes[i] = new Node(i,-1);
            map[i] = new ArrayList<>();
        }
    }

    public Node getNodeByLabel(int label){return nodes[label];}

    public void addEdge(int label1,int label2,int connectionTime){
        Node n1 = nodes[label1];
        Node n2 = nodes[label2];
        Edge edge = new Edge(n1,n2,connectionTime);
        map[label1].add(edge);
    }

    public ArrayList<Node> DFSForInfected(int root){
        Stack<Edge> stack = new Stack<>();
        stack.addAll(map[root]);
        while(stack.size() != 0){
            Edge curr = stack.pop();
            Node n1 = nodes[curr.getN1().getLabel()];
            Node n2 = nodes[curr.getN2().getLabel()];
            infectEither(n1,n2, curr.getConnectionTime());
            if(n2.getInfectionTime() >= 0)
            stack.addAll(map[curr.getN2().getLabel()]);
        }
        ArrayList<Node> infectedNodes = new ArrayList<>();
        for(Node n : nodes)
            if(n.getInfectionTime() >= 0) infectedNodes.add(n);
        return infectedNodes;
    }

    private void infectEither(Node n1,Node n2,int connectionTime){
        if(n1.getInfectionTime() >= 0 && n2.getInfectionTime() < 0){ //if n1 is infected and n2 isn't
            if(connectionTime >= n1.getInfectionTime()){
                n2.setInfectionTime(connectionTime);
                n2.setInfectionSequence(n1.getInfectionSequence() + " " + n1.getLabel());
            }
        }
        else if(n1.getInfectionTime() >= 0 && n2.getInfectionTime() >= 0){ //if both are infected
            if(connectionTime >= n1.getInfectionTime() && connectionTime < n2.getInfectionTime()){
                n2.setInfectionTime(connectionTime);
                n2.setInfectionSequence(n1.getInfectionSequence() + " " + n1.getLabel());
            }
        }

    }
}
