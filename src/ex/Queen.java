package ex;

public class Queen {
    private int n;

    public Queen(int n) {
        this.n = n;
    }

    public void dfs() {
        DFS dfs = new DFS();
        dfs.dfsUsingStack(new Node(n));
    }

    public void bfs() {
        BFS bfs = new BFS();
        bfs.bfsUsingQueue(new Node(n));
    }
}
