package sample; // for the love of pete, who thought this was a good package name!

import java.util.ArrayList;
import java.util.Random;        // Needed for the Random name generator.
/**
 * This Customer class
 */
public class Customer
{
    private final String name;      // the name of a Customer instance
    private final float weight;     // the weight of the laundry being cleaned
    private double locX;            // the X-coordinate location of the customer
    private double locY;            // the Y-coordinate location of the customer

    /**
     * Constructor
     * @param custName The customer's name.
     * @param lbs The weight of the customer's laundry.
     * The constructor increments the static field customerCount, to count the number of customers.
     */
    public Customer(String custName, float lbs)
    {
        weight = lbs;
        locX = 0.0;           // assuming the laundromat door is in the lower left corner of the map
        locY = 0.0;

        // Names to randomly choose from if 'custName' parameter is null.
        String[] names = {"Liam", "Olivia", "Noah", "Emma", "Oliver", "Ava", "Elijah", "Charlotte",
                            "William", "Sophia", "James", "Amelia", "Benjamin", "Isabella", "Lucas",
                            "Mia", "Henry", "Evelyn", "Alexander", "Harper"};
        if (custName != null)
            name = custName;
        else {
            Random index = new Random();
            name = names[index.nextInt(names.length-1)];
        }
    }

    /**
     * getName method
     * @return The customer's name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * getWeight method
     * @return The customer's weight in laundry being cleaned.
     */
    public float getWeight()
    {
        return weight;
    }

    /**
     * getLocX method
     * @return The X-coordinate location of the customer.
     */
    public double getLocX()
    {
        return locX;
    }

    /**
     * getLocY method
     * @return The Y-coordinate locatino of the customer.
     */
    public double getLocY()
    {
        return locY;
    }

    /**
     * move method
     * @param dir The direction that the customer is moving.
     */
    public void move(String dir)
    {
        // Attempt to move the customer.
        try {
            switch(dir)
            {
                case "left":
                    locX--;
                    break;
                case "up":
                    locY++;
                    break;
                case "right":
                    locX++;
                    break;
                case "down":
                    locY--;
                    break;
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Bad customer direction.");
        }

    }
    /**
     * The think method controls how the Customer moves and where they move.
     */
    public void think()
    {

    }

    /**
     * toString method
     * @return A string indicating the customer's name and weight of laundry being cleaned.
     */
    public String toString()
    {
        return "Customer name: " + name + "\nLaundry weight: " + weight + "\nX-coord: " + locX +
                "\nY-coord: " + locY;
    }

    public ArrayList<Integer> possibleMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i=0; i<4; i++) {

            if (GameController.gameEngine.tiles.get((int) (locY + Math.abs((i + 1) % 4 - 2) - 1))
                    .get((int) (locX + Math.abs((i + 2) % 4 - 2) - 1)) instanceof EmptyTile) {
                moves.add(i+1);
            }
        }
        return moves;
    }
}
