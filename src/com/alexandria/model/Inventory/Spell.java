package com.alexandria.model.Inventory;

import com.alexandria.model.Player.Player;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Traversal.Door;
import com.alexandria.model.NPC.NPC;

import java.io.Serializable;

// Generic Spell class
public class Spell<E extends Spell.SpellEffect> extends Item implements Serializable {
    private E effect;

    public Spell(String name, String description, int id, boolean isVisible, E effect) {
        super(name, description, id, isVisible);
        this.effect = effect;
    }

    public String cast(Player caster, Object target) {
        return effect.apply(caster, target);
    }

    public E getEffect() {
        return effect;
    }

    // Common interface for all spell effects
    interface SpellEffect extends Serializable {
        String apply(Player caster, Object target);
    }

    // --- Concrete spell effects ---

    public static class LightEffect implements SpellEffect {
        @Override
        public String apply(Player caster, Object target) {
            Lightsource orb = new Lightsource("magical orb", "A glowing orb that illuminates dark areas.", 999, true, false, false);
            String result = orb.turnOn();
            return result;
        }
    }
    /*
    class AttackEffect implements SpellEffect {
        private int damage;
        public AttackEffect(int damage) { this.damage = damage; }

        @Override
        public String apply(Player caster, Object target) {
            if (target instanceof NPC npc) {
                npc.takeDamage(damage);
                System.out.println("The NPC takes " + damage + " damage!");
            }
        }
    }

    class StunEffect implements SpellEffect {
        private int duration;
        public StunEffect(int duration) { this.duration = duration; }

        @Override
        public String apply(Player caster, Object target) {
            if (target instanceof NPC npc) {
                npc.stun(duration);
                System.out.println("The NPC is stunned for " + duration + " seconds!");
            }
        }
    }

    class UnlockEffect implements SpellEffect {
        @Override
        public String apply(Player caster, Object target) {
            if (target instanceof Door door) {
                door.unlock();
                System.out.println("The door unlocks magically!");
            }
        }
    }

    class TeleportEffect implements SpellEffect {
        private Room destination;
        public TeleportEffect(Room destination) { this.destination = destination; }

        @Override
        public String apply(Player caster, Object target) {
            caster.setLocation(destination);
            System.out.println("You teleport to " + destination.getName());
        }
    }

    class MapEffect implements SpellEffect {
        @Override
        public String apply(Player caster, Object target) {
            System.out.println("A magical map reveals the surrounding area!");
            // Could generate a map view for the player here
        }
    }*/

}
