import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Scanner infectedScanner = new Scanner(new File("src/TestCases/infectedTable.txt"));
        Scanner dataSetScanner = new Scanner(new File("src/TestCases/table.txt"));
        infectedScanner.nextLine();
        dataSetScanner.nextLine();

        System.out.println("Enter time to find infected computers:");
        int time = scan.nextInt();
        long start = System.currentTimeMillis();
        Graph graph = new Graph(100);
        ArrayList<Node> infected = new ArrayList<>();
        while(infectedScanner.hasNextInt()){
            int label = infectedScanner.nextInt();
            int infectionTime = infectedScanner.nextInt();
            if(infectionTime <= time){
                Node temp =  graph.getNodeByLabel(label);
                temp.setInfectionTime(infectionTime);
                infected.add(temp);
            }
        }

        Collections.sort(infected);
        for(Node n : infected)  graph.addEdge(100,n.getLabel(),n.getInfectionTime());

        while(dataSetScanner.hasNextInt()){
            int l1 = dataSetScanner.nextInt();
            int l2 = dataSetScanner.nextInt();
            int connectionTime = dataSetScanner.nextInt();
            if(connectionTime <= time){
                graph.addEdge(l1,l2,connectionTime);
                graph.addEdge(l2,l1,connectionTime);
            }
        }
        System.out.println("Infected Computers at Time " + time + " are:");
        ArrayList<Node> infectedNodes = graph.DFSForInfected(100);
        FileWriter fw = new FileWriter("CSCI203-win-22-prog#2-6979270-6346777.txt");
        for(Node n : infectedNodes){
            if(n.getLabel() != 100)
                fw.write("Computer#" + n.getLabel() + " at time "
                        + n.getInfectionTime() + ":[" + n.getInfectionSequence() + " ]\n");
        }
        fw.close();
        long execTime = System.currentTimeMillis() - start;
        System.out.println("Executed in " + (execTime/1000.) + " secs");
    }
}