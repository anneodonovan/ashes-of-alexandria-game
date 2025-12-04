package com.alexandria.model.Inventory;

import java.util.Timer;
import java.util.TimerTask;

public class Lightsource extends Item {
    private boolean isLit;
    private transient Timer timer = new Timer();
    private boolean used;

    public Lightsource(String name, String description, int id, boolean isVisible, boolean isLit, boolean used) {
        super(name, description, id, isVisible);
        this.isLit = isLit;
        this.used = used;
    }

    public boolean isLit() {
        return isLit;
    }
    
    public String turnOn() {
        StringBuilder out = new StringBuilder();

        if (isLit) {
            out.append("The " + getName() + " is already lit!\n");
            return out.toString();
        } else if (used) {
            out.append(getName() + " has already been used and cannot be lit again.\n");
            return out.toString();
        } else {
            isLit = true;
            used = true;
            out.append(getName() + " is now lit.\n");

            // schedule a task to turn off the light after the specified duration
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isLit = false;
                    out.append("\n" + getName() + " has turned off, plunging you into darkness.\n");
                }
            }, 10000); // 10 minutes
        }
        return out.toString();
    }
}
