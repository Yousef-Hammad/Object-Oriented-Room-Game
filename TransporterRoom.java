import java.util.Random;

/**
 * Write a description of class TransporterRoom here.
 *
 * @author Yousef Hammad 101217858
 * @version March 18, 2023 Assignment 2
 */
public class TransporterRoom extends Room
{
    private Random randomRoomIndex = new Random();
    
    /**
     * initializes the transporter room
     */
    public TransporterRoom(String description) 
    {
        super(description);
    }
    
    /**
    * Returns a random room, independent of the direction parameter.
    *
    * @param direction Ignored.
    * @return A randomly selected room.
    */
    public Room getExit(String direction)
    {
        return findRandomRoom();
    }
    
    /**
    * Choose a random room.
    *
    * @return The room we end up in upon leaving this one.
    */
    private Room findRandomRoom()
    {
        return getRooms().get(randomRoomIndex.nextInt(getRooms().size()));
    }
}