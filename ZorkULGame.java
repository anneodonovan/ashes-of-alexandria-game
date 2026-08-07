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

import java.util.List;
import java.util.Scanner;

public class ZorkULGame {
    private Parser parser;
    private Character player;

    public ZorkULGame() {
        createRooms();
        createItems();
        parser = new Parser();
    }

    private void createRooms() {
        Room outside, theatre, pub, lab, office, hallway, cafe;

        // create rooms
        outside = new Room("outside", "outside the main entrance of the university");
        theatre = new Room("theatre", "in a lecture theatre");
        pub = new Room("pub", "in the campus pub");
        lab = new Room("lab", "in a computing lab");
        office = new Room("office", "in the computing admin office");
        hallway = new Room("hallway", "in an empty hallway"); //add new room
        cafe = new Room("cafe", "in the cafe"); //add new room

        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        office.setExit("east", hallway); //add direction to new room
        
        hallway.setExit("west", office); //set an exit for new room
        hallway.setExit("east", cafe); //add direction to new room
        
        cafe.setExit("west", hallway); //set an exit for new room
        cafe.setExit("north", outside); //set an exit for new room

        // create the player character and start outside
        player = new Character("player", outside);
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
            case "eat":
            	eatItem(command);
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
    	chair = new Item("chair", "a soft chair");
    	desk = new Item("desk", "a wooden desk");
    	couch = new Item("couch", "a red couch");
    	muffin = new Item("muffin", "a chocolate chip muffin");
    	
    	//set object locations
    	chair.setLocation("office");
    	desk.setLocation("office");
    	couch.setLocation("pub");
    	muffin.setLocation("cafe");
    	
    	//set visibility
    	chair.setVisible(true);
    	desk.setVisible(true);
    	couch.setVisible(true);
    	muffin.setVisible(true);
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
    
    private void eatItem(Command command) {
    	if (!command.hasSecondWord()) {
            System.out.println("Eat what?");
            return;
        }

    	String itemName = command.getSecondWord();
    	for (Item item : player.getInventory()) {
    		if (item.getName().equalsIgnoreCase(itemName)) {
    			player.removeItem(item);
    			System.out.println("You ate the " + item.getName() + ". Delicious!");
    			return;
    		}
    	}
    	System.out.println("You don't have a " + itemName + " to eat.");
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
