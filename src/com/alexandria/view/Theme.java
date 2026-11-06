package com.alexandria.view;

import java.net.URL;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public final class Theme {
    public static final BooleanProperty dark = new SimpleBooleanProperty(false);

    private static final String DARK_CLASS = "dark";
    private static final String STYLESHEET = findStylesheet();

    private Theme() {}

    private static String findStylesheet() {
        URL url = Theme.class.getResource("/dark.css");
        if (url == null) {
            url = Theme.class.getResource("/resources/dark.css");
        }
        if (url == null) {
            throw new IllegalStateException("dark.css not found on the classpath");
        }
        return url.toExternalForm();
    }

    public static void install(Scene scene, Parent root) {
        scene.getStylesheets().add(STYLESHEET);
        apply(root, dark.get());
        dark.addListener((obs, was, is) -> apply(root, is));
    }

    public static void style(Dialog<?> dialog) {
        DialogPane pane = dialog.getDialogPane();
        pane.getStylesheets().add(STYLESHEET);
        apply(pane, dark.get());
    }

    private static void apply(Node node, boolean on) {
        node.getStyleClass().remove(DARK_CLASS);
        if (on) {
            node.getStyleClass().add(DARK_CLASS);
        }
    }
}
