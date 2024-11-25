package Week1;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int state;
    boolean visited;
    List<Node> neighbours;
    Node parent;

    public Node(int state)
    {
        this.state=state;
        this.neighbours=new ArrayList<>();
        this.parent= null;
    }
    public void addNeighbours(Node neighbourNode)
    {
        this.neighbours.add(neighbourNode);
    }



    public List<Node> getNeighbours() {
        return neighbours;}

    public void setNeighbours(List<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }


}
