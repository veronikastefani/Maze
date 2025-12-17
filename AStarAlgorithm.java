import java.util.*;

public class AStarAlgorithm implements PathfindingAlgorithm {
    private static class Node implements Comparable<Node> {
        Maze.Cell cell;
        int fScore; // gScore + heuristic
        Node(Maze.Cell c, int f) { this.cell = c; this.fScore = f; }
        @Override
        public int compareTo(Node o) { return Integer.compare(this.fScore, o.fScore); }
    }

    private int getHeuristic(Maze.Cell a, Maze.Cell b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    @Override
    public void solve(Maze maze) {
        maze.resetVisited();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<Maze.Cell, Integer> gScore = new HashMap<>();
        Map<Maze.Cell, Maze.Cell> parent = new HashMap<>();
        
        for (Maze.Cell[] row : maze.getGrid()) {
            for (Maze.Cell c : row) gScore.put(c, Integer.MAX_VALUE);
        }

        Maze.Cell start = maze.getStart();
        Maze.Cell end = maze.getEnd();
        gScore.put(start, 0);
        pq.offer(new Node(start, getHeuristic(start, end)));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            Maze.Cell u = current.cell;

            if (u.isVisited) continue;
            u.isVisited = true;

            if (u == end) break;

            for (Maze.Cell v : maze.getNeighbors(u)) {
                int tentativeG = gScore.get(u) + v.getCost();
                if (tentativeG < gScore.get(v)) {
                    parent.put(v, u);
                    gScore.put(v, tentativeG);
                    int f = tentativeG + getHeuristic(v, end);
                    pq.offer(new Node(v, f));
                }
            }
        }
        maze.buildPath(parent);
    }
}