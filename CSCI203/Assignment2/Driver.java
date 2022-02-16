public class Driver {
    public static void main(String[] args){
        Graph graph = new Graph();
        graph.addNode("D");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("A");
        graph.addNode("G");
        graph.addNode("E");
        graph.addNode("F");


        graph.addEdge("A","B");
        graph.addEdge("A","E");
        graph.addEdge("B","C");
        graph.addEdge("B","D");
        graph.addEdge("E","F");
        graph.addEdge("E","G");

        System.out.println(graph.DepthFirstSearch("A"));

    }
}
