# Complete JAR Builder with proper compilation order
Write-Host "===================================" -ForegroundColor Cyan
Write-Host "Building JAR (Full Compilation)" -ForegroundColor Cyan
Write-Host "===================================" -ForegroundColor Cyan
Write-Host ""

# Clean
Write-Host "[1/5] Cleaning..." -ForegroundColor Yellow
if (Test-Path "out") { Remove-Item -Recurse -Force "out" }
if (Test-Path "ProgrammingQuizGame.jar") { Remove-Item -Force "ProgrammingQuizGame.jar" }
New-Item -ItemType Directory -Path "out" -Force | Out-Null
Write-Host "Done" -ForegroundColor Green

# Get JSON library
Write-Host "[2/5] Preparing dependencies..." -ForegroundColor Yellow
if (-not (Test-Path "lib\json-20231013.jar")) {
    New-Item -ItemType Directory -Path "lib" -Force | Out-Null
    Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/json/json/20231013/json-20231013.jar" -OutFile "lib\json-20231013.jar"
}
Write-Host "Done" -ForegroundColor Green

# Compile in correct order
Write-Host "[3/5] Compiling all source files..." -ForegroundColor Yellow

# Compile everything at once
$srcPath = "src"
javac -d out -cp "lib\json-20231013.jar" -sourcepath $srcPath `
    "$srcPath\Main.java" `
    "$srcPath\logic\Question.java" `
    "$srcPath\logic\QuestionBank.java" `
    "$srcPath\logic\Player.java" `
    "$srcPath\logic\characters\Classmate.java" `
    "$srcPath\logic\characters\Anon.java" `
    "$srcPath\logic\characters\Elmer.java" `
    "$srcPath\logic\characters\Geoff.java" `
    "$srcPath\logic\characters\Merry.java" `
    "$srcPath\logic\characters\Yvonne.java" `
    "$srcPath\logic\data\Record.java" `
    "$srcPath\logic\data\Scoreboard.java" `
    "$srcPath\logic\tools\DebugTools.java" `
    "$srcPath\logic\tools\Timer.java" `
    "$srcPath\logic\GameManager.java" `
    "$srcPath\gui\audio\SoundManager.java" `
    "$srcPath\gui\BackgroundPanel.java" `
    "$srcPath\gui\GameState.java" `
    "$srcPath\gui\InstructionsPanel.java" `
    "$srcPath\gui\CategoryPanel.java" `
    "$srcPath\gui\ClassmatesPanel.java" `
    "$srcPath\gui\TopicsPanel.java" `
    "$srcPath\gui\GameScreen.java" `
    "$srcPath\gui\GameOverPanel.java" `
    "$srcPath\gui\SchoolRecordPanel.java" `
    "$srcPath\gui\PlayPanel.java" `
    "$srcPath\gui\Menu.java" `
    "$srcPath\gui\GUIMain.java"

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!" -ForegroundColor Red
    exit 1
}
Write-Host "Compiled successfully" -ForegroundColor Green

# Copy resources
Write-Host "[4/5] Copying resources..." -ForegroundColor Yellow
Copy-Item -Path "src\img" -Destination "out\img" -Recurse -Force
Copy-Item -Path "*.json" -Destination "out\" -Force
if (Test-Path "src\gui\audio") {
    New-Item -ItemType Directory -Path "out\gui\audio" -Force | Out-Null
    Copy-Item -Path "src\gui\audio\*" -Destination "out\gui\audio\" -Recurse -Force -ErrorAction SilentlyContinue
}
Write-Host "Done" -ForegroundColor Green

# Create JAR
Write-Host "[5/5] Creating JAR..." -ForegroundColor Yellow
$manifestContent = "Manifest-Version: 1.0`nMain-Class: gui.Main`n`n"
[System.IO.File]::WriteAllText("$PWD\out\MANIFEST.MF", $manifestContent)

# Extract JSON library classes
Push-Location "out"
jar xf "..\lib\json-20231013.jar"
Remove-Item "META-INF" -Recurse -Force -ErrorAction SilentlyContinue
Pop-Location

# Create final JAR
jar cfm QuizGame.jar out\MANIFEST.MF -C out .

if (Test-Path "QuizGame.jar") {
    $size = [math]::Round((Get-Item "QuizGame.jar").Length / 1MB, 2)
    Write-Host "JAR created successfully: $size MB" -ForegroundColor Green
} else {
    Write-Host "ERROR: JAR creation failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "===================================" -ForegroundColor Cyan
Write-Host "Build Complete!" -ForegroundColor Green
Write-Host "===================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Test: java -jar QuizGame.jar" -ForegroundColor Yellow
Write-Host ""
