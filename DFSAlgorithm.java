import java.util.*;

public class DFSAlgorithm implements PathfindingAlgorithm {
    @Override
    public void solve(Maze maze) {
        maze.resetVisited();
        Stack<Maze.Cell> stack = new Stack<>();
        Map<Maze.Cell, Maze.Cell> parent = new HashMap<>();
        
        Maze.Cell start = maze.getStart();
        Maze.Cell end = maze.getEnd();
        
        stack.push(start);
        start.isVisited = true;
        
        while (!stack.isEmpty()) {
            Maze.Cell current = stack.pop();
            
            if (current == end) {
                maze.buildPath(parent);
                return;
            }
            
            for (Maze.Cell neighbor : maze.getNeighbors(current)) {
                if (!neighbor.isVisited) {
                    neighbor.isVisited = true;
                    parent.put(neighbor, current);
                    stack.push(neighbor);
                }
            }
        }
    }
}