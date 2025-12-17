import java.awt.*;
import java.util.List;
import javax.swing.*;

public class MazeGame extends JFrame {
    private Maze maze;
    private MazePanel mazePanel;
    private JComboBox<String> algorithmBox;
    private JButton generateBtn, solveBtn, clearBtn; // Menambahkan clearBtn
    private JLabel statusLabel, costLabel, stepLabel;
    private Timer animationTimer;

    public MazeGame() {
        setTitle("Maze Pathfinding Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        maze = new Maze(15, 15);
        mazePanel = new MazePanel(maze, 38); 
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // --- ATAS: JUDUL ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel titleLabel = new JLabel("MAZE RUNNER");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- TENGAH: MAZE BOARD (CENTERED) ---
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(245, 245, 247));
        centerWrapper.add(mazePanel);
        
        JScrollPane scrollPane = new JScrollPane(centerWrapper);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // --- KANAN: PANEL KONTROL ---
        add(createSideControlPanel(), BorderLayout.EAST);
        
        setSize(1100, 750);
        setLocationRelativeTo(null);
    }

    private JPanel createSideControlPanel() {
        JPanel sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setPreferredSize(new Dimension(280, 0));
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 25, 10, 25);
        gbc.gridx = 0;

        algorithmBox = new JComboBox<>(new String[]{"BFS", "DFS", "Dijkstra", "A*"});
        generateBtn = new JButton("Generate Maze Baru");
        solveBtn = new JButton("Mulai Animasi 🏃");
        clearBtn = new JButton("Clear Path Only 🧹"); // Inisialisasi tombol baru
        
        statusLabel = new JLabel("Status: Siap");
        costLabel = new JLabel("Path Cost: 0");
        stepLabel = new JLabel("Total Langkah: 0");
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        statusLabel.setFont(labelFont);
        costLabel.setFont(labelFont);
        stepLabel.setFont(labelFont);

        // Susun komponen
        gbc.gridy = 0; sidePanel.add(new JLabel("PILIH ALGORITMA:"), gbc);
        gbc.gridy = 1; sidePanel.add(algorithmBox, gbc);
        gbc.gridy = 2; sidePanel.add(generateBtn, gbc);
        gbc.gridy = 3; sidePanel.add(solveBtn, gbc);
        gbc.gridy = 4; sidePanel.add(clearBtn, gbc); // Menambah tombol ke layout
        
        gbc.gridy = 5; gbc.insets = new Insets(40, 25, 5, 25);
        sidePanel.add(new JLabel("STATISTIK JALUR:"), gbc);
        
        gbc.gridy = 6; gbc.insets = new Insets(5, 25, 5, 25);
        sidePanel.add(statusLabel, gbc);
        gbc.gridy = 7; sidePanel.add(costLabel, gbc);
        gbc.gridy = 8; sidePanel.add(stepLabel, gbc);

        // --- EVENT LISTENERS ---
        
        // Tombol Generate (Buat labirin baru & hapus jalur)
        generateBtn.addActionListener(e -> {
            stopAnimation();
            maze = new Maze(15, 15);
            mazePanel.setMaze(maze);
            resetUI();
            mazePanel.repaint();
        });

        // Tombol Start (Hapus jalur lama, hitung, jalankan animasi)
        solveBtn.addActionListener(e -> solveAndAnimate());

        // Tombol Clear Path (Hapus jalur saja tanpa mengacak labirin)
        clearBtn.addActionListener(e -> {
            stopAnimation();
            maze.resetPath();
            resetUI();
            mazePanel.repaint();
        });
        
        return sidePanel;
    }

    private void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }

    private void resetUI() {
        statusLabel.setText("Status: Siap");
        costLabel.setText("Path Cost: 0");
        stepLabel.setText("Total Langkah: 0");
    }

    private void solveAndAnimate() {
        stopAnimation();
        maze.resetPath(); // Otomatis bersihkan jalur sebelum mencoba algoritma lain
        mazePanel.repaint();
        
        String algo = (String) algorithmBox.getSelectedItem();
        statusLabel.setText("Status: Menghitung...");

        new Thread(() -> {
            PathfindingAlgorithm solver = switch(algo) {
                case "BFS" -> new BFSAlgorithm();
                case "DFS" -> new DFSAlgorithm();
                case "Dijkstra" -> new DijkstraAlgorithm();
                default -> new AStarAlgorithm();
            };
            
            solver.solve(maze);

            SwingUtilities.invokeLater(() -> {
                costLabel.setText("Path Cost: " + maze.getTotalCost());
                stepLabel.setText("Total Langkah: " + maze.getPath().size());
                startMovement();
            });
        }).start();
    }

    private void startMovement() {
        List<Maze.Cell> path = maze.getPath();
        if (path.isEmpty()) {
            statusLabel.setText("Status: Jalur Tak Ada!");
            return;
        }

        animationTimer = new Timer(80, new java.awt.event.ActionListener() {
            private int index = 0;
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (index < path.size()) {
                    maze.setPlayerCell(path.get(index));
                    statusLabel.setText("Status: Bergerak...");
                    mazePanel.repaint();
                    index++;
                } else {
                    ((Timer)e.getSource()).stop();
                    statusLabel.setText("Status: Sampai! 🏁");
                }
            }
        });
        animationTimer.start();
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new MazeGame().setVisible(true));
    }
}