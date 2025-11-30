<#
Simple build script for this project (PowerShell).
Usage: Open PowerShell in project root and run `./build.ps1`
#>
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $scriptDir

Write-Output "Compiling Java sources..."
New-Item -ItemType Directory -Force -Path target\classes | Out-Null

$files = Get-ChildItem -Path .\src -Recurse -Filter *.java |
         Where-Object { $_.FullName -notmatch '\\(target|bin)\\' } |
         ForEach-Object { $_.FullName }

if ($files.Count -eq 0) {
    Write-Error "No Java source files found under .\src"
    exit 1
}

javac -encoding UTF-8 -d target\classes -cp "lib/*" $files

if ($LASTEXITCODE -ne 0) {
    Write-Error "Compilation failed with exit code $LASTEXITCODE"
    exit $LASTEXITCODE
} else {
    Write-Output "Compilation succeeded. Classes are in target\classes"
    # Ensure runtime data files are available on the classpath / next to classes
    if (Test-Path .\school_records.txt) {
        Copy-Item -Path .\school_records.txt -Destination target\classes -Force
        Write-Output "Copied school_records.txt to target\classes"
    }
}
