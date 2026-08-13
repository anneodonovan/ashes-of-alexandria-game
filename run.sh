#!/bin/bash
# Run the main JavaFX class with Gson + JavaFX

CP_SEP=":"
case "$(uname -s)" in
    MINGW*|MSYS*|CYGWIN*) CP_SEP=";" ;;
esac

java -cp "bin${CP_SEP}lib/gson-2.13.2.jar${CP_SEP}resources" \
     --module-path ~/javafx/javafx-sdk-21.0.9/lib \
     --add-modules javafx.controls,javafx.fxml \
     com.alexandria.view.GameFrame
