#!/bin/bash
# Run the main JavaFX class with Gson + JavaFX

java -cp "bin:lib/gson-2.13.2.jar" \
     --module-path ~/javafx/javafx-sdk-21.0.9/lib \
     --add-modules javafx.controls,javafx.fxml \
    com.alexandria.view.GameFrame
