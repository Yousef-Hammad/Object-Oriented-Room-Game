/**
 * This class is part of the Zuul world application.
 * 
 * This class represents the items that can placed inside a given room.
 *
 * @author Yousef Hammad 101217858
 * @version March 18, 2023 Assignment 2
 */
public class Item
{
    //item weight
    private double weight;
    
    //short name of the item
    private String name;
    
    //item description
    private String description;

    /**
     * Constructor for objects of class Item
     * 
     * @param name is the short name description of the item
     * @param description is the description of the item
     * @param weight is the weight of the item
     */
    public Item(String name, String description, double weight)
    {
        this.name = name;
        this.weight = weight;
        this.description = description;
    }

    /**
     * Method that returns the name of the item
     * 
     * @return a String name of the item
     */
    public String getName()
    {
        return name;
    }
    
    
    /**
     * Method that returns a description of the item along with its weight
     * 
     * @return a String description of the item
     */
    public String getDescription()
    {
        return name + ": a " + description + " that weighs " + weight + "kg.";
        
    }
}
