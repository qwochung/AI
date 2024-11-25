package ex;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    public void bfsUsingQueue(Node initial) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(initial);

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (node.getStates().size() == node.getN()) {
                System.out.println("Solution: " + node.getStates());
                continue;
            }

            for (Node neighbour : node.getNeighbours()) {
                queue.add(neighbour);
            }
        }
    }
}
