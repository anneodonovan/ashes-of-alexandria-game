package com.alexandria.view;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;

public class ConfettiEffect {
    private final Pane pane;
    private final Random random = new Random();

    public ConfettiEffect(Pane pane) {
        this.pane = pane;
    }

    public void playConfetti() {
        for (int i = 0; i < 50; i++) { // number of confetti pieces
            Rectangle confetti = new Rectangle(5, 10);
            confetti.setFill(Color.hsb(random.nextInt(360), 1.0, 1.0));
            confetti.setX(random.nextInt((int) pane.getWidth()));
            confetti.setY(-20); // start above the pane

            pane.getChildren().add(confetti);

            TranslateTransition fall = new TranslateTransition(Duration.seconds(3 + random.nextDouble()), confetti);
            fall.setFromY(-20);
            fall.setToY(pane.getHeight() + 20);
            fall.setInterpolator(Interpolator.LINEAR);

            RotateTransition rotate = new RotateTransition(Duration.seconds(2 + random.nextDouble()), confetti);
            rotate.setByAngle(360);
            rotate.setCycleCount(Animation.INDEFINITE);

            ParallelTransition animation = new ParallelTransition(fall, rotate);
            animation.setOnFinished(e -> pane.getChildren().remove(confetti));
            animation.play();
        }
    }
}
