package ex;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int n;
    private List<Integer> states;

    public Node(int n) {
        this.n = n;
        this.states = new ArrayList<>();
    }

    public Node(int n, List<Integer> states) {
        this.n = n;
        this.states = new ArrayList<>(states);
    }

    public List<Node> getNeighbours() {
        List<Node> neighbours = new ArrayList<>();
        if (states.size() == n) {
            return neighbours;
        }

        for (int col = 0; col < n; col++) {
            List<Integer> newState = new ArrayList<>(states);
            newState.add(col);

            if (isValid(newState)) {
                neighbours.add(new Node(n, newState));
            }
        }
        return neighbours;
    }

    public boolean isValid(List<Integer> state) {
        int currentQueenRow = state.size() - 1;
        int currentQueenCol = state.get(currentQueenRow);

        for (int row = 0; row < currentQueenRow; row++) {
            int col = state.get(row);

            if (col == currentQueenCol || Math.abs(col - currentQueenCol) == Math.abs(row - currentQueenRow)) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getStates() {
        return states;
    }

    public int getN() {
        return n;
    }


    public static void main(String[] args) {
//        Week2.Node n1 = new Week2.Node(4);
//        List<Week2.Node> r= n1.getNeighbours();
    }
}
