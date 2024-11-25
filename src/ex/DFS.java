package ex;

import java.util.Stack;

public class DFS {
    public void dfsUsingStack(Node initial) {
        Stack<Node> stack = new Stack<>();
        stack.push(initial);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (node.getStates().size() == node.getN()) {
                System.out.println("Solution: " + node.getStates());
                continue;
            }

            for (Node neighbour : node.getNeighbours()) {
                stack.push(neighbour);
            }
        }
    }
}
