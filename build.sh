#!/bin/bash
mkdir -p out
javac -d out src/main/java/*.java
cp src/cities.txt out/
