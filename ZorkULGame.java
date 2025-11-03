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

public class ZorkULGame {
    private Parser parser;
    private Player player;

    public ZorkULGame() {
        createRooms();
        createItems();
        parser = new Parser();
    }

    private void createRooms() {
        Room outside, classroom;

        // create rooms
        outside = new Room("outside", "outside the main entrance of the university");
        classroom = new Room("classroom", "a regular classroom"); //example room

        // initialise room exits
        outside.setExit("east", classroom); 
        classroom.setExit("west", outside); 

        // create the player character and start outside
        player = new Player("player", outside);
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
        System.out.println("Welcome to the University adventure!");
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
    	Item chair, desk, couch, muffin;
    	
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
        ZorkULGame game = new ZorkULGame();
        game.play();
    }
}
