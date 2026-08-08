#!/bin/bash
# Create an executable JAR with JavaFX and Gson embedded

set -e

mkdir -p dist

echo "🔨 Compiling Java files..."
javac -cp "lib/gson-2.13.2.jar" \
      --module-path ~/javafx/javafx-sdk-21.0.9/lib \
      --add-modules javafx.controls,javafx.fxml \
      -d bin $(find src -name "*.java")

echo "📦 Creating manifest..."
cat > /tmp/Manifest.txt << EOF
Manifest-Version: 1.0
Main-Class: com.alexandria.view.GameFrame
Class-Path: gson-2.13.2.jar resources/
EOF

echo "🎁 Packaging JAR..."
cd bin
jar cvfm ../dist/Alexandria.jar /tmp/Manifest.txt com/
cd ..

echo "📚 Adding dependencies..."
jar uf dist/Alexandria.jar -C lib gson-2.13.2.jar
jar uf dist/Alexandria.jar -C . resources/

chmod +x dist/Alexandria.jar

echo ""
echo "✅ Executable JAR created: dist/Alexandria.jar"
echo ""
echo "Run with:"
echo "  java --module-path ~/javafx/javafx-sdk-21.0.9/lib --add-modules javafx.controls,javafx.fxml -jar dist/Alexandria.jar"
