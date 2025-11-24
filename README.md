# Machine Problem Files — Build & Run

Quick steps to build and run this Java Swing project on Windows PowerShell.

Build
1. Open PowerShell in the repository root (where `src` and `lib` are).
2. Run:

```powershell
./build.ps1
```

Run
1. After a successful build, run:

```powershell
./run.ps1
```

Notes
- The scripts compile all `.java` files under `src` into `target\classes` and use JARs placed in `lib`.
- If `Main` is in a package, update `run.ps1` to use the fully-qualified name (e.g., `gui.Main`).
- Ensure a JDK (javac/java) is installed and on your PATH.
