@echo off
REM Batch script to copy frontend files to backend static resources
REM Usage: Run this from the inventory-project folder

echo.
echo ========================================
echo Frontend to Backend Static Sync Script
echo ========================================
echo.

set FRONTEND_DIR=inventory-frontend
set BACKEND_STATIC=inventory-backend\src\main\resources\static

if not exist "%FRONTEND_DIR%" (
    echo ERROR: Frontend folder not found at: %FRONTEND_DIR%
    pause
    exit /b 1
)

if not exist "%BACKEND_STATIC%" (
    echo ERROR: Backend static folder not found at: %BACKEND_STATIC%
    pause
    exit /b 1
)

echo Syncing files...
echo.

REM Copy index.html
echo Copying index.html...
copy "%FRONTEND_DIR%\index.html" "%BACKEND_STATIC%\index.html" >nul 2>&1

REM Copy js folder
echo Copying js folder...
if not exist "%BACKEND_STATIC%\js" mkdir "%BACKEND_STATIC%\js"
xcopy "%FRONTEND_DIR%\js\*" "%BACKEND_STATIC%\js\" /E /Y >nul 2>&1

REM Copy css folder
echo Copying css folder...
if not exist "%BACKEND_STATIC%\css" mkdir "%BACKEND_STATIC%\css"
xcopy "%FRONTEND_DIR%\css\*" "%BACKEND_STATIC%\css\" /E /Y >nul 2>&1

REM Copy assets folder if it exists
if exist "%FRONTEND_DIR%\assets" (
    echo Copying assets folder...
    if not exist "%BACKEND_STATIC%\assets" mkdir "%BACKEND_STATIC%\assets"
    xcopy "%FRONTEND_DIR%\assets\*" "%BACKEND_STATIC%\assets\" /E /Y >nul 2>&1
)

echo.
echo ========================================
echo ✓ Sync complete!
echo ========================================
echo.
echo Frontend source: %FRONTEND_DIR%
echo Backend target: %BACKEND_STATIC%
echo.
pause
