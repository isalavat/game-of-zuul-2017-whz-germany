package de.fh_zwickau.oose.zuul.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  This class is the main class of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.  Users
 *  can walk around some scenery. That's all. It should really be extended
 *  to make it more interesting!
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.1 (December 2002)
 */

public class Game implements Serializable
{
    private transient Parser parser;
    private Player player;
    private List<Room> rooms;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        player = new Player(300, 200, ("gnom.png"), 1.5);

        parser = new Parser();
        createRooms();
        createItems();
        createFurnitures();
    }

    /**
     * Create all the rooms and link their exits together.
     * Also created npc and its riddles, which are assigned
     * to the room
     */
    private void createRooms()
    {
    	String npcDialogText = "Do you want a key? Then solve my riddle!(Q)";
    	Map<String, Boolean> answers = new LinkedHashMap<>();
    	answers.put("Sequoia", false);
    	answers.put("Moon", false);
    	answers.put("Sun", false);
    	answers.put("Mountain", true);

    	// create the riddles and npc
    	Riddle r1 = new Riddle("What has roots as nobody sees," + '\n' +
    							"Is taller than trees, " + '\n' +
    							"Up, up it goes, " + '\n' +
    							"And yet never grows?", answers);
    	NPC npc1 = new NPC(200, 150, ("girl2.png"),
    			new Key("key", "key of room 1 (east exit)", "key.png", "Room-1", "east"), r1, npcDialogText);

    	answers.clear();
    	answers.put("Teeth", true);
    	answers.put("Tides", false);
    	answers.put("Polar bears", false);
    	answers.put("Five armies", false);
    	Riddle r2 = new Riddle("Thirty white horses on a red hill," + '\n' +
    							"First they champ," + '\n' +
    							"Then they stamp," + '\n' +
    							"Then they stand still.", answers);
    	NPC npc2 = new NPC(200, 150, ("gandalf.png"),
    			new Key("key", "key of room 2 (east exit)", "key.png", "Room-2", "east"), r2, npcDialogText);

    	answers.clear();
    	answers.put("Worm", false);
    	answers.put("Fire", false);
    	answers.put("Wind", true);
    	answers.put("Storm", false);
    	Riddle r3 = new Riddle("Voiceless it cries," + '\n' +
    							"Wingless flutters," + '\n' +
    							"Toothless bites," + '\n' +
    							"Mouthless mutters.", answers);
    	NPC npc3 = new NPC(200, 150, "org.png",
    			new Key("key", "key of room 3 (north exit)", "key.png", "Room-3", "north"), r3, npcDialogText);

    	answers.clear();
    	answers.put("A pot over a fire", false);
    	answers.put("Sun shining on daisies", true);
    	answers.put("Two peas in pod", false);
    	answers.put("A ring", false);
    	Riddle r4 = new Riddle("An eye in a blue face " + '\n' +
    							"Saw an eye in a green face. " + '\n' +
    							"'That eye is like to this eye' " + '\n' +
    							"Said the first eye, " + '\n' +
    							"'But in low place " + '\n' +
    							"Not in high place.'", answers);
    	NPC npc4 = new NPC(200, 150, "org.png",
    			new Key("key", "key of room 4 (west exit)", "key.png","Room-4", "west"), r4, npcDialogText);

    	answers.clear();
    	answers.put("Mountains", false);
    	answers.put("Dark", true);
    	answers.put("Time", false);
    	answers.put("The ring", false);
    	Riddle r5 = new Riddle("It cannot be seen, cannot be felt, " + '\n' +
    							"Cannot be heard, cannot be smelt. " + '\n' +
    							"It lies behind stars and under hills, " + '\n' +
    							"And empty holes it fills. " + '\n' +
    							"It comes first and follows after, " + '\n' +
    							"Ends life, kills laughter.", answers);
    	NPC npc5 = new NPC(200, 150, "org.png",
    			new Key("key", "key of room 5  (west exit)", "key.png","Room-5", "west"), r5, npcDialogText);

    	answers.clear();
    	answers.put("Chest", false);
    	answers.put("Throne", false);
    	answers.put("Egg", true);
    	answers.put("Ball", false);
    	Riddle r6 = new Riddle("A box without hinges, key, or lid," + '\n' +
    							"Yet golden treasure inside is hid.", answers);
    	NPC npc6 = new NPC(200, 150, "org.png",
    			new Key("key", "key of room 6  (north exit)", "key.png", "Room-6", "north"),r6, npcDialogText);

    	answers.clear();
    	answers.put("Dragons", false);
    	answers.put("Wind", false);
    	answers.put("Fire", false);
    	answers.put("Fish", true);
    	Riddle r7 = new Riddle("Alive without breath, " + '\n' +
    							"As cold as death; " + '\n' +
    							"Never thirsty, ever drinking, " + '\n' +
    							"All in mail never clinking.", answers);

    	NPC npc7 = new NPC(200, 150, "org.png",
    			new Key("key", "key of room 7 (east exit)", "key.png", "Room-7", "east"), r7,  npcDialogText);

    	answers.clear();
    	answers.put("Fire", false);
    	answers.put("Time", true);
    	answers.put("Dragons", false);
    	answers.put("Ice", false);
    	Riddle r8 = new Riddle("This thing all things devours: " + '\n' +
    							"Birds, beasts, trees, flowers; " + '\n' +
    							"Gnaws iron, bites steel; " + '\n' +
    							"Grinds hard stones to meal; " + '\n' +
    							"Slays king, ruins town, " + '\n' +
    							"And beats high mountain down.", answers);
    	NPC npc8 = new NPC(200, 150, "org.png",
    			new Key("key", "key of room 8  (east exit)", "key.png", "Room-8", "east"),r8, npcDialogText);

    	answers.clear();
    	answers.put("Air", false);
    	answers.put("Baloon", false);
    	answers.put("Helium", false);
    	answers.put("Hole", true);
    	Riddle r9 = new Riddle("What can you put in a barrel that will:" + '\n' +
    							"Be seen by the naked eye" + '\n' +
    							"Make the barrel lighter in weight" + '\n' +
    							"And has no weight of it's own", answers);
    	NPC npc9 = new NPC(200, 150, "org.png",
    			new Key("key", "key of room 9", "key.png", "Room-9", "east"), r9, npcDialogText);

    	Room room1, room2, room3, room4, room5, room6, room7, room8, room9;
        double points[] = {46, 350, 46, 85, 516, 85, 516, 350};

        // create the rooms
        room1 = new Room("Room-1", "room1.png", points, npc1);
        room2 = new Room("Room-2", "room2.png", points, npc2);
        room3 = new Room("Room-3", "room3.png", points, npc3);
        room4 = new Room("Room-4", "room4.png", points, npc4);
        room5 = new Room("Room-5", "room5.png", points, npc5);
        room6 = new Room("Room-6", "room6.png", points, npc6);
        room7 = new Room("Room-7", "room7.png", points, npc7);
        room8 = new Room("Room-8", "room8.png", points, npc8);
        room9 = new Room("Room-9", "room9.png", points, npc9);

        // initialise room exits
        room1.setExit("east", room2);
        room2.setExit("west", room1);

        room2.setExit("east", room3);
        room3.setExit("west", room2);

        room3.setExit("north", room4);
        room4.setExit("south", room3);

        room4.setExit("west", room5);
        room5.setExit("east", room4);

        room5.setExit("west", room6);
        room6.setExit("east", room5);

        room6.setExit("north", room7);
        room7.setExit("south", room6);

        room7.setExit("east", room8);
        room8.setExit("west", room7);

        room8.setExit("east", room9);
        room9.setExit("west", room8);


        // the player starts the game outside
        player.setCurrentRoom(room1);

        rooms = new ArrayList<Room>();
        rooms.add(room7);
        rooms.add(room8);
        rooms.add(room9);
        rooms.add(room6);
        rooms.add(room5);
        rooms.add(room4);
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
    }

    /**
     * creates items and adds them to the room and to the player
     */
    public void createItems(){
    	Item apple, bread, cheese;
    	apple = new Apple("Apple", "Without description", ("Golden-Apple-Sprite.png"), 100, 100);
    	bread = new Bread("Bread",  "Without description",("bread.png"), 100, 150);
    	cheese = new Cheese("Cheese", "Without description", ("plain_cheese.png"), 150, 100);
    	player.getCurrentRoom().addItem(bread);
    	player.addItem(apple);
    	player.addItem(cheese);
    }

    /**
     * creates furniture and adds them to the room
	 * furniture is not applicable for the player
     */
    public void createFurnitures(){
    	Furniture chair, chair2;
    	chair = new Furniture("Chair", ("furniture1.png"), 100, 80);
    	chair2 = new Furniture("Chair", ("furniture1.png"), 300, 80);
    	player.getCurrentRoom().addFurniture(chair);
    	player.getCurrentRoom().getExit("east").get().addFurniture(chair2);
    	
    }

    public Player getPlayer(){
    	return player;
    }

    public Parser getParser(){
    	return parser;
    }

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<Room> getRooms(){
		return rooms;
	}

	public void setParser(Parser parser){
		this.parser = parser;
	}



}
