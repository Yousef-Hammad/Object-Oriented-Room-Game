import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Assignment 2
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * Rooms contain items within them. Different rooms can have have
 * the same/different items within them, and one room can have multiple
 * instances of the same item.
 * 
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

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    
    private static ArrayList<Room> Rooms = new ArrayList<Room>(); //store all the rooms that are in the game

    private ArrayList<Item> items;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard". As well, initialize the arraylist of items to be
     * used.
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        Rooms.add(this);
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Item:
     *        a wooden chair that weighs 5.0kg
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String itemString = "";
        for (Item current: items){
            itemString += "\n    " + current.getDescription();
        }
        
        return "You are " + description + ".\n" + getExitString()
        + "\nItems:" + itemString;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

   /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Add a specific item to the room. Need to check the item isnt null before
     * adding it
     * 
     * @param item the item to be added in the room
     */
    public void addItem(Item item) 
    {
        if (item != null){
            items.add(item);
        }
    }
    
    /**
     * Pick up an item from the room to return
     * 
     * @param item the item to be added in the room
     */
    public Item grabItem(String item) 
    {
        for (Item curr: items){
            if (curr.getName().equals(item)){ //if the item is in the room
                items.remove(curr);
                return curr;
            }
        }
        return null;
    }
    
    /**
     * getter method to return all the rooms in the game
     * 
     * @return the list containing all the rooms in the game
     */
    public static ArrayList<Room> getRooms() 
    {
        return Rooms;
    }
}

