# Ashes of Alexandria
Ashes of Alexandria is a Java text adventure game with a simple JavaFX GUI based on the Zork Game. You explore the Library of Alexandria, collect items, talk to NPCs, and try to retrieve the master scroll before the library burns.

## How to Compile
Requirements:
- Java 21 (or the version specified for the module)
- JavaFX SDK installed on your machine

From the project root:
./build.sh

This script compiles the source code into the `bin/` directory using the JavaFX SDK path configured inside the script.

If `./build.sh` fails with a JavaFX path error, open `build.sh` and update the `--module-path` to the location of your JavaFX SDK on your machine.

## How to Run
After a successful build, you can run the game in two main ways.

### Run in development mode
./run.sh
This runs the compiled classes in `bin/` with the JavaFX module path already set in the script.

### Run the packaged JAR (if you built it)
First, create the JAR:
./package.sh

Then run it:
./run-jar.sh

If you prefer to call `java` directly, use:
java --module-path /path/to/javafx-sdk/lib
--add-modules javafx.controls,javafx.fxml
-jar dist/Alexandria.jar

Replace `/path/to/javafx-sdk/lib` with your actual JavaFX SDK path if needed.

## How to Use the Game
When you start the game:
- A window opens with:
  - A text area showing room descriptions and messages.
  - A text field where you can type commands.
  - Buttons on the side for save, reload, help, and quit.
  - A panel showing score, timer, health bar, and inventory list.

You control the game by typing commands into the input field and pressing Enter. Some core commands:

### Movement
- `go north`
- `go south`
- `go east`
- `go west`

If there are multiple exits in that direction, the GUI will prompt you to choose which one to take.

### Items and Inventory
- `take <item>` – pick up an item in the current room  
- `drop <item>` – drop an item from your inventory  
- `show` – list items you are carrying  
- `look` – see items and description of the current room  

### Interaction
- `read <scroll>` – read a scroll you are carrying  
- `light <lamp>` – light a lightsource  
- `unlock <door>` – unlock a door with a key you have  
- `talk to <npc>` – start a conversation with an NPC; dialogue options appear as buttons in the GUI  

### Game Management
- `save` – save your current game to a file with your player name  
- `reload` – load your saved game  
- `help` – show a short help message about the commands  
- `quit` – close the game window

## Notes
- Source code is under `src/com/alexandria/` organised using a Model–View–Controller structure:
  - `model/` – game logic (rooms, items, player, NPCs, commands)
  - `view/` – JavaFX GUI panels and main frame
  - `controller/` – `GameController` connecting GUI with the model
- Save files are written to the project root, named after the player (e.g. `PlayerName.txt`).