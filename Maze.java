import java.awt.Color;
import java.util.*;

public class Maze {
    private int rows, cols;
    private Cell[][] grid;
    private Cell start, end;
    private List<Cell> path = new ArrayList<>();
    private Cell playerCell; 
    private int totalCost;

    public enum Terrain {
        DEFAULT(0, Color.WHITE),
        GRASS(1, new Color(210, 245, 210)), // Hijau
        MUD(5, new Color(225, 200, 170)),   // Cokelat
        WATER(10, new Color(200, 230, 255)); // Biru
        
        final int cost;
        final Color color;
        Terrain(int cost, Color color) { this.cost = cost; this.color = color; }
    }

    public static class Cell {
        public int row, col;
        public boolean[] walls = {true, true, true, true};
        public boolean inPath = false, isVisited = false;
        public Terrain terrain = Terrain.DEFAULT;
        
        public Cell(int r, int c) { this.row = r; this.col = c; }
        public int getCost() { return terrain.cost; }
    }

    public Maze(int rows, int cols) {
        this.rows = rows; this.cols = cols;
        this.grid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) grid[i][j] = new Cell(i, j);
        }
        this.start = grid[0][0];
        this.end = grid[rows-1][cols-1];
        generateMaze();
    }

    private void generateMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell current = start;
        current.isVisited = true;
        int visitedCount = 1;
        Random rand = new Random();

        // 1. Buat Labirin Dasar
        while (visitedCount < rows * cols) {
            List<Cell> neighbors = getUnvisitedNeighbors(current);
            if (!neighbors.isEmpty()) {
                Cell next = neighbors.get(rand.nextInt(neighbors.size()));
                removeWalls(current, next);
                stack.push(current);
                current = next;
                current.isVisited = true;
                visitedCount++;
                
                // Terapkan Biaya Sesuai Permintaan Anda
                double r = rand.nextDouble();
                if (r < 0.20) current.terrain = Terrain.GRASS;  // Cost 1
                else if (r < 0.35) current.terrain = Terrain.MUD;    // Cost 5
                else if (r < 0.50) current.terrain = Terrain.WATER;  // Cost 10
            } else if (!stack.isEmpty()) current = stack.pop();
        }

        // 2. MODIFIKASI: Hancurkan dinding tambahan secara acak
        // Ini menciptakan jalur alternatif agar Dijkstra/A* bisa memilih rute termurah
        for (int i = 0; i < (rows * cols) / 4; i++) {
            int r = rand.nextInt(rows - 2) + 1;
            int c = rand.nextInt(cols - 2) + 1;
            if (c < cols - 1) removeWalls(grid[r][c], grid[r][c+1]);
            if (r < rows - 1) removeWalls(grid[r][c], grid[r+1][c]);
        }
        resetVisited();
    }

    // --- Helper Methods ---
    private List<Cell> getUnvisitedNeighbors(Cell c) {
        List<Cell> n = new ArrayList<>();
        if (c.row > 0 && !grid[c.row-1][c.col].isVisited) n.add(grid[c.row-1][c.col]);
        if (c.col < cols-1 && !grid[c.row][c.col+1].isVisited) n.add(grid[c.row][c.col+1]);
        if (c.row < rows-1 && !grid[c.row+1][c.col].isVisited) n.add(grid[c.row+1][c.col]);
        if (c.col > 0 && !grid[c.row][c.col-1].isVisited) n.add(grid[c.row][c.col-1]);
        return n;
    }

    private void removeWalls(Cell a, Cell b) {
        if (a.row < b.row) { a.walls[2] = false; b.walls[0] = false; }
        else if (a.row > b.row) { a.walls[0] = false; b.walls[2] = false; }
        else if (a.col < b.col) { a.walls[1] = false; b.walls[3] = false; }
        else if (a.col > b.col) { a.walls[3] = false; b.walls[1] = false; }
    }

    public List<Cell> getNeighbors(Cell c) {
        List<Cell> n = new ArrayList<>();
        if (!c.walls[0]) n.add(grid[c.row-1][c.col]);
        if (!c.walls[1]) n.add(grid[c.row][c.col+1]);
        if (!c.walls[2]) n.add(grid[c.row+1][c.col]);
        if (!c.walls[3]) n.add(grid[c.row][c.col-1]);
        return n;
    }

    public void buildPath(Map<Cell, Cell> parent) {
        path.clear();
        Cell current = end;
        int cost = 0;
        while (current != null) {
            current.inPath = true;
            path.add(current);
            cost += current.getCost();
            current = parent.get(current);
        }
        this.totalCost = cost;
        Collections.reverse(path);
    }

    public void resetPath() {
        path.clear(); totalCost = 0; playerCell = null;
        for (Cell[] row : grid) for (Cell c : row) { c.inPath = false; c.isVisited = false; }
    }

    public void resetVisited() { for (Cell[] row : grid) for (Cell c : row) c.isVisited = false; }
    public Cell[][] getGrid() { return grid; }
    public Cell getStart() { return start; }
    public Cell getEnd() { return end; }
    public List<Cell> getPath() { return path; }
    public int getTotalCost() { return totalCost; }
    public void setPlayerCell(Cell c) { this.playerCell = c; }
    public Cell getPlayerCell() { return playerCell; }
}