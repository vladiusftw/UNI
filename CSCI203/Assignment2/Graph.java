import java.util.*;

public class Graph {
    private Map<String, ArrayList<String>> map;

    public Graph(){
        map = new HashMap<>();
    }

    public Map<String,ArrayList<String>> getMap(){return map;}
    public void setMap(Map<String,ArrayList<String>> map){this.map = map;}

    public void addNode(String label){
        map.put(label,new ArrayList<>());
    }

    public void removeNode(String label){
        map.remove(label);
    }

    public void addEdge(String label1,String label2){
        map.get(label1).add(label2);
    }

    public void removeEdge(String label1,String label2){
        map.get(label1).remove(label2);
    }

    public ArrayList<String> BreadthFirstSearch(String root){
        ArrayList<String> visited = new ArrayList<>();
        visited.add(root);
        Queue<String> queue = new LinkedList<>(map.get(root));
        while(queue.size() != 0){
            String temp = queue.poll();
            visited.add(temp);
            queue.addAll(map.get(temp));
        }
        return visited;
    }

    public ArrayList<String> DepthFirstSearch(String root){
        ArrayList<String> visited = new ArrayList<>();
        visited.add(root);
        Stack<String> stack = new Stack<>();
        stack.addAll(map.get(root));
        while(stack.size() != 0){
            String temp = stack.pop();
            visited.add(temp);
            stack.addAll(map.get(temp));
        }
        return visited;
    }

    public String toString(){
        return map.toString();
    }
}
