# 2-Minute Demo Video Outline: Ashes of Alexandria

## Target: Show off key Java concepts + engaging gameplay

---

## **[0:00-0:15] INTRO & SETUP (15 seconds)**

**On Screen:**
- Show VS Code with project open
- Briefly show folder structure: `src/com/alexandria/model`, `view`, `controller`

**What You Say:**
> "Hi, I'm presenting Ashes of Alexandria, a Java text adventure game built with JavaFX. This project demonstrates key Java concepts including OOP, the MVC pattern, Collections, Generics, serialization, and GUI development. Let me walk you through the code and gameplay."

**Action:**
- Click through folders quickly to show organization
- Highlight: `model/`, `view/`, `controller/` to show MVC structure

---

## **[0:15-0:35] CODE HIGHLIGHTS (20 seconds)**

**Show 3 Key Code Snippets (5-6 seconds each):**

### 1. **Inheritance & Polymorphism** (5 sec)
**File:** `src/com/alexandria/model/Inventory/Item.java` and subclasses

**What You Say:**
> "First, here's our Item class. We use inheritance to create specialized items like Keys, Scrolls, and Lightsources. This demonstrates OOP principles."

**Show:**
- Quick show of `Item.java` base class (highlight `getName()`, `getDescription()`)
- Flash to `Key.java` extending Item
- Flash to `Spell.java` with Generics: `Spell<E extends SpellEffect>`

**Code snippet to show:**
```java
public class Item implements Serializable {
    protected String name;
    protected String description;
    // ... getters/setters
}

public class Key extends Item { ... }
public class Spell<E extends Spell.SpellEffect> extends Item { ... }
```

---

### 2. **Collections & Enums** (6 sec)
**File:** `src/com/alexandria/model/Traversal/Room.java`

**What You Say:**
> "Rooms store exits and NPCs using Collections—ArrayList and HashSet. We also use an Enum for Directions to ensure type safety."

**Show:**
- Highlight Room class fields:
```java
private ArrayList<Exit> exits;
private ArrayList<NPC> npcs;
private static final List<Room> allRooms = new ArrayList<>();

public String getExitString() {
    Set<Direction> added = new HashSet<>();
    // ... builds exit string from unique directions
}
```
- Flash to `Direction.java` showing NORTH, SOUTH, EAST, WEST

---

### 3. **MVC Pattern & Controller** (5 sec)
**File:** `src/com/alexandria/controller/GameController.java`

**What You Say:**
> "Here's our controller that bridges the Model and View. When a player enters a command, the controller parses it, calls the game logic, and updates the UI."

**Show:**
```java
public void handleCommand(Command command) {
    String result = gameModel.processCommand(command);
    centerPanel.getOutputArea().appendText("> " + command + "\n" + result + "\n");
    updateUI();
}
```
- Highlight connection: `gameModel` (Model) → `centerPanel` (View)

---

## **[0:35-1:25] LIVE GAMEPLAY DEMO (50 seconds)**

**Launch the game:** `./run.sh`

**Action Sequence (follow this order):**

### **Part A: Exploration & Room Navigation (15 sec)**
- Start in Main Hall
- Type: `look` (show items in the room)
- Type: `go north` (move to Lecture Hall)
- Briefly describe: "Navigating between rooms shows how our Exit and Direction classes work together"

### **Part B: Item Management & Inventory (15 sec)**
- Type: `look` (see items)
- Type: `take master key` (show inventory update in right panel)
- Type: `show` (display inventory in output)
- **Highlight**: "Notice the Score in the right panel updates—that's the inventory count. This uses Java serialization for persistence."

### **Part C: NPC Interaction & Dialogue (15 sec)**
- Navigate to a room with an NPC (e.g., Kitchen to talk to Cook)
- Type: `talk to cook` (show dialogue tree)
- Select a dialogue option
- **Highlight**: "NPC dialogues are loaded from JSON files using Gson and managed by our DialogueManager. This shows file I/O and exception handling."

### **Part D: Puzzle Solving (5 sec)**
- Navigate to a locked door
- Type: `unlock <door name>` with master key
- **Highlight**: "Locked doors demonstrate polymorphism—Door extends Exit and adds lock/unlock logic."

---

## **[1:25-1:50] KEY FEATURES RECAP (25 seconds)**

**Return to Code View** (use Alt+Tab or split screen)

**Show these key files briefly:**

1. **Serialization (Save/Load)** — 5 sec
   - File: `src/com/alexandria/model/Player/Player.java`
   ```java
   public void savePlayerState() throws IOException {
       FileOutputStream fos = new FileOutputStream(name + ".txt");
       ObjectOutputStream oos = new ObjectOutputStream(fos);
       oos.writeObject(this);
       oos.close();
   }
   ```
   - Explain: "Player state (inventory, health, location) is serialized to disk"

