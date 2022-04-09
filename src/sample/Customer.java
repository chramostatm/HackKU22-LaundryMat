package sample; // for the love of pete, who thought this was a good package name!

/**
 * This Customer class
 */
public class Customer
{
    private static int customerCount = 0;
    private final String name;    // the name of a Customer instance
    private final float weight;   // the weight of the laundry being cleaned

    /**
     * Constructor
     * @param custName The customer's name.
     * @param lbs The weight of the customer's laundry.
     * The constructor increments the static field customerCount, to count the number of customers.
     */
    public Customer(String custName, float lbs)
    {
        customerCount++;
        name = custName;
        weight = lbs;
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
     * The think method returns ?
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
        return "Customer name: " + name + "\nLaundry weight: " + weight;
    }
}
