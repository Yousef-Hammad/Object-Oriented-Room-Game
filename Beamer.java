/**
 * This class represents the beamer item that will be used by the player to teleport to a different room 
 * when charged
 *
 * @author Yousef Hammad 101217858
 * @version March 18, 2023 Assigment 2
 */
public class Beamer extends Item
{
    
    private boolean Charged;
    private Room chargedRoom;

    /**
     * Constructor for objects of class Beamer
     * 
     * @param name the name of the beamer
     * @param description a string description of the beamer
     * @param weight the weight of the beamer
     */
    public Beamer(String name, String description, double weight)
    {
        super (name, description, weight);
        Charged = false;
        chargedRoom = null;
    }

    /**
     * Getter method to check if the beamer is charged
     *
     * @return true if the beamer is charged, false otherwise
     */
    public boolean isCharged()
    {
        return Charged;
    }
    
    /**
     *Method to charge the beamer inside a given room in the game
     *
     *@param curr the current room that the beamer will be charged in
     */
    public void chargeBeamer(Room curr)
    {
        //if the beamer is not already charged
        if (!isCharged()){
            chargedRoom = curr;
            Charged = true;
            System.out.println("Beamer successfully charged.");
        }
        else{
            System.out.println("Beamer has already been charged!");
        }
    }
    
    /**
     * Method to fire the beamer and return the room that the player will teleport back to.
     *
     *@return the room that the player will be teleported back to.
     */
    public Room fireBeamer()
    {
        Charged = false;
        System.out.println("Beamer successfully fired.");
        return chargedRoom;
    }
}
