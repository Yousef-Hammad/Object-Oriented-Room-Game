import java.util.Stack;

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
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns. In addition, it also allows
 *  the player to navigate to the previouse room using 2 different methods.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Lynn Marshall
 * @version October 21, 2012
 * 
 * @author Yousef Hammad 101217858
 * @version March 18, 2023 Assignment 2
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> stackpreviousRoom;
    
    private Item holdItem; //item being held
    private int hungerLevel; //number of items allowed to hold before becoming hungry
    /**
     * Create the game and initialize its internal map. As well, initialize 
     * previousRoom and stackpreviousRoom for the "back" and "stackBack"
     * commands 
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        stackpreviousRoom = new Stack<Room>();
        holdItem = null;
        hungerLevel = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        Item chair1, chair2, chair3, chair4, chair5, firTree, laptop, blanket, phone, 
        backpack, car, cookie1, cookie2, cookie3, cookie4, chair6;
        TransporterRoom transporterR;
        Beamer beamer1, beamer2;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporterR = new TransporterRoom("in a transporter room");
        
        // create some items
        chair1 = new Item("chair", "wooden chair", 5.0);
        chair2 = new Item("chair", "wooden chair", 5.0);
        chair3 = new Item("chair", "wooden chair", 5.0);
        chair4 = new Item("chair", "wooden chair", 5.0);
        chair5 = new Item("chair", "wooden chair", 5.0);
        chair6 = new Item("chair", "wooden chair", 5.0);
        firTree = new Item("firTree", "wooden fir tree", 500.5);
        laptop = new Item("laptop", "mac laptop", 10.15);
        blanket = new Item("blanket", "warm blanket", 1.5);
        phone = new Item("phone", "telephone", 2.0);
        backpack = new Item("backpack", "leather backpack", 7.1);
        car = new Item("car", "red car", 4000);
        cookie1 = new Item("cookie", "a yummy chocolate chip cookie", 0.2);
        cookie2 = new Item("cookie", "a yummy chocolate chip cookie", 0.2);
        cookie3 = new Item("cookie", "a yummy chocolate chip cookie", 0.2);
        cookie4 = new Item("cookie", "a yummy chocolate chip cookie", 0.2);
        
        beamer1 = new Beamer("beamer", "a wonderful beamer", 1.0);
        beamer2 = new Beamer("beamer", "a wonderful beamer", 1.0);

        
        // add the items to the rooms (at least 1 item per room)
        outside.addItem(chair1);
        outside.addItem(car);
        outside.addItem(beamer1);
        outside.addItem(cookie1);
        theatre.addItem(firTree);
        theatre.addItem(phone);
        theatre.addItem(backpack);
        pub.addItem(chair2);
        lab.addItem(laptop);
        lab.addItem(cookie2);
        lab.addItem(chair3);
        lab.addItem(chair4);
        office.addItem(blanket);
        office.addItem(chair5);
        office.addItem(cookie3);
        office.addItem(cookie4);
        office.addItem(beamer2);
        transporterR.addItem(chair6);
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);
        theatre.setExit("south", transporterR);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        transporterR.setExit("anywhere", null);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")){
            back(command);
        }
        else if (commandWord.equals("stackBack")){
            stackBack(command);
        }
        else if (commandWord.equals("take")){
            take(command);
        }
        else if (commandWord.equals("drop")){
            drop(command);    
        }
        else if (commandWord.equals("charge")){
            beamerCharge(command);
        }
        else if (commandWord.equals("fire")){
            beamerFire(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
        //for(String command: parser.getCommands()) {
          //  System.out.print(command + "  ");
        //}
        //System.out.println();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            // update previousRoom and stackpreviousRoom for the commands back and stackback
            previousRoom = currentRoom;
            stackpreviousRoom.push(currentRoom);
            currentRoom = nextRoom;
            printRoomAndCarry();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /** 
     * "look" was entered. Check the rest of the commmand to see if no other
     * command was entered as well. Otherwise, print the the details of the 
     * current room.
     *
     *@param command The command to be processed.
     */
    private void look(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else {
            printRoomAndCarry();
        }
    }
    
    /** 
     * Print a descprition of the room along with the items the player is carrying
     *
     */
    private void printRoomAndCarry() 
    {
        System.out.println(currentRoom.getLongDescription());
        if (holdItem == null){
            System.out.println("You are not carrying an item");
        }
        else{
            System.out.println("You are carrying:\n    " + holdItem.getDescription());
        }
        
    }

     /** 
     *"eat" was entered. Check the rest of the command to see if no other
     *command was entered as well. Otherwise, print the message stating that 
     *the player has eaten.
     *
     *@param command The command to be processed.
     */
    private void eat(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Eat what?");
        }
        else {
            if (holdItem == null || !holdItem.getName().equals("cookie")){
                System.out.println("You have no food with you to eat");
            }
            else{
                System.out.println("You have eaten the cookie.");
                holdItem = null;
                hungerLevel += 5;
            }
        }
        
    }
    
    /** 
     *"back" was entered. navigate to the last visited room in the game
     *
     *@param command The command to be processed.
     */
    private void back(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            //if there is no room to go back to
            if (previousRoom == null){
                System.out.println("No room to go back to.");
            }
            else{
                //navigate to the previouse room and print the room details
                Room temporaryRoom = currentRoom;
                currentRoom = previousRoom;
                previousRoom = temporaryRoom;
                stackpreviousRoom.push(temporaryRoom);
                
                System.out.println(currentRoom.getLongDescription());
            }
            
        }
    }
    
    /** 
     *"stackBack" was entered. Navigate back one room at a time until the
     *beginning of the game
     *
     *@param command The command to be processed.
     */
    private void stackBack(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("stackBack what?");
        }
        else {
            //if there is no room to go back to
            if (stackpreviousRoom.empty()){
                System.out.println("No room to go back to.");
            }
            else{
                //go to the previouse room and remove that room from the stackPreviousRoom stack
                previousRoom = currentRoom;
                currentRoom = stackpreviousRoom.pop();
                //dislplay room details
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    
    
    /** 
     *take allows the player to grab any single item that is inside a given room. The
     *item MUST be in the room for the player to grab it. 
     *
     *@param command The command to be processed.
     */
    private void take(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("take what?");
        }
        else{
            if (holdItem == null){ //if no item is currently being held
                
                String secondWordItem = command.getSecondWord();
                
                if (hungerLevel > 0 || secondWordItem.equals("cookie")){
                    Item grabItem = currentRoom.grabItem(secondWordItem);
                    
                    if (grabItem != null){ //if the item is in the room 
                        if (!secondWordItem.equals("cookie")){ //if the item is not a cookie
                            hungerLevel -=1;
                        }
                        System.out.println("You Picked up " + secondWordItem);
                        holdItem = grabItem;
                    }else{
                    System.out.println("That item is not in the room");
                    }
                }else{
                    System.out.println("You are hungry. You need to eat a cookie before grabbing any items.");
                }
            }
            else{
                System.out.println("You are already holding something!");
            }
        }      
    }
    
        
    /** 
     *drop allows the players to drop the current item that they are holding. Dropping
     *an item will store it in the given rooms item's list.
     *
     *@param command The command to be processed.
     */
    private void drop(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("drop what?");
        }
        else{
            if (holdItem == null){
                System.out.println("You have nothing to drop!");
            }
            else{
                currentRoom.addItem(holdItem);
                System.out.println("You have dropped " + holdItem.getName());
                holdItem = null;
            }
        } 
    }
    
    /** 
     *Method to charge the beamer inside a given room
     *
     *@param command The command to be proccessed
     */
    private void beamerCharge(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("charge what?");
        }
        else{
            if (holdItem instanceof Beamer){
                ((Beamer)holdItem).chargeBeamer(currentRoom);
            }
            else{
                System.out.println("You are not holding a beamer to charge!");
            }
        } 
    }
    
    /** 
     *Method to fire the beamer inside a given room
     *
     *@param command The command to be proccessed
     */
    private void beamerFire(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("fire what?");
        }
        else{
            if (holdItem instanceof Beamer){
                Beamer castBeamer = (Beamer) holdItem;
                
                if (castBeamer.isCharged()){
                    previousRoom = currentRoom;
                    stackpreviousRoom.push(currentRoom);
                    currentRoom = castBeamer.fireBeamer();
                    
                    printRoomAndCarry();
                }
                else{
                   System.out.println("Beamer needs to be charged first!"); 
                }
            }
            else{
                System.out.println("You are not holding a beamer to fire!");
            }
        }
    } 
}
