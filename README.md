# Puzzle Bound 

**Project Type:** 2D Platformer Puzzle Game  
**Language/Tech:** Java (Swing / AWT)  
**Developed By:** Joshua Chu, Dizahlene Catenza, Lander Valeriano  

---

## 📖 Project Overview

**Puzzle Bound** is a 2D platformer game designed to challenge players through unconventional and thought-provoking level mechanics. 

While it initially presents itself as a traditional platformer — where the primary objective is to reach an exit — each level introduces unique and unexpected twists that subvert standard gameplay logic. This design approach encourages players to think creatively, reinterpret rules, and solve platforming puzzles that require out-of-the-box strategies.

---

## ⚙️ Prerequisites

- **Java Development Kit (JDK):** Version 21 or higher is recommended.
- Ensure Java is installed and added to your system's `PATH` variables.

---

## 🚀 How to Build and Run (Command Prompt / CMD)

Make sure you open your Command Prompt (CMD) or terminal in the **root directory** of the project (where `PuzzleBound.java` and the `src/` & `resource/` folders reside).

### ▶ Method A: Direct Compile and Run (No JAR)

1. **Create the output build directory:**
   ```cmd
   mkdir bin
   ```

2. **Compile all Java source files into the bin directory:**
   ```cmd
   javac -d bin PuzzleBound.java src/*.java
   ```

3. **Copy the resource assets into the bin directory:**
   ```cmd
   xcopy /E /I /Y resource bin\resource
   ```

4. **Run the game directly from class files:**
   ```cmd
   java -cp bin PuzzleBound
   ```

### ▶ Method B: Run Executable JAR File

   ```cmd
   java -jar PuzzleBound.jar
   ```
   *(Note: You can also double-click `PuzzleBound.jar` in Windows File Explorer to launch the game.)*

---

## 🎨 Asset Credits

### 🎵 Music
* **Artist:** Pix
* **Tracks Utilized:**
  * "An-Ugly-Heart-But-It-does-Beats"
  * "A-Lonely-Cherry-Tree-_"
* *All tracks are used with appropriate attribution and under terms set by the artist.*

### 👾 Visual Resources
* **Creator:** Pixel Frog
* **Asset Pack Title:** Pixel Adventure
* *These visual elements form the basis of the game's tilemaps, character sprites, and interactive elements. Used in accordance with its distribution license.*

### 🖼️ GUI & UI Assets
* **Asset Pack:** Wooden Pixel Art GUI 32x32

### 🔠 Font
* **Font Name:** Press Start 2P
* **Creator:** CraftPix / CodeMan38
* *Used for UI elements and in-game text to evoke a classic arcade feel.*

---

## 📄 Licensing and Use

This game was created solely for academic purposes. All external assets are credited accordingly and used in compliance with their respective licenses. 

**No commercial distribution is intended.** Redistribution or adaptation of this game must retain full credit to the original developers and third-party asset creators.

---

## 🙏 Acknowledgements

We would like to thank the artists and asset creators whose work helped bring Puzzle Bound to life. We also extend gratitude to our peers and instructor for their feedback and support throughout the development process.

***

*Thank you for playing Puzzle Bound!*