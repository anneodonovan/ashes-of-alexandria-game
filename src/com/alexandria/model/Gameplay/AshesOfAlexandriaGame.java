/* This game is a classic text-based adventure set in a university environment.
   The player starts outside the main entrance and can navigate through different rooms like a 
   lecture theatre, campus pub, computing lab, and admin office using simple text commands (e.g., "go east", "go west").
    The game provides descriptions of each location and lists possible exits.

Key features include:
Room navigation: Moving among interconnected rooms with named exits.
Simple command parser: Recognizes a limited set of commands like "go", "help", and "quit".
Player character: Tracks current location and handles moving between rooms.
Text descriptions: Provides immersive text output describing the player's surroundings and available options.
Help system: Lists valid commands to guide the player.
Overall, it recreates the classic Zork interactive fiction experience with a university-themed setting, 
emphasizing exploration and simple command-driven gameplay
*/
package com.alexandria.model.Gameplay;
import com.alexandria.model.Inventory.Item;
import com.alexandria.model.Inventory.Lightsource;
import com.alexandria.model.Inventory.Scroll;
import com.alexandria.model.Inventory.Key;
//import com.alexandria.model.Inventory.Spell;
import com.alexandria.model.Player.Player;
import com.alexandria.model.Traversal.Direction;
import com.alexandria.model.Traversal.Exit;
import com.alexandria.model.Traversal.Room;
import com.alexandria.model.Traversal.Door;
import com.alexandria.model.Commands.Command;
import com.alexandria.model.Commands.Parser;
import com.alexandria.model.NPC.Assistants;
import com.alexandria.model.NPC.Guardians;
import com.alexandria.model.NPC.NPC;
import com.alexandria.controller.GameController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AshesOfAlexandriaGame {
    private final Parser parser;
    private Player player;

    public AshesOfAlexandriaGame() {
        createGameObjects();
        parser = new Parser();
    }

    public void createGameObjects() {
        Room main_hall, scribing_room, reading_room, lecture_hall, residential_quarter, muse_garden, dining_hall, social_hall, kitchen, courtyard, library, scroll_vault, serapeum, eratosthenes_chamber, sphinx_room, hallway, tower;

        // create rooms
        main_hall = new Room("main hall", "in the main hall. There are grand colomns, marble floors and a high ceiling. The main doors behind you are locked."); //starting room
        lecture_hall = new Room("lecture hall", "in the lecture hall. It is used for public readings, philosophical debates, and teaching. The amphitheater-style seating surrounds a central podium. Outside the windows, you see flames."); //example room
        scribing_room = new Room("scribing room", "in the scribing room. Where texts were transcribed, translated, and annotated. There are benches and piles of scrolls, paper, pens, ink and more, strewn around by the scholars who fled.");
        residential_quarter = new Room("residential quarter", "in the residential quarter. For scholars who lived and worked on-site. Sparse stone rooms with cots, personal scrolls, and faded murals depicting mythological scenes.");
        reading_room = new Room("reading room", "in the reading room. Where scholars studied scrolls and manuscripts, the main reading room is a large oval room with high ceilings and many tables and chairs made by local artisans – scrolls are left strewn haphazardly after the rest of the scholars fled, and the faint smell of smoke drifts in through the windows in the gallery above.");
        muse_garden = new Room("garden of muses", "in the Garden of Muses. This garden provides space for reflection and informal discussion. Statues of the nine Muses surround a central fountain, now cracked and dry.");
        dining_hall = new Room("dining hall", "in the dining hall. A hall deep within the library where the scholars ate meals. Long wooden tables are overturned, and the scent of charred bread lingers.");
        social_hall = new Room("social hall", "in the social hall. This is a gathering space for scholars to relax and debate informally. Cushions and low tables are scattered, and a harp lies broken in the corner.");
        kitchen = new Room("kitchen", "in the kitchen. This is a large vaulted room with tables, a fire pit and steel utensils which line the walls. This is where the cook resides, not having been told about the war raging outside the library walls.");
        courtyard = new Room("main courtyard", "in the main courtyard. This is an open-air courtyard with colonnades and a reflecting pool, now filled with ash and debris. The sky above glows orange from distant flames.");
        library = new Room("library", "in the library. It consists of a maze of shelves filled with books and codices of all sorts — people often get lost. The air is thick with dust and the scent of old parchment.");
        serapeum = new Room("serapeum", "in the serapeum. A grand temple annex in the library tower dedicated to the god Serapis, adorned with statues and offerings. The walls are inscribed with prayers and hymns, now partially obscured by soot.");
        eratosthenes_chamber = new Room("Eratosthene's chamber", "in Eratosthene's chamber. This is the library keepers chambers, the location of which was lost for years after his death. It is filled with his personal belongings, scrolls, and a large desk covered in star charts and maps.");
        sphinx_room = new Room("sphinx room", "in the sphinx's room. A vast, echoing chamber carved from sandstone, lit only by flickering oil lamps set into lion-headed sconces. At its center sits the Sphinx — not a statue, but a living guardian of riddles, its eyes glowing faintly with ancient wisdom. The air is thick with incense and silence, broken only when the Sphinx speaks. The walls are etched with faded riddles and failed answers — some scratched in desperation.");
        scroll_vault = new Room("scroll vault", "in the ancient scroll storage vault. Climate-controlled areas for preserving papyrus scrolls. Used for older, damaged and crumbling scrolls that are too fragile for just anyone to handle. The walls are lined with scrolls in compartmentalised shelves. You are now deep underground, presumably to keep the scrolls protected from heat and light. The acrid scent of disintegrating papyrus and old leather is almost overpowering.");
        hallway = new Room("hallways", "in the hallways. A long, narrow corridor lined with cracked marble columns and faded frescoes of scholars in debate. Every footstep echoes unnaturally, as if the hallway remembers every conversation ever held within it. Occasionally, the player hears whispers — fragments of ancient arguments or forgotten truths — but they vanish when pursued.");
        tower = new Room("tower", "in the tower. A spiraling stone staircase leads to what looks like a doorway, half hidden in shadows. The ceiling is painted with constellations, and a massive bronze astrolabe dominates the center. Dust motes drift in the sunlight filtering through cracked stained glass. From here, one can see the burning city beyond — and perhaps glimpse the stars that guided ancient scholars.");
    
        // create exits
        Exit inquiry_arch, whispering_door, marble_threshold, scholars_door, ink_stained_arch, echoing_hall, bronze_gateway, gilded_door, secret_door, winning_portal, heavy_wooden_door, half_door, courtyard_door, stone_arch, vine_covered_gate, wreathed_arch, pantry_door, garden_door, stairway_door, iron_door, vault_door, marble_entrance, reading_passage, oak_door, steel_door;
        
        // main hall exits
        inquiry_arch = new Exit(main_hall, lecture_hall, Direction.SOUTH, Direction.NORTH, "inquiry arch", true);
        whispering_door = new Door(main_hall, library, Direction.WEST, Direction.EAST, "whispering door", true, "master key", true, false);
        marble_threshold = new Exit(main_hall, social_hall, Direction.NORTH, Direction.SOUTH, "marble threshold", true);

        // lecture hall exits
        scholars_door = new Door(lecture_hall, library, Direction.WEST, Direction.EAST, "scholars' door", true, "master key",true, false);
        
        // library exits
        ink_stained_arch = new Exit(library, scribing_room, Direction.WEST, Direction.EAST, "ink-stained arch", true);
        echoing_hall = new Exit(library, reading_room, Direction.WEST, Direction.EAST, "echoing hall", true);
        bronze_gateway = new Door(library, tower, Direction.SOUTH, Direction.NORTH, "bronze gateway", true, "tower key", true, false);
        oak_door = new Door(library, hallway, Direction.NORTH, Direction.SOUTH, "oak door", true, "master key", true, false);

        // social hall exits
        heavy_wooden_door = new Door(social_hall, dining_hall, Direction.NORTH, Direction.SOUTH, "heavy wooden door", true, "master key", true, false);
        half_door = new Door(social_hall, kitchen, Direction.NORTH, Direction.SOUTH, "half door", true, "kitchen key", true, false);
        courtyard_door = new Door(social_hall, courtyard, Direction.WEST, Direction.EAST, "courtyard door", true, "master key", true, false);
        stone_arch = new Exit(social_hall, hallway, Direction.WEST, Direction.EAST, "stone arch", true);

        // kitchen exits
        pantry_door = new Door(kitchen, dining_hall, Direction.EAST, Direction.WEST, "pantry door", true, "kitchen key", true, false);
        garden_door = new Door(kitchen, courtyard, Direction.WEST, Direction.EAST, "garden door", true, "kitchen key", true, false);
        
        // tower exits
        gilded_door = new Door(tower, serapeum, Direction.SOUTH, Direction.NORTH, "gilded door", true, "master key", true, false);

        // serapeum exits
        secret_door = new Door(serapeum, eratosthenes_chamber, Direction.EAST, Direction.WEST, "secret door", false, "eratosthenes key", true, false);

        //courtyard/garden exits
        vine_covered_gate = new Door(courtyard, muse_garden, Direction.NORTH, Direction.SOUTH, "vine-covered gate", true, "garden key", true, false);
        winning_portal = new Exit(muse_garden, eratosthenes_chamber, Direction.NORTH, Direction.SOUTH, "winning portal", false);
        steel_door = new Door(courtyard, residential_quarter, Direction.WEST, Direction.EAST, "steel door", true, "master key", true, false);

        //hallway exits
        wreathed_arch = new Exit(hallway, courtyard, Direction.NORTH, Direction.SOUTH, "wreathed arch", true);
        iron_door = new Door(hallway, residential_quarter, Direction.NORTH, Direction.SOUTH, "iron door", true, "master key", true, false);
        marble_entrance = new Exit(hallway, scribing_room, Direction.SOUTH, Direction.NORTH, "marble entrance", true);
        
        //scribing room exits
        stairway_door = new Door(scribing_room, sphinx_room, Direction.NORTH, Direction.SOUTH, "stairway door", true, "master key", true, false);
        reading_passage = new Exit(scribing_room, reading_room, Direction.EAST, Direction.WEST, "reading passage", true);

        //sphinx room exits
        vault_door = new Door(sphinx_room, scroll_vault, Direction.NORTH, Direction.SOUTH, "vault door", true, "sphinx's key", true, false);

        //add exits to rooms
        main_hall.addExit(inquiry_arch); //to lecture hall
        main_hall.addExit(whispering_door); //to library
        main_hall.addExit(marble_threshold); //to social hall
        lecture_hall.addExit(inquiry_arch); //to main hall
        lecture_hall.addExit(scholars_door); //to library
        library.addExit(ink_stained_arch); //to scribing room
        library.addExit(echoing_hall); //to reading room
        library.addExit(bronze_gateway); //to tower
        library.addExit(whispering_door); //to main hall
        library.addExit(scholars_door); //to lecture hall
        library.addExit(oak_door); //to hallway
        social_hall.addExit(heavy_wooden_door); //to dining hall
        social_hall.addExit(half_door); //to kitchen
        social_hall.addExit(courtyard_door); //to courtyard
        social_hall.addExit(stone_arch); //to hallway
        social_hall.addExit(marble_threshold); //to main hall
        kitchen.addExit(pantry_door); //to dining hall
        kitchen.addExit(garden_door); //to courtyard
        kitchen.addExit(half_door); //to social hall
        dining_hall.addExit(heavy_wooden_door); //to social hall
        dining_hall.addExit(pantry_door); //to kitchen
        tower.addExit(gilded_door); //to serapeum
        tower.addExit(bronze_gateway); //to library
        serapeum.addExit(secret_door); //to eratosthenes chamber
        serapeum.addExit(gilded_door); //to tower
        eratosthenes_chamber.addExit(secret_door); //to serapeum
        eratosthenes_chamber.addExit(winning_portal); //to muse garden
        courtyard.addExit(vine_covered_gate); //to muse garden
        courtyard.addExit(courtyard_door); //to social hall
        courtyard.addExit(garden_door); //to kitchen
        courtyard.addExit(wreathed_arch); //to hallway
        courtyard.addExit(steel_door); //to residential quarter
        residential_quarter.addExit(steel_door); //to courtyard
        residential_quarter.addExit(iron_door); //to hallway
        muse_garden.addExit(winning_portal); //to eratosthenes chamber
        muse_garden.addExit(vine_covered_gate); //to courtyard  
        hallway.addExit(wreathed_arch); //to courtyard
        hallway.addExit(iron_door); //to residential quarter
        hallway.addExit(marble_entrance); //to scribing room
        hallway.addExit(oak_door); //to library
        hallway.addExit(stone_arch); //to hallway
        scribing_room.addExit(stairway_door); //to sphinx room
        scribing_room.addExit(reading_passage); // to reading room
        scribing_room.addExit(ink_stained_arch); //to library
        scribing_room.addExit(marble_entrance); //to hallway
        reading_room.addExit(echoing_hall); //to library
        reading_room.addExit(reading_passage); //to scribing room
        sphinx_room.addExit(vault_door); //to scroll vault
        sphinx_room.addExit(stairway_door); //to scribing room
        scroll_vault.addExit(vault_door); //to sphinx room
    	
    	//create items: lightsources
        Lightsource lamp1, lamp2, everlasting_flame;
    	lamp1 = new Lightsource("oil lamp", "an oil lamp, still filled with oil - but only enough to last 10 minutes.", 1, true, false, false);
        lamp2 = new Lightsource("bronze lamp", "an oil lamp, still filled with oil - but only enough to last 10 minutes.", 2, true, false, false);
        everlasting_flame = new Lightsource("everlasting flame", "a strange piece of wood that burns blue at the tip, it never goes out and cannot burn - the everlasting flame!", 5, false, true, true);
        lamp1.setLocation(main_hall);
        lamp2.setLocation(hallway);
        everlasting_flame.setLocation(reading_room);

        //scrolls
        Scroll rosetta_stone, scroll_of_eratosthenes, blank_scroll;
        rosetta_stone = new Scroll("rosetta stone", "the Rosetta Stone - a granodiorite stele inscribed with a decree issued in Memphis, Egypt in 196 BC.",3, true, "The Rosetta Stone is a granodiorite stele inscribed with a decree issued in Memphis, Egypt in 196 BC on behalf of King Ptolemy V. The decree appears in three scripts: the upper text is Ancient Egyptian hieroglyphs, the middle portion Demotic script, and the lowest Ancient Greek. Because it presents essentially the same text in all three scripts, it provided the key to the modern understanding of Egyptian hieroglyphs.");
        scroll_of_eratosthenes = new Scroll("scroll of Eratosthenes", "the scroll of Eratosthenes - a scroll containing the works of Eratosthenes, including his method for calculating the Earth's circumference.", 4, true, "Eratosthenes of Cyrene was a Greek mathematician, geographer, poet, astronomer, and musician");
        blank_scroll = new Scroll("blank scroll", "a blank scroll made of papyrus.", 9, true, "This is a blank scroll made of papyrus, ready to be written on.");
        rosetta_stone.setLocation(scroll_vault);
        scroll_of_eratosthenes.setLocation(eratosthenes_chamber);
        blank_scroll.setLocation(scroll_vault);

        //keys
        Key master_key, tower_key, kitchen_key, garden_key, sphinxs_key, eratosthenes_key;
        master_key = new Key("master key", "a large iron key that looks like it could open many doors; the master key.", 7, true);
        tower_key = new Key("tower key", "a small iron key with a tower engraved on the bow; the tower key.", 8, true);
        kitchen_key = new Key("kitchen key", "a small iron key with a cooking pot engraved on the bow; the kitchen key.", 9, true);
        garden_key = new Key("garden key", "a small iron key with a flower engraved on the bow; the garden key.", 10, true);
        sphinxs_key = new Key("sphinx's key", "a small golden key with a sphinx engraved on the bow; the sphinx's key.", 11, true);
        eratosthenes_key = new Key("eratosthenes' key", "a small golden key with a star engraved on the bow; Eratosthenes' key.", 12, true);
        master_key.setLocation(lecture_hall);
        master_key.addUnlockableExit(whispering_door);
        master_key.addUnlockableExit(scholars_door);
        master_key.addUnlockableExit(oak_door);
        master_key.addUnlockableExit(courtyard_door);
        master_key.addUnlockableExit(heavy_wooden_door);
        master_key.addUnlockableExit(gilded_door);
        master_key.addUnlockableExit(steel_door);
        master_key.addUnlockableExit(iron_door);
        master_key.addUnlockableExit(stairway_door);
        tower_key.setLocation(residential_quarter);
        tower_key.addUnlockableExit(bronze_gateway);
        kitchen_key.setLocation(social_hall);
        kitchen_key.addUnlockableExit(pantry_door);
        kitchen_key.addUnlockableExit(garden_door);
        kitchen_key.addUnlockableExit(half_door);
        garden_key.setLocation(courtyard);
        garden_key.addUnlockableExit(vine_covered_gate);
        sphinxs_key.setLocation(sphinx_room);
        sphinxs_key.addUnlockableExit(vault_door);
        eratosthenes_key.setLocation(serapeum);
        eratosthenes_key.addUnlockableExit(secret_door);
        
        //items
        Item fish, ink, shovel;
        fish = new Item("fish", "a small, charred fish - food for the library cats.", 7, true);
        ink = new Item("ink", "a small vial of black ink, still usable.", 8, true);
        shovel = new Item("shovel", "a sturdy shovel, useful for digging.", 10, true);
        fish.setLocation(kitchen);
        ink.setLocation(scribing_room);
        shovel.setLocation(kitchen);
        
        //spells
        /*Spell<E> light_spell, attack_spell, stun_spell, unlock_spell, teleport_spell, map_spell;
        light_spell = new Spell<>("light spell", "a spell that creates a small orb of light to illuminate dark areas.", 11, true, "This spell conjures a small orb of light that hovers around the caster for 2 minutes, illuminating dark areas.");
        attack_spell = new Spell<>("attack spell", "a spell that conjures a burst of energy to strike an enemy.", 12, true, "This spell conjures a burst of energy that can be directed at an enemy, causing damage upon impact and damaging health points.");
        stun_spell = new Spell<>("stun spell", "a spell that temporarily incapacitates an enemy.", 13, true, "This spell emits a wave of energy that temporarily stuns an enemy, rendering them immobile for a short duration.");
        unlock_spell = new Spell<>("unlock spell", "a spell that unlocks doors and chests.", 14, true, "This spell magically unlocks doors and chests, allowing access without the need for a physical key.");
        teleport_spell = new Spell<>("teleport spell", "a spell that teleports the caster to a known location.", 15, true, "This spell allows the caster to instantly teleport to a previously visited location.");
        map_spell = new Spell<>("map spell", "a spell that reveals a map of the surrounding area.", 16, true, "This spell conjures a magical map that reveals the layout of the surrounding area, including hidden paths and locations.");
        light_spell.setLocation(sphinx_room);
        attack_spell.setLocation(lecture_hall);
        stun_spell.setLocation(reading_room);
        unlock_spell.setLocation(residential_quarter);
        teleport_spell.setLocation(scroll_vault);
        map_spell.setLocation(library);*/

        //create npcs
        Assistants cook, apprentice, secret_keeper, cat;
        cook = new Assistants("The Cook", 50, kitchen, true, new ArrayList<>(), "cook");
        cook.addItem(fish);
        kitchen.addNPC(cook);

        apprentice = new Assistants("The Apprentice", 50, scribing_room, true, new ArrayList<>(), "apprentice");
        apprentice.addItem(ink);
        scribing_room.addNPC(apprentice);

        secret_keeper = new Assistants("The Secret Keeper", 50, hallway, true, new ArrayList<>(), "secret_keeper");
        secret_keeper.addItem(shovel);
        hallway.addNPC(secret_keeper);

        cat = new Assistants("The Library Cat", 25, residential_quarter, true, new ArrayList<>(), "cat");
        residential_quarter.addNPC(cat);

        Guardians sphinx, flamewatcher, librarian, hypatia;
        sphinx = new Guardians("The Sphinx", 150, sphinx_room, true, new ArrayList<>(), "sphinx", 20);
        sphinx.addItem(sphinxs_key);
        sphinx_room.addNPC(sphinx);

        flamewatcher = new Guardians("The Flamewatcher", 150, reading_room, true, new ArrayList<>(), "flamewatcher", 20);
        flamewatcher.addItem(everlasting_flame);
        reading_room.addNPC(flamewatcher);

        librarian = new Guardians("The Librarian", 150, library, true, new ArrayList<>(), "librarian", 20);
        library.addNPC(librarian);

        hypatia = new Guardians("Hypatia", 150, eratosthenes_chamber, true, new ArrayList<>(), "hypatia", 20);
        hypatia.addItem(eratosthenes_key);
        serapeum.addNPC(hypatia);

        //create the player character and start in starting room
        Parser parser = new Parser();
        String playerName = GameController.askPlayerName();
        player = new Player(playerName, main_hall, 100, 0);
    }

    //pass the player back to the gameframe
    public Player getPlayer() {
        return player;
    }

    public String printWelcome() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Welcome to the Ashes of Alexandria game!\n");
        sb.append("The library of Alexandria, Egypt, 48 BCE: on the eve of Julius Caesar's siege of Alexandria, you are trapped in the library as war rages outside.\n");
        sb.append("The library will soon burn, and so you must save the master scroll...\n");
        sb.append("What is that, you might ask?\n");
        sb.append("This scroll contains the origin story of the library itself and a prophecy about its fall and rebirth. You must retrieve it to ensure the knowledge can be passed on to future generations, before it falls into the hands of Julius Caesar and is destroyed!\n");
        sb.append("\n");
        sb.append("Click the 'help' button if you need help with gameplay.\n");
        sb.append("\n");
        sb.append(player.getCurrentRoom().getLongDescription()).append("\n");
        return sb.toString();
    }

    public String processCommand(Command command) {
        String commandWord = command.getCommandWord();
        StringBuilder out = new StringBuilder();        

        if (commandWord == null) {
            out.append("I don't understand your command...\n");
            return out.toString();
        }

        switch (commandWord) {
            case "go":
                out.append(goRoom(command));
                break;
            case "quit":
                if (command.hasSecondWord()) {
                    System.out.println("Quit what?");
                    return out.toString();
                } else {
                    return out.toString(); 
                }
            case "look":
            	out.append(seeItem(command));
            	break;
            case "take":
            	out.append(takeItem(command));
            	break;
            case "show":
            	showInventory();
            	break;
            case "drop":
            	out.append(dropItem(command));
            	break;
            case "save":
                try {
                    player.savePlayerState();
                    out.append("Game saved successfully.\n");
                } catch (Exception e) {
                    out.append("Error saving game: " + e.getMessage());
                }
                return out.toString();
            case "reload":
                try {
                    player = Player.reloadPlayerState(player.getName());
                    out.append("Game reloaded successfully.\n");
                    out.append(player.getCurrentRoom().getLongDescription()).append("\n");
                } catch (Exception e) {
                    out.append("Error reloading game: " + e.getMessage());
                }
                break;
            case "light":
                out.append(lightLamp(command));
                break;
            case "unlock":
                out.append(unlockDoor(command));
                break;
            case "read":
                out.append(readScroll(command));
                break;
            case "talk":
                out.append(talkToNPC(command.toString()));
                break;
            default:
                out.append("I don't know what you mean...\n");
                break;
        }
        return out.toString();
    }

    public String goRoom(Command command) {
        StringBuilder output = new StringBuilder();

        // handle: if no second word
        if (!command.hasSecondWord()) {
            output.append("Go where?\n");
            return output.toString();
        }

        String directionStr = command.getSecondWord().toUpperCase();
        Direction direction;

        // handle: invalid direction
        try {
            direction = Direction.valueOf(directionStr);
        } catch (Exception e) {
            output.append("That's not a valid direction!\n");
            return output.toString();
        }

        Room currentRoom = player.getCurrentRoom();
        List<Exit> matchingExits = new ArrayList<>();

        for (Exit exit : currentRoom.getExits()) {
            if (exit.getDirectionFrom(currentRoom) == direction) {
                matchingExits.add(exit);
            }
        }

        // handle: no exits in that direction
        if (matchingExits.isEmpty()) {
            output.append("You can't go that way!\n");
            return output.toString();
        }

        // handle: multiple exits in that direction
        if (matchingExits.size() > 1) {
            output.append("Multiple exits going ").append(directionStr.toLowerCase()).append(".\n");
            for (Exit exit : matchingExits) {
                output.append("- ").append(exit.getLabel()).append("\n");
            }
            output.append("[CHOOSE_EXIT]\n");
            // Let the controller decide how to resolve this
            return output.toString();
        }

        // One exit: just pass normally
        Exit chosenExit = matchingExits.get(0);
        if (chosenExit instanceof Door) {
            Door doorThru = (Door) chosenExit;
            if (!doorThru.canPass) {
                output.append("You attempt to go through the ").append(doorThru.getLabel()).append(", however it is locked.\n");
                if (doorThru.requiredKeyID != null) {
                    output.append("You need the ").append(doorThru.requiredKeyID).append(" to unlock this door...\n");
                }
                return output.toString();
            } else {
                output.append("You pass through the ").append(doorThru.getLabel()).append(".\n");
            }
        }
        Room nextRoom = chosenExit.getOtherSide(currentRoom);
        player.setCurrentRoom(nextRoom);
        output.append(player.getCurrentRoom().getLongDescription()).append("\n");
        return output.toString();
    }

    //method to move through a specific exit (used when multiple exits in same direction)
    public String moveThroughExit(Exit chosenExit) {
        StringBuilder output = new StringBuilder();
        Room currentRoom = player.getCurrentRoom();

        if (chosenExit instanceof Door doorThru && !doorThru.canPass) {
            output.append("You attempt to go through the ").append(doorThru.getLabel()).append(", however it is locked.\n");
        
            if (doorThru.requiredKeyID != null) {
                output.append("You need the ").append(doorThru.requiredKeyID).append(" to unlock this door...\n");
            }
            return output.toString();
        }

        Room nextRoom = chosenExit.getOtherSide(currentRoom);
        player.setCurrentRoom(nextRoom);
        output.append(player.getCurrentRoom().getLongDescription()).append("\n");
        return output.toString();

    }

        
    //item methods
    public String seeItem(Command command) {
        StringBuilder output = new StringBuilder();
        Room location = player.getCurrentRoom();
        
        List<Item> items = Item.getItems(location);
        if (items.isEmpty()) {
            output.append("You see nothing.\n");
        } else {
            output.append("You see:\n");
            for (Item item : items) {
                if (item.isVisible()) {
                    output.append("\t" + item.getDescription());
                }
            }
        }
        return output.toString();
    }
    
    public String takeItem(Command command) {
        StringBuilder output = new StringBuilder();

    	if (!command.hasSecondWord()) {
            output.append("Take what?\n");
            return output.toString();
        }
    	
    	String itemName = command.getSecondWord();
    	Room currentLoc = player.getCurrentRoom();
    	
    	List<Item> items = Item.getItems(currentLoc);
        Iterator<Item> itemsIterator = items.iterator();
        while (itemsIterator.hasNext()) {
            Item item = itemsIterator.next();
    		if (item.getName().contains(itemName) && item.isVisible()) {
    			player.addItem(item);
                itemsIterator.remove(); // remove from room's item list for future calls
    			item.setVisible(false);
    			output.append("You successfully took " + item.getName() + "!\n");

                if (item instanceof Lightsource) {
                    output.append("You can try to 'light' it if you want to use it.\n");
                }
                if (item instanceof Scroll) {
                    output.append("You can try to 'read' it to see its contents.\n");
                }
                if (item instanceof Key) {
                    output.append("You can try to 'unlock' doors with it.\n");
                    player.setScore(player.getScore() + 5); //reward player with points for getting a key
                }
    			return output.toString();
    		}
        }	
    	output.append("There is no " + itemName + " here.\n");

        return output.toString();
    }

    public String dropItem(Command command) {
        StringBuilder output = new StringBuilder();

        if (!command.hasSecondWord()) {
            output.append("Drop what?\n");
            return output.toString();
        }

        String itemName = command.getSecondWord();
        List<Item> inventory = player.getInventory();

        if (inventory.isEmpty()) {
            output.append("You have nothing to drop.\n");
            return output.toString();
        }

        Iterator<Item> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().contains(itemName)) {
                iterator.remove(); // Remove from inventory using iterator
                item.setVisible(true);
                item.setLocation(player.getCurrentRoom());
                output.append("You dropped " + item.getName() + ".\n");
                return output.toString();
            }
        }
        output.append("You don't have a " + itemName + " to drop.\n");
        return output.toString();
    }
    
    public void showInventory() {
    	List<Item> items = player.getInventory();
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("You are carrying:");
            for (Item item : items) {
                System.out.println("\t -" + item.getName());
            }
        }
    }

    public String lightLamp(Command command) {
        StringBuilder output = new StringBuilder();

        if (!command.hasSecondWord()) { 
            output.append("Light what?\n");
            return output.toString();
        }

        String lampName = command.getSecondWord();
        List<Item> inventory = player.getInventory();

        Iterator<Item> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().contains(lampName)) {
                if (item instanceof Lightsource) { //if the item is a lightsource
                    String results = (((Lightsource) item).turnOn()); //cast the item to lightsource to call the method
                    output.append(results);
                }   
            } else {
                output.append(lampName + " item can't be found or isn't a lightsource.\n");
            }
        }
        return output.toString();
    }

    public String unlockDoor(Command command) {
        StringBuilder output = new StringBuilder();

        if (!command.hasSecondWord()) { 
            output.append("Unlock what?\n");
            return output.toString();
        }

        String doorName = command.getSecondWord().toLowerCase();
        List<Door> doorList = Door.getDoorsList();
        List<Item> inventory = player.getInventory();

        boolean foundDoor = false;

        for (Door door : doorList) {
            // Normalize both sides to lowercase for comparison
            if (door.getLabel().toLowerCase().contains(doorName)) { 
                foundDoor = true;
                String keyNeeded = door.getRequiredKeyID();

                boolean hasKey = false;
                for (Item item : inventory) {
                    if (item instanceof Key && item.getName().equalsIgnoreCase(keyNeeded)) {
                        hasKey = true;
                        boolean success = door.unlock(item.getName());
                        if (success) {
                            output.append("You unlocked the " + door.getLabel() + " with the " + item.getName() + ".\n");
                        } else {
                            output.append("You don't have the correct key to unlock the " + door.getLabel() + ".\n");
                        } 
                    }
                }
                if (!hasKey) {
                    output.append("You don't have the required key to unlock the " + door.getLabel() + ".\n");
                }
                break; // exit the loop after finding the door
            }         
        }
        if (!foundDoor) {
            output.append(doorName + " can't be found.\n");
        }
        return output.toString();
    }

    public String readScroll(Command command) {
        StringBuilder output = new StringBuilder();

        if (!command.hasSecondWord()) { 
            output.append("Read what?\n");
            return output.toString();
        }

        String scrollName = command.getSecondWord();
        List<Item> inventory = player.getInventory();

        Iterator<Item> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().contains(scrollName)) {
                if (item instanceof Scroll) { //if the item is a scroll
                    String contents = ((Scroll) item).getContents(); //cast the item to scroll to call the method
                    output.append("You read the " + item.getName() + ":\n" + contents);
                    return output.toString(); 
                }
            } else {
                output.append(scrollName + " item isn't in your inventory or isn't a scroll. Try again.\n");
            }
        } 
        return output.toString();   
    }

    //NPC methods
    public NPC talkToNPC(String npcName) {
        StringBuilder output = new StringBuilder();

        npcName = npcName.toLowerCase();
        Room currentLoc = player.getCurrentRoom();

        //check the list of NPCs in the current room for both types    	
    	List<NPC> npcs = currentLoc.getNPCs();

        if (npcs == null || npcs.isEmpty()) {
            return null;
        }

        // search npcs for matching name
        for (NPC npc : npcs) {
            if (npc.getName().toLowerCase().contains(npcName)) {
            return npc; // return the NPC object for further interaction if needed
            }
        }

        // If not found
        return null;
    }

    //main method has been moved to GameFrame but I'll leave this here for reference
    /* 
    public static void main(String[] args) {
        AshesOfAlexandriaGame game = new AshesOfAlexandriaGame();
        game.play();
        
        if (player.getHealth() <= 0) {
            System.out.println("You have perished in the library. Game over.");
            return true;
        } else if (player.hasItem("scroll of Eratosthenes")) {
            System.out.println("Congratulations! You have secured the master scroll and escaped the burning library!");
            return true;
        } else {
            System.out.println("The library burns around you, but you failed to secure the master scroll. Game over.");
            return true;
        }
    }*/
}
