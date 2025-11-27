# 🎓 Can You Pass Survey of Programming Paradigm?

<div align="center">

**A thrilling Java-based quiz game that tests your knowledge of programming concepts!**

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)
[![Build](https://img.shields.io/badge/Build-Passing-success?style=for-the-badge)](build-full.ps1)

</div>

---

## 📖 About The Game

**Can You Pass Survey of Programming Paradigm?** is an educational quiz game designed to challenge your programming knowledge across two main categories: **Theoretical Concepts** and **Programming Fundamentals**. Navigate through 5 increasingly difficult levels while managing your score and learning from your mistakes!

### 🎮 Game Features

- **📚 Two Quiz Categories**
  - **Theoretical**: Test your understanding of programming paradigms, concepts, and theory
  - **Programming**: Challenge your practical coding knowledge and problem-solving skills

- **🎯 5 Progressive Levels**
  - Each level increases in difficulty
  - Unlock new levels by achieving high scores
  - Track your progress through the semester

- **👥 Character Selection**
  - Choose from multiple classmate characters
  - Each character brings unique personality to your quiz journey
  - Meet Anon, Elmer, Geoff, Merry, and Yvonne!

- **🎵 Immersive Audio Experience**
  - Background music to keep you focused
  - Sound effects for correct and incorrect answers
  - Full audio control with volume management

- **🏆 Score Tracking System**
  - Real-time score calculation
  - School record keeping
  - Performance feedback after each question

- **🛠️ Debug Tools** (Development Mode)
  - Quick level navigation
  - Testing utilities for developers
  - Helpful for classroom demonstrations

---

## 🖼️ Game Preview

```
┌─────────────────────────────────────────┐
│  🎯 Select Your Category                │
│                                         │
│  📚 Theoretical  |  💻 Programming      │
│                                         │
│  Choose your path and start learning!  │
└─────────────────────────────────────────┘
```

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 17 or higher**
- **Windows PowerShell** (for build scripts)
- **Maven** (optional, for alternative builds)

### 📥 Installation

1. **Clone the repository**
   ```powershell
   git clone https://github.com/Geoffkai/Machine-Problem-Files.git
   cd Machine-Problem-Files
   ```

2. **Build the project**
   ```powershell
   ./build-full.ps1
   ```
   This will compile all source files and create `QuizGame.jar`

3. **Run the game**
   ```powershell
   ./run.ps1
   ```
   Or double-click `QuizGame.exe` if you have the standalone version

---

## 🎮 How to Play

1. **Start the Game**: Launch from the main menu
2. **Select Category**: Choose between Theoretical or Programming questions
3. **Pick Your Character**: Select your favorite classmate to accompany you
4. **Answer Questions**: Read carefully and select your answer
5. **Get Feedback**: Learn from instant feedback on your answers
6. **Progress Through Levels**: Complete all 5 levels to master the subject!

### 🎯 Scoring System

- ✅ **Correct Answer**: Earn points and boost your score
- ❌ **Incorrect Answer**: Learn from mistakes with detailed feedback
- ⏱️ **Time Bonus**: Answer quickly for bonus points
- 🏆 **Level Completion**: Unlock achievements and new challenges

---

## 🛠️ Development

### Build Scripts

| Script | Purpose | Output |
|--------|---------|--------|
| `build-full.ps1` | Complete build with JAR creation | `QuizGame.jar` |
| `build.ps1` | Quick incremental build | `target\classes\` |
| `run.ps1` | Execute the game | Launches GUI |
| `create-submission.ps1` | Package submission files | ZIP archives |

### Quick Development Workflow

```powershell
# Make code changes
# Then rebuild and test
./build.ps1
./run.ps1
```

### Project Structure

```
Machine-Problem-Files/
├── src/                    # Source code
│   ├── Main.java          # Entry point
│   ├── gui/               # GUI components
│   │   ├── audio/         # Sound system
│   │   └── *.java         # UI panels
│   ├── logic/             # Game logic
│   │   ├── characters/    # Character classes
│   │   ├── data/          # Data management
│   │   └── tools/         # Utility tools
│   └── img/               # Image resources
├── lib/                   # External libraries
├── programming.json       # Programming questions
├── theoretical.json       # Theoretical questions
└── QuizGame.exe          # Standalone executable
```

---

## 📦 Submission Format

This project includes two submission packages:

1. **QuizGame-SourceCode.zip** (47.74 MB)
   - Complete source code
   - Build scripts
   - Documentation
   - All dependencies

2. **QuizGame-Standalone.zip**
   - `QuizGame.exe` (ready to run)
   - Required resources (`src/img/`, `src/gui/audio/`)
   - Question banks (JSON files)

---

## 🔧 Technical Stack

- **Language**: Java 21
- **GUI Framework**: Swing
- **Build Tool**: Maven + Custom PowerShell Scripts
- **Audio**: javax.sound.sampled
- **Data Format**: JSON for question banks
- **Packaging**: Launch4j (JAR to EXE conversion)

---

## 👥 Contributors

- **Anon** - The mysterious programmer
- **Elmer** - The debugging expert
- **Geoff** - The architecture enthusiast
- **Merry** - The testing specialist
- **Yvonne** - The documentation guru

---

## 📝 License

This project is part of the CMSC 13 Machine Problem coursework.

---

## 🙏 Acknowledgments

- Built with ❤️ for Survey of Programming Paradigm course
- Special thanks to our instructors and classmates
- Question banks curated from course materials

---

<div align="center">

**🎓 Ready to test your programming knowledge? Let's get started! 🚀**

[Download Latest Release](../../releases) | [Report Bug](../../issues) | [Request Feature](../../issues)

Made with 💻 by CMSC 13 Students

</div>
