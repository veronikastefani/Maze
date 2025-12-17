# Maze – Pathfinding Visualizer 🏃🏼‍♀️

**Maze** merupakan sebuah game Java berbasis **GUI** yang dirancang untuk memvisualisasikan berbagai **algoritma pencarian jalur (*pathfinding*)** dalam sebuah labirin dinamis. Game ini juga menerapkan **sistem biaya medan (*terrain cost*)** sehingga setiap algoritma dapat menunjukkan perilaku dan hasil yang berbeda.

## Fitur Utama:
### 🎬 Visualisasi Interaktif
* Karakter 🏃 akan bergerak **selangkah demi selangkah** mengikuti jalur yang dipilih sesuai algoritma yang diinginkan.
* Proses pencarian jalur dapat diamati secara langsung melalui animasi.
### 🧠 Beragam Algoritma Pathfinding
Game ini mendukung beberapa algoritma, yaitu:
* **Breadth First Search (BFS)**
  Mencari jalur dengan **jumlah langkah paling sedikit** (tanpa mempertimbangkan biaya).
* **Depth First Search (DFS)**
  Menjelajah jalur secara mendalam, bersifat eksploratif dan tidak menjamin jalur terbaik.
* **Dijkstra**
  Menentukan jalur dengan **biaya total terendah** berdasarkan cost medan.
* **A*** **(A-Star)**
  Algoritma yang lebih efisien dengan memanfaatkan **heuristik** untuk mencapai tujuan lebih cepat dan optimal.

### 🌍 Sistem Biaya Medan (*Terrain Cost*)
Setiap jenis medan memiliki biaya berbeda yang memengaruhi jalur yang dipilih algoritma:
* ⬜ **Default** : Cost 0
* 🟩 **Rumput (Grass)** : Cost 1
* 🟫 **Lumpur (Mud)** : Cost 5
* 🟦 **Air (Water)** : Cost 10

### 🔀 Jalur Alternatif
Labirin dirancang dengan **banyak rute berbeda**, sehingga hasil pencarian dapat bervariasi tergantung algoritma yang digunakan.

## 🛠️ Cara Menjalankan Aplikasi
1. Pastikan **Java JDK** sudah terpasang pada komputer.
2. Clone repository ini atau unduh seluruh file `.java`.
3. Buka **terminal/command prompt** pada folder proyek.
4. Compile seluruh file Java:
   ```bash
   javac *.java
   ```
5. Jalankan program utama:
   ```bash
   java MazeGame
   ```
   
## 🎯 Tujuan Proyek

Proyek ini dibuat sebagai media pembelajaran untuk:
* Memahami cara kerja algoritma pathfinding
* Membandingkan efisiensi algoritma BFS, DFS, Dijkstra, dan A*
* Melihat pengaruh biaya medan terhadap jalur yang dihasilkan
