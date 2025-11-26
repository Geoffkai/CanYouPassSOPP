# Create Submission ZIP Files Script
# This script creates the required ZIP files for submission

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Programming Quiz Game - ZIP Creator" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if JAR exists
$jarPath = "QuizGame.jar"
if (-not (Test-Path $jarPath)) {
    Write-Host "ERROR: JAR file not found!" -ForegroundColor Red
    Write-Host "Please run build-full.ps1 first" -ForegroundColor Yellow
    exit 1
}

# Check if EXE exists
$exePath = "QuizGame.exe"
$exeExists = Test-Path $exePath

# Create timestamp for backup
$timestamp = Get-Date -Format "yyyyMMdd_HHmmss"

Write-Host "[1/2] Creating Source Code ZIP..." -ForegroundColor Yellow

# Source code files to include
$sourceFiles = @(
    "src",
    "pom.xml",
    "README.md",
    "programming.json",
    "theoretical.json",
    "launch4j-config.xml",
    "build-jar.ps1",
    "EXECUTABLE_GUIDE.md"
)

# Remove old ZIP if exists
$sourceZip = "QuizGame-SourceCode.zip"
if (Test-Path $sourceZip) {
    Remove-Item $sourceZip
}

# Create source ZIP
Compress-Archive -Path $sourceFiles -DestinationPath $sourceZip -Force
$sourceSize = (Get-Item $sourceZip).Length / 1MB
Write-Host "✓ Source code ZIP created: $sourceZip" -ForegroundColor Green
Write-Host "  Size: $([math]::Round($sourceSize, 2)) MB" -ForegroundColor Gray
Write-Host ""

Write-Host "[2/2] Creating Standalone Executable ZIP..." -ForegroundColor Yellow

# Standalone files to include
$standaloneFiles = @()

if ($exeExists) {
    $standaloneFiles += $exePath
    Write-Host "  Including: QuizGame.exe" -ForegroundColor Gray
} else {
    Write-Host "  WARNING: EXE not found, including JAR instead" -ForegroundColor Yellow
    $standaloneFiles += $jarPath
    Write-Host "  Including: $jarPath" -ForegroundColor Gray
}

# Include resource files
$standaloneFiles += "src\img"
$standaloneFiles += "programming.json"
$standaloneFiles += "theoretical.json"

# Check if audio folder exists
if (Test-Path "src\gui\audio") {
    $standaloneFiles += "src\gui\audio"
    Write-Host "  Including: src\gui\audio" -ForegroundColor Gray
}

# Create README for standalone
$readmeContent = @"
# Programming Quiz Game

## How to Run

### If using the EXE file:
1. Double-click QuizGame.exe
2. Make sure Java 17+ is installed on your system
3. If prompted, download Java from: https://www.java.com/download/

### If using the JAR file:
1. Open command prompt in this folder
2. Run: java -jar QuizGame.jar

## Requirements
- Windows OS
- Java 17 or higher
- 4GB RAM recommended

## Troubleshooting
- If images don't show: Make sure src/img folder is in the same directory
- If JSON errors occur: Ensure programming.json and theoretical.json are present
- If game won't start: Check that Java is properly installed

## Game Features
- Two categories: Theoretical and Programming
- 5 levels with 2 questions each
- Debug tools: Refactor, ConsoleLog, CtrlC, AutoDebug
- Score tracking and progression
- Custom feedback panels

## How to Play
1. Launch the game (double-click EXE or run: java -jar ProgrammingQuizGame.jar)
2. Click splash screen to continue
3. Select a classmate character
4. Choose a category (Theoretical or Programming)
5. Answer questions to earn points
6. Use debug tools strategically
7. Complete all 10 questions

## Troubleshooting
- If game won't start: Install Java 17+ from https://www.java.com/download/
- If images missing: Ensure src/img folder is in same directory
- If errors occur: Run from command prompt to see error messages

## Credits
Developed as CMSC 13 Machine Problem
Requires Java 17 or higher
"@

Set-Content -Path "STANDALONE_README.txt" -Value $readmeContent

$standaloneFiles += "STANDALONE_README.txt"

# Remove old ZIP if exists
$standaloneZip = "QuizGame-Standalone.zip"
if (Test-Path $standaloneZip) {
    Remove-Item $standaloneZip
}

# Create standalone ZIP
Compress-Archive -Path $standaloneFiles -DestinationPath $standaloneZip -Force
$standaloneSize = (Get-Item $standaloneZip).Length / 1MB
Write-Host "✓ Standalone ZIP created: $standaloneZip" -ForegroundColor Green
Write-Host "  Size: $([math]::Round($standaloneSize, 2)) MB" -ForegroundColor Gray
Write-Host ""

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "ZIP Creation Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Files created:" -ForegroundColor Yellow
Write-Host "  1. $sourceZip - Submit this for source code" -ForegroundColor White
Write-Host "  2. $standaloneZip - Submit this for executable" -ForegroundColor White
Write-Host ""

if (-not $exeExists) {
    Write-Host "NOTICE: EXE file was not found!" -ForegroundColor Yellow
    Write-Host "The standalone ZIP contains the JAR file instead." -ForegroundColor Yellow
    Write-Host "To create the EXE:" -ForegroundColor Yellow
    Write-Host "  1. Install Launch4j from: https://launch4j.sourceforge.net/" -ForegroundColor White
    Write-Host "  2. Run: launch4jc.exe launch4j-config.xml" -ForegroundColor White
    Write-Host "  3. Run this script again to recreate the standalone ZIP" -ForegroundColor White
    Write-Host ""
}

Write-Host "Testing checklist:" -ForegroundColor Yellow
Write-Host "  [ ] Extract both ZIP files to test folders" -ForegroundColor White
Write-Host "  [ ] Verify source code compiles: mvn clean package" -ForegroundColor White
Write-Host "  [ ] Verify standalone executable runs correctly" -ForegroundColor White
Write-Host "  [ ] Test all game features" -ForegroundColor White
Write-Host "  [ ] Check for bugs" -ForegroundColor White
Write-Host ""
