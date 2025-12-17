# Maze - Pathfinding Visualizer 🏃‍♂️

**Maze** is a Java-based **GUI** application designed to visualize various **pathfinding algorithms** within a dynamic maze environment. This project incorporates a **terrain cost system**, allowing each algorithm to demonstrate unique behaviors and results based on the weight of the path.

## Key Features:
### Interactive Visualization
* Watch the character 🏃 move **step-by-step** along the calculated path.
* Observe the logic behind path discovery through smooth, real-time animations.

### Supported Pathfinding Algorithms
Compare how different algorithm solve the same puzzle:
* **Breadth-First Search (BFS)**: Focuses on the **fewest number of steps**, ignoring terrain costs.
* **Depth-First Search (DFS)**: An exploratory approach that dives deep into paths; adventurous but rarely optimal.
* **Dijkstra’s Algorithm**: The gold standard for finding the **cheapest path** based on accumulated terrain costs.
* **A*** **(A-Star)**: A smart, high-performance algorithm that uses **heuristics** to find the most efficient route both in cost and speed.

### Terrain Cost System
Different tiles impose different "penalties", forcing smart algorithms to make strategic choices:
* ⬜ **Default Terrain**: Cost 0
* 🟩 **Grass**: Cost 1
* 🟫 **Mud**: Cost 5
* 🟦 **Water**: Cost 10

### Multiple Routes
The maze is generated with **alternative paths and loops**, ensuring that algorithms have multiple options to choose from, highlighting the difference between "the shortest path" and "the cheapest path."

## How to Run
1. Ensure **Java JDK** is installed on your system.
2. Clone this repository or download all `.java` files.
3. Open your **terminal or command prompt** in the project folder.
4. Compile the source code:
   ```bash
   javac *.java
5. Run the program:
   ```bash
   java MazeGame

## Project Goals
This project serves as an educational tool to:
* Visualize the internal logic of famous search algorithms.
* Compare the efficiency of BFS, DFS, Dijkstra, and A* in real-time.
* Observing the effect of terrain costs on the resulting path.
