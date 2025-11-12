public class Exit {
    private Room roomA;
    private Room roomB;
    private Direction directionFromA;
    private Direction directionFromB;
    private String label;
    private boolean isVisible;

    public Exit(Room roomA, Room roomB, Direction directionFromA, Direction directionFromB, String label, boolean isVisible) {
        this.roomA = roomA;
        this.roomB = roomB;
        this.directionFromA = directionFromA;
        this.directionFromB = directionFromB;
        this.label = label;
        this.isVisible = isVisible;
    }

    public void setRoomA(Room room) {
        this.roomA = room;
    }

    public void setRoomB(Room room) {
        this.roomB = room;
    }

    public void setDirectionFromA(Direction direction) {
        this.directionFromA = direction;
    }

    public void setDirectionFromB(Direction direction) {
        this.directionFromB = direction;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Room getOtherSide(Room current) {
        if (current.equals(roomA)) return roomB;
        if (current.equals(roomB)) return roomA;
        throw new IllegalArgumentException("Room not part of this exit");   //throw error if room not found
    }

    public Direction getDirectionFrom(Room room) { //problem
        if (room == null) {
            throw new IllegalArgumentException("Room is null");
        }

        if (room == roomA) {
            return directionFromA;
        } else if (room == roomB) {
            return directionFromB;
        } else {
            throw new IllegalArgumentException("Room " + room.getName() + " not part of this exit"); //throw error if room not found
        }
    }
    
    public Direction getDirectionFromA() {
        return directionFromA;
    }

    public Direction getDirectionFromB() {
        return directionFromB;
    }

    public String getLabel() {
        return label;
    }

    public boolean getIsVisible() {
        return isVisible;
    }
}
