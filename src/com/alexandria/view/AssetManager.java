package com.alexandria.view;

import com.alexandria.model.Inventory.Item;
import com.alexandria.model.Inventory.Key;
import com.alexandria.model.Inventory.Lightsource;
import com.alexandria.model.Inventory.Scroll;
import com.alexandria.model.Inventory.Spell;

import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// Loads pixel-art assets by logical key (room name, NPC dialogue id, item type) and
// falls back to a generated placeholder image when the real asset file isn't present
// yet, so the UI always renders something instead of a broken-image icon.
public class AssetManager {

    private static final Map<String, Image> cache = new HashMap<>();
    private static final int PLACEHOLDER_SIZE = 128;

    private AssetManager() {}

    public static Image getRoomArt(String roomName) {
        String key = sanitize(roomName);
        return loadOrPlaceholder("/art/rooms/" + key + ".png", "room:" + key, roomName);
    }

    public static Image getNpcPortrait(String dialogueFileName) {
        String key = sanitize(dialogueFileName);
        return loadOrPlaceholder("/art/npc/" + key + ".png", "npc:" + key, dialogueFileName);
    }

    public static Image getItemIcon(Item item) {
        String type = itemType(item);
        String label = (item != null) ? item.getName() : type;
        return loadOrPlaceholder("/art/items/" + type + ".png", "item:" + type, label);
    }

    private static String itemType(Item item) {
        if (item instanceof Key) {
            return "key";
        } else if (item instanceof Scroll) {
            return "scroll";
        } else if (item instanceof Lightsource) {
            return "lightsource";
        } else if (item instanceof Spell) {
            return "spell";
        } else {
            return "generic";
        }
    }

    // lowercase, apostrophes stripped, everything else non-alphanumeric collapsed to
    // underscores, e.g. "Eratosthene's chamber" -> "eratosthenes_chamber"
    public static String sanitize(String name) {
        if (name == null) {
            return "unknown";
        }
        String result = name.toLowerCase().replace("'", "").trim();
        result = result.replaceAll("[^a-z0-9]+", "_");
        result = result.replaceAll("^_+|_+$", "");
        return result.isEmpty() ? "unknown" : result;
    }

    private static Image loadOrPlaceholder(String resourcePath, String cacheKey, String label) {
        Image cached = cache.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        Image image = tryLoad(resourcePath);
        if (image == null) {
            image = generatePlaceholder(label);
        }
        cache.put(cacheKey, image);
        return image;
    }

    private static Image tryLoad(String resourcePath) {
        try (InputStream in = AssetManager.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                return null;
            }
            Image image = new Image(in);
            return image.isError() ? null : image;
        } catch (Exception e) {
            return null;
        }
    }

    private static Image generatePlaceholder(String label) {
        Canvas canvas = new Canvas(PLACEHOLDER_SIZE, PLACEHOLDER_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Color background = colorForLabel(label);
        gc.setFill(background);
        gc.fillRect(0, 0, PLACEHOLDER_SIZE, PLACEHOLDER_SIZE);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeRect(2, 2, PLACEHOLDER_SIZE - 4, PLACEHOLDER_SIZE - 4);

        String trimmed = (label == null) ? "" : label.trim();
        String initial = trimmed.isEmpty() ? "?" : trimmed.substring(0, 1).toUpperCase();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 64));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(initial, PLACEHOLDER_SIZE / 2.0, PLACEHOLDER_SIZE / 2.0 + 4);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return canvas.snapshot(params, null);
    }

    private static Color colorForLabel(String label) {
        int hash = (label == null) ? 0 : label.hashCode();
        double hue = Math.abs(hash % 360);
        return Color.hsb(hue, 0.45, 0.55);
    }
}
