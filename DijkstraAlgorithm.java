import java.util.*;

public class DijkstraAlgorithm implements PathfindingAlgorithm {
    private static class Node implements Comparable<Node> {
        Maze.Cell cell;
        int dist;
        Node(Maze.Cell c, int d) { this.cell = c; this.dist = d; }
        @Override
        public int compareTo(Node o) { return Integer.compare(this.dist, o.dist); }
    }

    @Override
    public void solve(Maze maze) {
        maze.resetVisited();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<Maze.Cell, Integer> distances = new HashMap<>();
        Map<Maze.Cell, Maze.Cell> parent = new HashMap<>();
        
        for (Maze.Cell[] row : maze.getGrid()) {
            for (Maze.Cell c : row) distances.put(c, Integer.MAX_VALUE);
        }

        Maze.Cell start = maze.getStart();
        distances.put(start, 0);
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            Maze.Cell u = current.cell;

            if (u.isVisited) continue;
            u.isVisited = true;

            if (u == maze.getEnd()) break;

            for (Maze.Cell v : maze.getNeighbors(u)) {
                int weight = v.getCost();
                if (distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    parent.put(v, u);
                    pq.offer(new Node(v, distances.get(v)));
                }
            }
        }
        maze.buildPath(parent);
    }
}