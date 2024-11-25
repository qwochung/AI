import Week1.BFS;
import Week1.DFS;
import Week1.Node;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Node node40 =new Node(40);
        Node node10 =new Node(10);
        Node node20 =new Node(20);
        Node node30 =new Node(30);
        Node node60 =new Node(60);
        Node node50 =new Node(50);
        Node node70 =new Node(70);

        node40.addNeighbours(node10);
        node40.addNeighbours(node20);
        node10.addNeighbours(node30);
        node20.addNeighbours(node10);
        node20.addNeighbours(node30);
        node20.addNeighbours(node60);
        node20.addNeighbours(node50);
        node30.addNeighbours(node60);
        node60.addNeighbours(node70);
        node50.addNeighbours(node70);

        BFS bfsExample = new BFS();
        DFS dfsExample = new DFS();
        Queue queue = new LinkedList<>();
        Stack stack = new Stack();

        System.out.println("The BFS traversal of the graph using queue ");
//        bfsExample.bfsUsingQueue(node40,70, queue);
        dfsExample.dfsUsingStack(node40,70, stack);


    }
}