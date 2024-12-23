package Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    int index;
    String value;
    List<Node> next;
    boolean isSuggest;
    Map<String, Node> children;
    String result;

    public Node(int index, String value, boolean isSuggest) {
        this.index = index;
        this.value = value;
        this.next =  new ArrayList<>();
        this.isSuggest = isSuggest;
    }

    public void addChild(Node child) {
        this.next.add(child);
    }

    Node(String attribute) {
        this.value = attribute;
        this.children = new HashMap<>();
    }

    Node(String attribute, String result) {
        this.value = attribute;
        this.result = result;
    }
}