2. **Command Parser** — 5 sec
   - File: `src/com/alexandria/model/Commands/Parser.java`
   ```java
   public Command parse(String inputLine) {
       String word1, word2, word3;
       Scanner tokenizer = new Scanner(inputLine);
       // tokenize and create Command object
   }
   ```
   - Explain: "Input parsing handles multi-word commands like 'go north' or 'talk to sphinx'"

3. **Exception Handling** — 5 sec
   - Show any try-catch block in GameController or Player class
   - Explain: "We gracefully handle file I/O errors and invalid inputs"

4. **Executable JAR** — 5 sec
   - Show: `dist/Alexandria.jar` in file explorer
   - Explain: "Packaged as an executable JAR with all dependencies embedded. No separate classpath needed beyond JavaFX."

---

## **[1:50-2:00] CONCLUSION (10 seconds)**

**Final Screen: Show GitHub/Project Stats (or just speak):**

**What You Say:**
> "This project demonstrates core Java concepts: OOP with inheritance and polymorphism, Collections and Generics for managing game objects, serialization for persistent game state, exception handling for robustness, and an MVC architecture separating business logic from the UI. The game is playable, fully documented, and packaged as an executable JAR. Thanks for watching!"

**Action:**
- Optionally show: File count, lines of code, package structure one more time
- Or simply end with a polished screenshot of the game

---

## **VIDEO PRODUCTION TIPS**

### **What to Show on Screen (Sequence):**
1. Project structure in VS Code (5 sec)
2. Key code files (20 sec) — use syntax highlighting
3. Live gameplay (50 sec) — smooth, clear clicks
4. Code recap (25 sec) — back to VS Code
5. Conclusion (10 sec)

### **Recording Settings:**
- **Screen Resolution**: 1920x1080 or 1280x720
- **Font Size in VS Code**: Increase to 16-18pt for readability
- **Game Window**: Maximize for clarity
- **Microphone**: Speak clearly, moderate pace, no background noise

### **Tools to Use:**
- **Windows**: OBS Studio (free) or Windows 10 Game Bar (Win + G)
- **Mac**: QuickTime Player (Cmd + Space → QuickTime)
- **Linux**: OBS Studio or SimpleScreenRecorder

### **Editing (Optional but Recommended):**
- Add intro/outro slides with title and project name
- Zoom in on important code lines
- Add text overlays: "MVC Pattern", "Inheritance", "Collections"
- Add background music (low volume, royalty-free)
- Trim pauses and dead time

### **Script Tips:**
- **Speak slowly** (you have 2 min, no rush)
- **Explain "why" not just "what"** — Why did we use inheritance? Why serialize?
- **Point with cursor** to highlight code while explaining
- **Natural transitions** — "Now let's see this in action..." (click to game)

---

## **TIMING BREAKDOWN**

| Section | Time | Duration |
|---------|------|----------|
| Intro & Setup | 0:00-0:15 | 15 sec |
| Code Highlights (3 snippets) | 0:15-0:35 | 20 sec |
| Live Gameplay | 0:35-1:25 | 50 sec |
| Code Recap (4 features) | 1:25-1:50 | 25 sec |
| Conclusion | 1:50-2:00 | 10 sec |
| **TOTAL** | | **2:00** |

---

## **ALTERNATE SHORT VERSION (if you go over)**

If you run long, cut in this order:
1. Remove one NPC dialogue interaction (keep just 2 gameplay parts)
2. Combine "Part C & D" into one demo (both show game mechanics)
3. Keep intro, code, gameplay, conclusion — drop the recap

---

## **SCRIPT TEMPLATE TO READ**

```
[0:00] "Hi, I'm presenting Ashes of Alexandria, a Java text adventure game 
demonstrating key OOP, Collections, Generics, serialization, and MVC patterns."

[0:15] "Looking at the code structure, we have a clear MVC architecture. 
The model contains game logic, the view is our JavaFX UI, and the controller 
bridges them. Let me show you some key implementations..."

[0:35] "Here's item inheritance—Items are a base class for Keys, Scrolls, 
and Spells. Spells even use Generics with bounded type parameters."

[1:25] "We use Collections—ArrayList for rooms and exits, HashSet for unique 
directions. And we have an Enum for type-safe directions."

[1:50] "The controller updates the model and refreshes the view. When you 
save, player state is serialized to disk. Commands are parsed with a tokenizer. 
And we handle errors gracefully throughout.

[2:00] Thanks for watching!"
```

---

## **FINAL CHECKLIST BEFORE RECORDING**

- [ ] Project compiles without errors (`./build.sh`)
- [ ] Game runs smoothly (`./run.sh`)
- [ ] Screen is clean (close extra tabs/windows)
- [ ] VS Code font is large enough (14pt+)
- [ ] Microphone is working and clear
- [ ] You have a backup recording location
- [ ] You know which NPC and rooms you'll demo
- [ ] You've practiced the gameplay (don't fumble commands)
- [ ] You have 2-3 test runs before final recording

---

**Good luck with your demo! This outline should take ~60-90 min to film and edit.** 🎬
