<#
Simple run script for this project (PowerShell).
Usage: Open PowerShell in project root and run `./run.ps1`
#>
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $scriptDir

# Check for compiled classes in out/ directory
if (Test-Path "out") {
    Write-Output "Running from out/classes..."
    java -cp "out;lib/*" gui.Main
}
# Check for Maven compiled classes
elseif (Test-Path "target\classes") {
    Write-Output "Running from target/classes..."
    java -cp "target\classes;lib/*" gui.Main
}
else {
    Write-Error "No compiled classes found. Run ./build-full.ps1 first."
    exit 1
}
