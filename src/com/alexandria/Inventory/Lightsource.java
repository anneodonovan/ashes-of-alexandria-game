package com.alexandria.Inventory;

import java.util.Timer;
import java.util.TimerTask;

public class Lightsource extends Item {
    private boolean isLit;
    private Timer timer = new Timer();
    private boolean used;

    public Lightsource(String name, String description, String Location, int id, boolean isVisible, boolean isLit, boolean used) {
        super(name, description, Location, id, isVisible);
        this.isLit = isLit;
        this.used = used;
    }

    public boolean isLit() {
        return isLit;
    }
    
    public void turnOn() {
        if (used) {
            System.out.println(getName() + " has already been used and cannot be lit again.");
            return;
        } else {
            isLit = true;
            used = true;
            System.out.println(getName() + " is now lit.");

            // schedule a task to turn off the light after the specified duration
            timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isLit = false;
                System.out.println(getName() + " has turned off, plunging you into darkness.");
            }
        }, 100000); // 10 minutes
        }
    }
}
