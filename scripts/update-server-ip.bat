@echo off
setlocal enabledelayedexpansion

REM Get the script directory and project root
set "SCRIPT_DIR=%~dp0"
for %%I in ("%SCRIPT_DIR%..") do set "PROJECT_ROOT=%%~fI"

REM Get the local IP address (Windows)
for /f "tokens=2 delims=:" %%a in ('ipconfig ^| findstr /c:"IPv4 Address"') do (
    set "LOCAL_IP=%%a"
    REM Remove leading space
    set "LOCAL_IP=!LOCAL_IP:~1!"
    goto :found
)

echo Could not detect local IP address
exit /b 1

:found
echo Detected IP: %LOCAL_IP%

REM Update the client-side server path provider
set "CLIENT_FILE=%PROJECT_ROOT%\core\logic\data\src\commonMain\kotlin\com\basket\sample\scango\data\common\api\providers\BasketApiServerPathProviderImpl.kt"

if not exist "%CLIENT_FILE%" (
    echo File not found: %CLIENT_FILE%
    exit /b 1
)

REM Create a temporary file with the updated content
powershell -Command "(Get-Content '%CLIENT_FILE%') -replace 'http://\d+\.\d+\.\d+\.\d+:8080', 'http://%LOCAL_IP%:8080' | Set-Content '%CLIENT_FILE%'"

echo Updated %CLIENT_FILE% with IP: %LOCAL_IP%
echo Done! Server IP updated to: %LOCAL_IP%
echo Remember: Server is configured to listen on 0.0.0.0 (all interfaces)

endlocal
