import java.util.HashMap;
import java.util.Map;

public class Room {
	private String name; //added this to make it easier to access seperate room objects
    private String description;
    private Map<String, Room> exits; // Map direction to neighboring Room

    public Room(String name, String description) {
        this.name = name;
    	this.description = description;
        exits = new HashMap<>();
    }

    public String getName() {
    	return name;
    }
    
    public String getDescription() {
        return description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        for (String direction : exits.keySet()) {
            sb.append(direction).append(" ");
        }
        return sb.toString().trim();
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitString();
    }
}
