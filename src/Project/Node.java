package Project;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int index;
    String value;
    List<Node> next;
    boolean isSuggest;


    public Node(int index, String value, boolean isSuggest) {
        this.index = index;
        this.value = value;
        this.next =  new ArrayList<>();
        this.isSuggest = isSuggest;
    }

    public void addChild(Node child) {
        this.next.add(child);
    }
}
