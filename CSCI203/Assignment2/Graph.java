import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map<Node, ArrayList<Node>> map;

    public Graph(){
        map = new HashMap<>();
    }

    public Map<Node,ArrayList<Node>> getMap(){return map;}
    public void setMap(Map<Node,ArrayList<Node>> map){this.map = map;}

    public void addNode(String label){
        map.put(new Node(label),new ArrayList<Node>());
    }

    public void removeNode(String label){
        map.remove(new Node(label));
    }

    public void addEdge(String label1,String label2){
       map.get(new Node(label1)).add(new Node(label2));
    }

    public void removeEdge(String label1,String label2){
        map.get(new Node(label1)).remove(new Node(label2));
    }

    public boolean BreadthFirstSearch(String label){

    }

    public String toString(){
        return map.toString();
    }
}
