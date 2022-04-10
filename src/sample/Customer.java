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
    private int machineX;        // the X-coordinate of the customer's machine they are using.
    private int machineY;        // the Y-coordinate of the customer's machine they are using.
    private boolean hasMachine = false;
    private int cooldown = 15;
    public int satisfaction = 50; // from 0 to 100, changes overall satisfaction about the company.
    private boolean leaving = false;
    private boolean left = false;
    private boolean waiting = false;
    private boolean checked = false; //stores whether current isle was checked or not.

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
        locY = 1.0;
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
     * hasLeft method
     * @return if the customer has left the laundromat.
     */
    public boolean hasLeft() {
        return left;
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
     * @return The Y-coordinate location of the customer.
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
                    locY--;
                    break;
                case "right":
                    locX++;
                    break;
                case "down":
                    locY++;
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
        if (left) {
            return;
        }

        cooldown--;
        if (cooldown<=0) {
            cooldown = (int) (15 + 2*Math.random());
            if (leaving) {
                moveToExit();
                return;
            }
            //find machine they can use.
            ArrayList<Integer> moves = possibleMoves();

            if (!hasMachine) {

                //if there is an isle below the AI, look at all washers down those rows.
                if (moves.contains(3) && locX!=0 && !checked) {
                    //if there are machines above it and to the left, check those machines
                    int offsetY = 0;
                    while(GameController.gameEngine.tiles.get((int) (locY+1+offsetY)).get((int)(locX-1)) instanceof Machine) {
                        Machine t = (Machine)GameController.gameEngine.tiles.get((int) (locY+1+offsetY)).get((int)(locX-1));
                        if (t.getAvailable()) {
//                            System.out.println("Found machine.");
                            machineX = (int)locX-1;
                            machineY = (int)locY+1+offsetY;
                            hasMachine = true;
                            satisfaction+=10+10*Math.random();
                            return;
                        }
                        if (!t.isBroken()) {
                            satisfaction = Math.max(20, satisfaction-5);
                        }
                        offsetY++;
                    }
                    //if there are machines above it and to the right, check those machines
                    offsetY = 0;
                    while(GameController.gameEngine.tiles.get((int) (locY+1+offsetY)).get((int)(locX+1)) instanceof Machine) {
                        Machine t = (Machine)GameController.gameEngine.tiles.get((int) (locY+1+offsetY)).get((int)(locX+1));
                        if (t.getAvailable()) {
//                            System.out.println("Found machine (2).");
                            machineX = (int)locX+1;
                            machineY = (int)locY+1+offsetY;
                            hasMachine = true;
                            satisfaction+=10+10*Math.random();
                            return;
                        }
                        offsetY++;
                    }
                    checked = true;
                } else {
                    checked = false;
                    //move right if possible, else stop and get unsatisfied.
//                    System.out.println(moves);


//                    visitCooldown = 60+(int)((float)(1.1F-satisfaction/100)*(120+Math.random()*240));

                    if (moves.contains(2)) {
                        //System.out.println(locX);
                        move("right");
                    } else {
                        leaving = true;
                        satisfaction-=30+Math.random()*15;
                    }
                }
            } else {
                Machine t = (Machine)GameController.gameEngine.tiles.get(machineY).get(machineX);
                int diffX = (int) (locX-t.x), diffY = (int) (locY-t.y);
                if (Math.abs(diffX)+Math.abs(diffY)<=1) {
                    if (waiting) {
                        waiting = (t.getTimeUntilComplete()<=0);
                        leaving = true;
                        return;
                    } //else
                    if (!leaving) {
                        startMachine();
                        waiting = true;
                    } else {
                        stopMachine();
                    }
                } else {
                    moveToMachine();
                }
            }



        }
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

        //0 (1): up     x: 0,   y: 1
        //1 (2): right  x: 1,  y: 0
        //2 (3): down   x: 0,   y: -1
        //3 (4): left   x: -1,   y: 0

        for (int i=0; i<4; i++) {

            int x = (int) (locX + Math.abs((i + 3) % 4 - 2) - 1);
            if (x<0 || x>GameController.gameEngine.xSize-1) continue;

            int y = (int) (locY - (Math.abs((i) % 4 - 2) - 1));
            if (y<0 || y>GameController.gameEngine.ySize-1) continue;

            if (GameController.gameEngine.tiles.get(y)
                    .get(x) instanceof EmptyTile) {
                moves.add(i+1);
            }
        }
        return moves;
    }

    public void moveToMachine() {
        ArrayList<Integer> moves = possibleMoves();
        if (locY>machineY && moves.contains(1)) {
            move("up");
            return;
        }
        if (locY<machineY && moves.contains(3)) {
            move("down");
            return;
        }
        if (locX<machineX && moves.contains(2)) {
            move("right");
            return;
        }
        if (locX>machineX && moves.contains(4)) {
            move("left");
        }
    }

    public void moveToExit() {
        ArrayList<Integer> moves = possibleMoves();
        if (moves.contains(1)) {
            move("up");
            return;
        }
        if (moves.contains(4)) {
            move("left");
            return;
        }
        if (atDoor()) {
            move("left");
            left = true;

        }
    }

    public void startMachine() {
        int diffX = (int) (locX-machineX), diffY = (int) (locY-machineY);
        Machine t = (Machine) GameController.gameEngine.tiles.get((int) (locY-diffY)).get((int) (locX-diffX));
        t.setTimeUntilComplete((t.isUpgraded()) ? 20 : 30);
        t.setAvailable(false);
    }

    public void stopMachine() {
        int diffX = (int) (locX-machineX), diffY = (int) (locY-machineY);
        Machine t = (Machine) GameController.gameEngine.tiles.get((int) (locY-diffY)).get((int) (locX-diffX));
        t.setAvailable(true);

        //increment cash here
        GameController.gameEngine.balanceSheet.currentCapital+=100;
    }

    public boolean atDoor() {
        return (GameController.gameEngine.tiles.get((int) locY).get((int)locX-1) instanceof DoorTile);
    }

}
