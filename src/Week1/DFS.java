package Week1;

import java.util.Comparator;
import java.util.Queue;
import java.util.Stack;

public class DFS {
    public  void dfsUsingStack(Node initial, int goal, Stack<Node> stack) {
        if (initial.getState() ==goal){
            pirntResult(initial);
        } else if (initial.getNeighbours() != null || !initial.getNeighbours().isEmpty()) {

            initial.getNeighbours().stream().sorted(comparator.reversed()).toList().forEach(n->{
                if (!n.visited && !stack.contains(n)) {
                    n.setParent(initial);
                    initial.setVisited(true);
                    stack.push(n);
                }
            });
            dfsUsingStack(stack.pop(), goal, stack);
        }
    }


    public void pirntResult(Node node){
        String s="";
                while (node.getParent()!=null)                {
                    s=node.state+" "+s;
                    node=node.getParent();
                }
                System.out.println(node.state+ " "+s);
              
    }


    Comparator<Node> comparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.state-o2.state;
        }
    };
}

