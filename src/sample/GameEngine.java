package sample;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;

import static javafx.application.Platform.exit;
import static sample.Constants.*;

public class GameEngine
{
    final float xSize = 12;
    final float ySize = 10;
    public ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    public ArrayList<Customer> customers = new ArrayList<>();
    public BalanceSheet balanceSheet = new BalanceSheet();
    private GameTimer timer;
    public int satisfaction = 50;
    public float speedModifier = 1;

    public GameEngine(){
        start();
        //continue here.
    }

    public void start() {
        timer = new GameTimer();
        timer.start();

        tiles = new ArrayList<>();
        customers = new ArrayList<>();

        //Define tiles for laundry mat.

        //i = y
        //j = x
        for (int i=0; i<ySize; i++) {
            tiles.add(new ArrayList<>());
            for (int j=0; j<xSize; j++) {
                if (j == 0 || j == xSize-1 || i == 0 || i == ySize-1) {
                    tiles.get(i).add(new Wall(j, i));
                } else if (j %3!=1 && i !=1 && i !=ySize-2 && j !=1 && j !=xSize-2) {
                    tiles.get(i).add(new Machine(j, i));
                } else {
                    tiles.get(i).add(new EmptyTile(j, i));
                }
            }
        }
        //defines the doorTile as the last tile in the building.
        tiles.get((int) (1)).set((int) (0), new DoorTile(0, 1));
    }
    private void displayShop(){
        tiles.forEach(tileRow -> {
                    for (Tile tileN : tileRow) {
                        switch (tileN.getClass().toString()) {
                            case "class sample.Wall":
                                System.out.print("*");
                                break;
                            case "class sample.EmptyTile":
                                System.out.print(" ");
                                break;
                            case "class sample.Machine":
                                System.out.print("M");
                                break;
                            case "class sample.DoorTile":
                                System.out.print("D");
                                break;
                        }
                    }
                    System.out.print("\n");
                }
        );
    }

    public void repair() {
        if(Constants.repairCost <= balanceSheet.currentCapital)
        {
            for(int i =0; i<tiles.size(); i++)
            {
                for (int j=0; j<tiles.get(i).size();j++)
                {
                    Tile curTile = tiles.get(i).get(j);
                    if (curTile instanceof Machine && ((Machine)curTile).isSpecialTile() && ((Machine)curTile).isBroken())
                    {
                        balanceSheet.repair();
                        ((Machine)curTile).setBroken(false);
                    }
                }
            }
        }
    }

    public void incSpeed() {
        speedModifier+=0.1F;
    }

    private class GameTimer extends AnimationTimer {

        //Keeps track of time
        long lastUpdate = 0;
        float rate = 1000_000_000/(float)FRAME_RATE;
        private int dayCounter = 0;
        private int secondCounter = 0;
        private int frameCounter = 0;
        private int visitCooldown = 30;

        //Keeps track of when to stop.
//        int order;

        //constructor method.
        public GameTimer() {
//            this.order = order;
        }

        /**
         * Clock that runs the game (customer AI calls, events, etc.).
         * @param now
         */
        @Override
        public void handle(long now) {
            float difference = now - lastUpdate;

            //100 milliseconds (.1 seconds)
            //runs every frame.
            if (difference>=rate) {
                speedModifier = Math.max(1, speedModifier-(0.01F/30F)*speedModifier);
                GameController.graphics.draw();
                frameCounter = (frameCounter + 1) % FRAME_RATE;

                //call customer logic every frame.
                for (int i = 0; i < customers.size(); i++) {
                    customers.get(i).think();
                }
                //determines if it's time to increase debt via interest.
                //...

                //determine if a customer should spawn
                visitCooldown--;
                if (visitCooldown<=0) {
                    customers.add(new Customer(null, 100));

                    visitCooldown = (int) (60+((1.1F-satisfaction/100)*(30+Math.random()*120))/speedModifier);
                    GameController.graphics.setText(speedModifier);
                }

                //lowers a machine's cooldown over time.
                for (int i = 0; i < ySize; i++) {
                    for (int j = 0; j < xSize; j++) {
                        //skips non-machine tiles or inactive machines or done machines.
                        if ((tiles.get(i).get(j) instanceof Machine) && !(((Machine)tiles.get(i).get(j)).isBroken()) && !((Machine) tiles.get(i).get(j)).getAvailable())
                        {
                            Machine machine = (Machine) tiles.get(i).get(j);

                            //don't use this commented out code for now, but when the customer uses a machine, something like this should be used.
//                        machine.setTimeUntilComplete(30);
                            //lower cooldown of machine
                            machine.washMachine();
                        }
                    }
                }

                //if more than 1 minute passes, then increment the day counter. If you are not in debt by the end of
                //the 30 day period then you win.

                //five minutes, then a fifteen second break.
                if (frameCounter == 0) {
                    secondCounter = (secondCounter + 1) % 75;
                    if (secondCounter == 60) {
                        GameController.gameEngine.balanceSheet.debt+=GameController.gameEngine.balanceSheet.debt
                                *GameController.gameEngine.balanceSheet.interest;
                        dayCounter++;
                        //do more things after the day passes.
                    }
                    if (dayCounter >= 30) {
                        exit();
                    }
                }
//            GameController.gameEngine.satisfaction += (e.satisfaction - GameController.gameEngine.satisfaction)/10F;
//            System.out.println(GameController.gameEngine.satisfaction);

                //Remove customers who have left.
                customers.forEach(e-> {
                    if (e.hasLeft()) {
                        GameController.gameEngine.satisfaction += (e.satisfaction - GameController.gameEngine.satisfaction)/10F;
                        System.out.println(GameController.gameEngine.satisfaction);
                    }
                });
                customers.removeIf(Customer::hasLeft);

            }
        }
    }
}
