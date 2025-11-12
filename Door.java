public class Door extends Exit {
    private boolean isLocked;
    protected String requiredKeyID;
    protected boolean canPass;

    public Door(Room roomA, Room roomB, Direction directionFromA, Direction directionFromB, String label, boolean isVisible, String requiredKeyID, boolean isLocked, boolean canPass) {
        super(roomA, roomB, directionFromA, directionFromB, label, isVisible);
        this.requiredKeyID = requiredKeyID;
        this.isLocked = isLocked;
        this.canPass = !isLocked;
    }

    public void setRequiredKeyID(String requiredKeyID) {
        this.requiredKeyID = requiredKeyID;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
        if (isLocked) {
            this.canPass = false; // if locked, it cannot be passed
        }
    }

    public boolean unlock(String keyID) {
        if (isLocked && keyID.equals(requiredKeyID)) {
            isLocked = false;
            canPass = true;
            return true; // Successfully unlocked
        }
        return false; // Failed to unlock
    }

    public boolean lock(String keyID) {
        if (!isLocked && keyID.equals(requiredKeyID)) {
            isLocked = true;
            canPass = false;
            return true; // Successfully locked
        }
        return false; // Failed to lock
    }
}
