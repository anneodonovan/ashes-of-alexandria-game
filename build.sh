#!/bin/bash
# Compile all Java files with Gson + JavaFX

javac -cp "lib/gson-2.13.2.jar" \
      --module-path ~/javafx/javafx-sdk-21.0.9/lib \
      --add-modules javafx.controls,javafx.fxml \
      -d bin $(find src -name "*.java")
