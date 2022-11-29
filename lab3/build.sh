javac src/Main.java src/*/*.java -d build
cd build
jar -cfm Main.jar manifest.txt Main.class */*.class