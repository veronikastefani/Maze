import java.awt.*;
import javax.swing.*;

public class MazePanel extends JPanel {
    private Maze maze;
    private int cellSize;
    
    public MazePanel(Maze maze, int cellSize) {
        this.maze = maze;
        this.cellSize = cellSize;
        setBackground(Color.WHITE);
        updatePreferredSize();
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        updatePreferredSize();
        revalidate();
    }

    private void updatePreferredSize() {
        int w = maze.getGrid()[0].length * cellSize;
        int h = maze.getGrid().length * cellSize;
        setPreferredSize(new Dimension(w + 1, h + 1));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Maze.Cell[][] grid = maze.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Maze.Cell cell = grid[i][j];
                int x = j * cellSize;
                int y = i * cellSize;
                
                g2d.setColor(cell.terrain.color);
                g2d.fillRect(x, y, cellSize, cellSize);

                if (cell.inPath) {
                    g2d.setColor(new Color(255, 240, 0, 150)); 
                    g2d.fillRect(x, y, cellSize, cellSize);
                }

                if (cell == maze.getStart()) {
                    g2d.setColor(new Color(46, 204, 113));
                    g2d.fillRect(x, y, cellSize, cellSize);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("START", x+2, y+cellSize/2);
                } else if (cell == maze.getEnd()) {
                    g2d.setColor(new Color(231, 76, 60));
                    g2d.fillRect(x, y, cellSize, cellSize);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("END", x+5, y+cellSize/2);
                }

                g2d.setColor(new Color(180, 180, 180));
                g2d.setStroke(new BasicStroke(2));
                if (cell.walls[0]) g2d.drawLine(x, y, x + cellSize, y);
                if (cell.walls[1]) g2d.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
                if (cell.walls[2]) g2d.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
                if (cell.walls[3]) g2d.drawLine(x, y, x, y + cellSize);
            }
        }

        Maze.Cell p = maze.getPlayerCell();
        if (p != null) {
            g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, (int)(cellSize*0.7)));
            g2d.drawString("🏃", p.col*cellSize + 5, p.row*cellSize + (int)(cellSize*0.8));
        }
    }
}