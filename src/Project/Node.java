package Project;

public class Node {
    int index;
    String value;
    Node next;
    boolean isSuggest;


    public Node(int index, String value, Node next, boolean isSuggest) {
        this.index = index;
        this.value = value;
        this.next = next;
        this.isSuggest = isSuggest;
    }
}
