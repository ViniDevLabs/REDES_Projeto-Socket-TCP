@echo off
REM Criar diretório de build
if not exist out (
    mkdir out
)

REM Compilar os arquivos Java
javac -d out src\main\java\*.java

REM Copiar cities.txt para a pasta de de out
copy src\cities.txt out\
