import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        Scanner infectedScanner = new Scanner(new File("src/TestCases/infected.txt"));
        Scanner dataSetScanner = new Scanner(new File("src/TestCases/dataset.txt"));
        infectedScanner.nextLine();
        dataSetScanner.nextLine();
        
        System.out.println("Enter time to find infected computers: (-1 to exit)");
        int time = scan.nextInt();
        long start = System.currentTimeMillis();
        Graph graph = new Graph(100);
        while(infectedScanner.hasNextInt()){
            int label = infectedScanner.nextInt();
            int infectionTime = infectedScanner.nextInt();
            if(infectionTime <= time){
                graph.getNodeByLabel(label).setInfectionTime(infectionTime);
                graph.addEdge(100,label,infectionTime);
            }
        }

        while(dataSetScanner.hasNextInt()){
            int l1 = dataSetScanner.nextInt();
            int l2 = dataSetScanner.nextInt();
            int connectionTime = dataSetScanner.nextInt();
            if(connectionTime <= time){
                Node n2 = graph.getNodeByLabel(l2);
                if(n2.getInfectionTime() >= 0){
                    graph.addEdge(l2,l1,connectionTime);
                }
                else graph.addEdge(l1,l2,connectionTime);
            }
        }
        System.out.println("Infected Computers at Time " + time + " are:");
        ArrayList<Node> infectedNodes = graph.DFSForInfected(100);
        for(Node n : infectedNodes){
            if(n.getLabel() != 100)
                System.out.println("Computer#" + n.getLabel() + " at time "
                        + n.getInfectionTime() + ":[" + n.getInfectionSequence() + " ]");
        }
        long execTime = System.currentTimeMillis() - start;
        System.out.println("Executed in " + (execTime/1000.) + " secs");
    }
}
