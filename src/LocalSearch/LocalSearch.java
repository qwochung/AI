package LocalSearch;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class LocalSearch {


    public static int[] localSearch(int[] current, int h) {


        return null;

    }

    public static int checkHorizontal(Node node) {
        List<Integer> current = node.getState();
        int h = 0;

        for (int row = 0; row < current.size(); row++) {
            for (int col = row + 1; col < current.size(); col++) {
                if (current.get(row) == current.get(col)) {
                    h++;
                }
            }
        }
        return h;
    }


    public static int checkDiagonal(Node node) {
        int h = 0;
        List<Integer> current = node.getState();

        for (int row = 0; row < current.size(); row++) {
            for (int col = row + 1; col < current.size(); col++) {
                if (current.get(row) == current.get(col) || Math.abs(current.get(row) - current.get(col)) == Math.abs(col - row)) {
                    h++;
                }
            }
        }
        return h;
    }

    public int heuristic(Node node) {
        return checkHorizontal(node) + checkDiagonal(node);
    }


    public int tryMovingOneQueen(Node node, int x, int y) {

        Node newNode = new Node(node.getN(), node.deepCopy());
        newNode.getState().set(y, x);
        return heuristic(newNode);
    }




    public SortedMap<Integer, Node> generateNeighbours(Node node) {
        SortedMap<Integer, Node> neighbours = new TreeMap<Integer, Node>();

        for (int col = 0; col < node.getN(); col++) {
//            for (int row = col + 1; row < node.getN(); row++) {
            for (int row = 0; row < node.getN(); row++) {
                if (row != node.getState().get(col)) {
//                int h = tryMovingOneQueen(node, col, row);
                int h = tryMovingOneQueen(node, row, col);

                Node newNode = new Node(node.getN(), node.deepCopy());
                newNode.getState().set(col, row);
                neighbours.put(h, newNode);
            }}

        }

        return neighbours;
    }


    public void run() {
        Node initial = new Node(4); //hoặc 4,5,6,7
        if (heuristic(initial) == 0) //goal
        {
            System.out.println(initial.state);
            return;
        }
        System.out.println("Initial state is: " + initial.state);
        Node node = initial;
        SortedMap<Integer, Node> neighbours = generateNeighbours(node);
        Integer bestHeuristic = neighbours.firstKey();
        while (bestHeuristic < heuristic(node)) {
            node = neighbours.get(bestHeuristic);
            neighbours = generateNeighbours(node);

            bestHeuristic = neighbours.firstKey();
            System.out.println("Heuristic: " +  neighbours.firstKey());

        }

        System.out.println("Heuristic: " + heuristic(node));
        if (heuristic(node) == 0) {
            System.out.println("Goal is: " + node.state);
        } else
            System.out.println("Cannot find goal state! Best state is: " + node.state);
    }


    public static void main(String[] args) {
        LocalSearch ls = new LocalSearch();
        ls.run();
    }

}
