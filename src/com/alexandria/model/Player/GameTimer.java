package com.alexandria.model.Player;

import java.util.concurrent.*;
import javafx.application.Platform;
import java.util.function.Consumer;
import java.io.Serializable;

public class GameTimer implements Serializable {
    private transient ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private transient ScheduledFuture<?> future;
    private long elapsedMillis; // total time played so far
    private transient long sessionStart; // when current session began
    private static final long LIMIT_MS = 4 * 60 * 60 * 1000; // 4 hours

    public void start(Runnable onTimeout, Consumer<String> onTick) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        sessionStart = System.currentTimeMillis();
        scheduleTask(onTimeout, onTick);
    }

    public long getStartTime() {
        return sessionStart;
    }

    public void resume(Runnable onTimeout, Consumer<String> onTick) {
        // Reset session start for this new session
        sessionStart = System.currentTimeMillis();

        // If total play time already exceeded, end immediately
        if (elapsedMillis >= LIMIT_MS) {
            Platform.runLater(onTimeout);
            return;
        }

        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
        }
        if (future == null || future.isDone()) {
            // Push an immediate update so the label refreshes right away
            long sessionElapsed = System.currentTimeMillis() - sessionStart;
            long totalElapsed = elapsedMillis + sessionElapsed;
            long remaining = LIMIT_MS - totalElapsed;

            long seconds = (remaining / 1000) % 60;
            long minutes = (remaining / (1000 * 60)) % 60;
            long hours   = (remaining / (1000 * 60 * 60));
            String countdown = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            Platform.runLater(() -> onTick.accept(countdown));

            // Now schedule repeating updates
            scheduleTask(onTimeout, onTick);
        }
    }

    private void scheduleTask(Runnable onTimeout, Consumer<String> onTick) {
        future = scheduler.scheduleAtFixedRate(() -> {
            synchronized (this) {
                long sessionElapsed = System.currentTimeMillis() - sessionStart;
                long totalElapsed = elapsedMillis + sessionElapsed;
                long remaining = LIMIT_MS - totalElapsed;

                if (remaining <= 0) {
                    Platform.runLater(onTimeout); // end game
                    cancel();
                } else {
                    long seconds = (remaining / 1000) % 60;
                    long minutes = (remaining / (1000 * 60)) % 60;
                    long hours   = (remaining / (1000 * 60 * 60));

                    String countdown = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                    Platform.runLater(() -> onTick.accept(countdown));
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void saveProgress() {
        elapsedMillis += (System.currentTimeMillis() - sessionStart);
    }

    public void cancel() {
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}