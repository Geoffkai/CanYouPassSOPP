<#
Simple run script for this project (PowerShell).
Usage: Open PowerShell in project root and run `./run.ps1`
#>
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
Set-Location $scriptDir

if (-Not (Test-Path target\classes)) {
    Write-Error "target\classes not found. Run build.ps1 first."
    exit 1
}

Write-Output "Running Main..."
java -cp "target\classes;lib/*" Main
