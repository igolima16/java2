@echo off
chcp 65001 > nul
echo Compilando TP04.java...
javac -encoding UTF-8 -cp ".;mssql-jdbc.jar;h2.jar" TP04.java
if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b %errorlevel%
)
echo Executando TP04...
java -cp ".;mssql-jdbc.jar;h2.jar" TP04
pause
