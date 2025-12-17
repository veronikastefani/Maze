import java.util.*;

public class BFSAlgorithm implements PathfindingAlgorithm {
    @Override
    public void solve(Maze maze) {
        maze.resetVisited();
        Queue<Maze.Cell> queue = new LinkedList<>();
        Map<Maze.Cell, Maze.Cell> parent = new HashMap<>();
        
        Maze.Cell start = maze.getStart();
        Maze.Cell end = maze.getEnd();
        
        queue.offer(start);
        start.isVisited = true;
        
        while (!queue.isEmpty()) {
            Maze.Cell current = queue.poll();
            
            if (current == end) {
                maze.buildPath(parent);
                return;
            }
            
            for (Maze.Cell neighbor : maze.getNeighbors(current)) {
                if (!neighbor.isVisited) {
                    neighbor.isVisited = true;
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
    }
}