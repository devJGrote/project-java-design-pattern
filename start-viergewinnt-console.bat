@echo off
echo ================================
echo   4 Gewinnt - Konsolen Modus
echo ================================
echo.
echo Kompiliere Projekt...
call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo Fehler beim Kompilieren!
    pause
    exit /b 1
)

echo.
echo Starte Spiel...
echo.
java -cp "target/classes;target/jmh-benchmarks.jar" de.inosofttech.example.viergewinnt.ConnectFourMain
pause
