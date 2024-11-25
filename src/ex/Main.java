package ex;

public class Main {
    public static void main(String[] args) {
        Queen q = new Queen(4);

        System.out.println("Finding solution with DFS:");
        q.dfs();

        System.out.println("Finding solution with BFS:");
        q.bfs();
    }
}
