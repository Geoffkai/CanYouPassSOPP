# Create Submission ZIP Files Script

Write-Host '========================================' -ForegroundColor Cyan
Write-Host 'Programming Quiz Game - ZIP Creator' -ForegroundColor Cyan
Write-Host '========================================' -ForegroundColor Cyan
Write-Host ''

$sourceFiles = @('src', 'pom.xml', 'README.md', 'programming.json', 'theoretical.json', 'launch4j-config.xml', 'build-full.ps1', 'build.ps1', 'run.ps1', 'QUICK_START.md', 'school_records.txt')
$sourceZip = 'QuizGame-SourceCode.zip'
if (Test-Path $sourceZip) { Remove-Item $sourceZip }
Compress-Archive -Path $sourceFiles -DestinationPath $sourceZip -Force
Write-Host 'Source code ZIP created' -ForegroundColor Green

$standaloneFiles = @('QuizGame.exe', 'src', 'programming.json', 'theoretical.json', 'school_records.txt')
$standaloneZip = 'QuizGame-Standalone.zip'
if (Test-Path $standaloneZip) { Remove-Item $standaloneZip }
Compress-Archive -Path $standaloneFiles -DestinationPath $standaloneZip -Force
Write-Host 'Standalone ZIP created' -ForegroundColor Green
