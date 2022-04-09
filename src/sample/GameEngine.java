package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.ArrayList;

import static javafx.application.Platform.exit;
import static sample.Constants.*;

public class GameEngine
{
    final float initialSize = 10;
    public ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    public ArrayList<Customer> customers = new ArrayList<>();
    private BalanceSheet balanceSheet = new BalanceSheet();
    private GameTimer timer;
    public GameEngine(){
        start();
        displayShop();
        //continue here.
    }

    public static void main(String[] args) {
        new GameEngine();
    }
    public void start() {
        timer = new GameTimer();
        timer.start();

        tiles = new ArrayList<>();
        customers = new ArrayList<>();

        //Define tiles for laundry mat.

        //i = y
        //j = x
        for (int i=0; i<initialSize; i++) {
            tiles.add(new ArrayList<>());
            for (int j=0; j<initialSize; j++) {
                if (j == 0 || j == initialSize-1 || i == 0 || i == initialSize-1) {
                    tiles.get(i).add(new Wall(j, i));
                } else if (j %3!=0 && i !=1 && i !=initialSize-2) {
                    tiles.get(i).add(new Machine(j, i));
                } else {
                    tiles.get(i).add(new EmptyTile(j, i));
                }
            }
        }
        //defines the doorTile as the last tile in the building.
        tiles.get((int) (1)).set((int) (0), new DoorTile(1, 0));
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

    private class GameTimer extends AnimationTimer {

        //Keeps track of time
        long lastUpdate = 0;
        float rate = 1000_000_000/(float)FRAME_RATE;
        private int dayCounter = 0;
        private int secondCounter = 0;
        private int frameCounter = 0;

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
                GameController.graphics.draw();
                frameCounter = (frameCounter + 1) % FRAME_RATE;

                //call customer logic every frame.
                for (int i = 0; i < customers.size(); i++) {
                    customers.get(i).think();
                }
                //determines if it's time to increase debt via interest.
                //...

                //lowers a machine's cooldown over time.
                for (int i = 0; i < initialSize; i++) {
                    for (int j = 0; j < initialSize; j++) {
                        //skips non-machine tiles or inactive machines or done machines.
                        if (!(tiles.get(i).get(j) instanceof Machine) || !(((Machine) tiles.get(i).get(j)).getActive()) || ((Machine) tiles.get(i).get(j)).getAvailable())
                            continue;
                        Machine machine = (Machine) tiles.get(i).get(j);

                        //don't use this commented out code for now, but when the customer uses a machine, something like this should be used.
//                        machine.setTimeUntilComplete(30);
                        //lower cooldown of machine
                        machine.washMachine();
                    }
                }

                //if more than 5 minutes pass, then increment the day counter. If you are not in debt by the end of
                //the 30 day period then you win.

                //five minutes, then a fifteen second break.
                if (frameCounter == 0) {
                    secondCounter = (secondCounter + 1) % 315;
                    if (secondCounter >= 300) {
                        dayCounter++;
                        //do more things after the day passes.
                    }
                    if (dayCounter >= 30) {
                        exit();
                    }
//                    if (balanceSheet.getCurrentCapital() - balanceSheet.getdebt() == 0) {
//                        exit();
//                    }
                }
            }
        }
    }
}
