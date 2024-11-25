package Week1;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    public  void bfsUsingQueue(Node initial, int goal, Queue<Node> queue) {
        if (initial.getState() ==goal){
            pirntResult(initial);
        } else if (initial.getNeighbours() != null || !initial.getNeighbours().isEmpty()) {

            initial.getNeighbours().stream().sorted(comparator).toList().forEach(n->{
                if (!n.visited && !queue.contains(n)) {
                    n.setParent(initial);
                    initial.setVisited(true);
                    queue.add(n);
                }

            });
            bfsUsingQueue(queue.poll(), goal, queue);
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

