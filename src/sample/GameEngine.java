package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.ArrayList;

import static sample.Constants.*;

public class GameEngine
{
    final float initialSize = 10;
    ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    ArrayList<Customer> customers = new ArrayList<>();


    public GameEngine()
    {


        //continue here.
    }

    public void start() {
        tiles = new ArrayList<>();
        customers = new ArrayList<>();

        //Define tiles for laundry mat.

        //i = y
        //j = x
        for (int i=0; i<initialSize; i++) {
            for (int j=0; j<initialSize; j++) {
                tiles.add(new ArrayList<>());
                if (j == 0 || j == initialSize-1 || i == 0 || i == initialSize-1) {
                    tiles.get(i).add(new Wall(i, j));
                } else if (j %3!=0 && i !=1 && i !=initialSize-2) {
                    tiles.get(i).add(new Machine(i, j));
                } else {
                    tiles.get(i).add(new EmptyTile(i, j));
                }
            }
        }
        //defines the doorTile as the last tile in the building.
        tiles.get((int) (initialSize-1)).set((int) (initialSize-1), new DoorTile(initialSize-1, initialSize-1));
    }

    private class GameTimer extends AnimationTimer {

        //Keeps track of time
        long lastUpdate = 0;
        float rate = 1000_000_000/(float)FRAME_RATE;

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
            if (difference>=rate) {

                //call customer logic every frame.
                for (int i=0; i<customers.size(); i++) {
                    customers.get(i).think();
                }
                //determines if it's time to increase debt via interest.
                //...

                //lowers a machine's cooldown over time.
                for (int i=0; i<initialSize; i++) {
                    for (int j=0; j<initialSize; j++) {
                        //skips non-machine tiles or inactive machines.
                        if (!(tiles.get(i).get(j) instanceof Machine) || !(((Machine)tiles.get(i).get(j)).getActive())) continue;
                        Machine machine = (Machine)tiles.get(i).get(j);

                        //don't use this commented out code for now, but when the customer uses a machine, something like this should be used.
//                        machine.setTimeUntilComplete(30);
                        //lower cooldown of machine
                        machine.washMachine();
                    }
                }
            }
        }
    }

}
