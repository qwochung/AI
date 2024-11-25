package Week2;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int n;
    List<Integer> state;
    List<Node> neighbors;


    public Node(int n) {
        this.n = n;
        state = new ArrayList<>();
        neighbors = new ArrayList<>();

    }

    public Node(int n, List<Integer> state) {
        this.n = n;
        this.state = state;
        neighbors = new ArrayList<>();

    }


    public void addNeighbor(Node node) {
        neighbors.add(node);
    }




    public boolean isValid(List<Integer> state){
        if(state.size() ==1){
            return true;
        }

        int currentRow = state.size() - 1;
        int currentCol = state.get(currentRow);

        for (int i = 0  ; i < currentCol; i++) {
            if (currentRow == state.get(i) || Math.abs(currentRow - state.get(i)) == Math.abs(currentCol - state.get(i))) {
                return false;
            }
        }


        return true;
    }




    public List<Node>getNeighbours(){
        List<Node> neighbours = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> temp = new ArrayList<>(state);
            temp.add(i);
            if (isValid(temp)) {
                neighbours.add(new Node(n, temp));
            }


        }
        return neighbours;
    }



    public static void main(String[] args) {
        Node n1 = new Node(4);
        List<Node> r= n1.getNeighbours();
    }




}
