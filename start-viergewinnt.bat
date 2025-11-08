@echo off
echo ================================
echo   4 Gewinnt - Web Server
echo ================================
echo.
echo Kompiliere Projekt...
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo Fehler beim Kompilieren!
    pause
    exit /b 1
)

echo.
echo Starte Web-Server...
echo Server laeuft auf: http://localhost:8081/connectfour.html
echo.
echo Druecke Strg+C zum Beenden
echo.
java -cp "target/classes;target/jmh-benchmarks.jar" de.inosofttech.example.viergewinnt.ConnectFourWebServer
pause
