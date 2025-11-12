import java.util.ArrayList;

public class Room {
	private String name; //added this to make it easier to access seperate room objects
    private String description;
    private ArrayList<Exit> exits; // array list of exit objects for each room

    public Room(String name, String description) {
        this.name = name;
    	this.description = description;
        exits = new ArrayList<>();
    }

    public String getName() {
    	return name;
    }
    
    public String getDescription() {
        return description;
    }

    public void addExit(Exit exit) {
        exits.add(exit); //adds new exit to the room's exits arraylist
    } 

    public ArrayList<Exit> getExits() {
        return exits;
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        for (Exit exit : exits) { //iterate through list of exits and get direction names to display to player
            Direction direction = exit.getDirectionFrom(this); //get direction based on current room
            if (direction != null) {    
                sb.append(direction.name().toLowerCase()).append(" "); //make sure the exits described are based on the current room
                }
            }
        return sb.toString().trim();
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitString();
    }

}
