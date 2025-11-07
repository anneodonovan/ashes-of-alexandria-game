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

import java.util.Iterator;
import java.util.List;

public class AshesOfAlexandriaGame {
    private Parser parser;
    private Player player;

    public AshesOfAlexandriaGame() {
        createRooms();
        createItems();
        parser = new Parser();
    }

    private void createRooms() {
        Room main_hall, scribing_room, reading_room, lecture_hall, residential_quarter, muse_garden, dining_hall, social_hall, kitchen, courtyard, library, scroll_vault, serapeum, eratosthenes_chamber, sphinx_room, hallway, tower, staircase;

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
        library = new Room("main library", "in the main library. It consists of a maze of shelves filled with books and codices of all sorts — people often get lost. The air is thick with dust and the scent of old parchment.");
        serapeum = new Room("serapeum", "in the serapeum. A grand temple annex in the library tower dedicated to the god Serapis, adorned with statues and offerings. The walls are inscribed with prayers and hymns, now partially obscured by soot.");
        eratosthenes_chamber = new Room("Eratosthene's chamber", "in Eratosthene's chamber. This is the library keepers chambers, the location of which was lost for years after his death. It is filled with his personal belongings, scrolls, and a large desk covered in star charts and maps.");
        sphinx_room = new Room("sphinx room", "in the sphinx's room. A vast, echoing chamber carved from sandstone, lit only by flickering oil lamps set into lion-headed sconces. At its center sits the Sphinx — not a statue, but a living guardian of riddles, its eyes glowing faintly with ancient wisdom. The air is thick with incense and silence, broken only when the Sphinx speaks. The walls are etched with faded riddles and failed answers — some scratched in desperation.");
        scroll_vault = new Room("scroll vault", "in the ancient scroll storage vault. Climate-controlled areas for preserving papyrus scrolls. Used for older, damaged and crumbling scrolls that are too fragile for just anyone to handle. The walls are lined with scrolls in compartmentalised shelves. You are now deep underground, presumably to keep the scrolls protected from heat and light. The acrid scent of disintegrating papyrus and old leather is almost overpowering.");
        hallway = new Room("hallways", "in the hallways. A long, narrow corridor lined with cracked marble columns and faded frescoes of scholars in debate. Every footstep echoes unnaturally, as if the hallway remembers every conversation ever held within it. Occasionally, the player hears whispers — fragments of ancient arguments or forgotten truths — but they vanish when pursued.");
        tower = new Room("tower", "in the tower. A spiraling stone staircase leads to what looks like a doorway, half hidden in shadows. The ceiling is painted with constellations, and a massive bronze astrolabe dominates the center. Dust motes drift in the sunlight filtering through cracked stained glass. From here, one can see the burning city beyond — and perhaps glimpse the stars that guided ancient scholars.");
        staircase = new Room("staircase", "on the staircase. It's dark and damp - nobody has been here in a long, long time. A faint sent of burnt wood and dust hangs in the air, and darkness shrouds the steps as they lead down into nothingness.");

        // initialise room exits
        main_hall.setExit("south", lecture_hall);
        main_hall.setExit("west", library);
        main_hall.setExit("north", social_hall);
        
        lecture_hall.setExit("north", main_hall);
        lecture_hall.setExit("west", library);

        social_hall.setExit("south", main_hall);
        social_hall.setExit("north", dining_hall);
        social_hall.setExit("north", kitchen); //have to fix the direction/exit classes to make this work
        social_hall.setExit("west", courtyard);
        social_hall.setExit("west", hallway);

        kitchen.setExit("west", muse_garden);
        kitchen.setExit("south", social_hall);
        kitchen.setExit("east", dining_hall);

        dining_hall.setExit("south", social_hall);
        dining_hall.setExit("west", kitchen);

        courtyard.setExit("east", social_hall);
        courtyard.setExit("east", kitchen);
        courtyard.setExit("west", residential_quarter);
        courtyard.setExit("north", muse_garden);
        courtyard.setExit("south", hallway);
        
        muse_garden.setExit("south", courtyard);
        
        hallway.setExit("north", courtyard);
        hallway.setExit("south", library);
        hallway.setExit("east", social_hall);
        hallway.setExit("west", scribing_room);
        hallway.setExit("west", residential_quarter);

        residential_quarter.setExit("south", hallway);
        residential_quarter.setExit("east", courtyard);

        scribing_room.setExit("north", hallway);
        scribing_room.setExit("south", reading_room);
        scribing_room.setExit("east", library);
        scribing_room.setExit("west", staircase);

        staircase.setExit("east", scribing_room);
        staircase.setExit("west", sphinx_room);

        sphinx_room.setExit("east", staircase);
        sphinx_room.setExit("west", scroll_vault);

        reading_room.setExit("north", hallway);
        reading_room.setExit("east", library);

        library.setExit("north", hallway);
        library.setExit("east", main_hall);
        library.setExit("west", scribing_room);
        library.setExit("east", reading_room);
        library.setExit("south", tower);

        tower.setExit("up", serapeum);
        tower.setExit("down", library);

        serapeum.setExit("north", tower);
        serapeum.setExit("east", eratosthenes_chamber);

        eratosthenes_chamber.setExit("west", serapeum);

        // create the player character and start in starting room
        player = new Player("player", main_hall);
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the Ashes of Alexandria game!");
        System.out.println("The library of Alexandria, Egypt, 48 BCE: on the eve of Julius Caesar's siege of Alexandria, you are trapped in the library as war rages outside.");
        System.out.println("The library will soon burn, and so you must save the master scroll...");
        System.out.println("What is that, you might ask?");
        System.err.println("This scroll contains the origin story of the library itself and a prophecy about its fall and rebirth. You must retrieve it to ensure the knowledge can be passed on to future generations, before it falls into the hands of Julius Caesar and is destroyed!");
        System.out.println();
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private boolean processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            System.out.println("I don't understand your command...");
            return false;
        }

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "quit":
                if (command.hasSecondWord()) {
                    System.out.println("Quit what?");
                    return false;
                } else {
                    return true; // signal to quit
                }
            case "look":
            	seeItem(command);
            	break;
            case "take":
            	takeItem(command);
            	break;
            case "show":
            	showInventory();
            	break;
            case "drop":
            	dropItem(command);
            	break;
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return false;
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander around the university.");
        System.out.print("Your command words are: ");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }
    
    public void createItems() {
    	Item chair;
    	
    	//create items
    	chair = new Item("chair", "a wooden chair");
    	
    	//set object locations
    	chair.setLocation("classroom");
    	
    	//set visibility
    	chair.setVisible(true);
    }
    
    private void seeItem(Command command) {
    	String location = player.getCurrentRoom().getName();
    	
    	List<Item> items = Item.getItems(location);
    	if (items.isEmpty()) {
    		System.out.println("You see nothing.");
    	} else {
    		System.out.println("You see:");
    		for (Item item : items) {
    			System.out.println("\t" + item.getDescription());
    		}
    	}
    }
    
    private void takeItem(Command command) {
    	if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
    	
    	String itemName = command.getSecondWord();
    	String currentLoc = player.getCurrentRoom().getName();
    	
    	List<Item> items = Item.getItems(currentLoc);
    	for (Item item : items) {
    		if (item.getName().equalsIgnoreCase(itemName) && item.isVisible()) {
    			player.addItem(item);
    			item.setVisible(false);
    			System.out.println("You successfully took " + item.getName());
    			return;
    		}
    	}
    	System.out.println("There is no " + itemName + " here.");

    }

    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        List<Item> inventory = player.getInventory();

        if (inventory.isEmpty()) {
            System.out.println("You have nothing to drop.");
            return;
        }

        Iterator<Item> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(itemName)) {
                iterator.remove(); // Remove from inventory using iterator
                item.setVisible(true);
                item.setLocation(player.getCurrentRoom().getName());
                System.out.println("You dropped " + item.getName());
                return;
            }
        }
        System.out.println("You don't have a " + itemName + " to drop.");
    }
    
    private void showInventory() {
    	List<Item> items = player.getInventory();
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("You are carrying:");
            for (Item item : items) {
                System.out.println("\t" + item.getDescription());
            }
        }
    }

    public static void main(String[] args) {
        AshesOfAlexandriaGame game = new AshesOfAlexandriaGame();
        game.play();
    }
}
