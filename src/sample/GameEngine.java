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
        //Define tiles for laundry mat.
        for (int i=0; i<initialSize; i++) {
            for (int j=0; j<initialSize; j++) {
                int x = i;
                int y = j;
                tiles.add(new ArrayList<>());
                if (x==0 || x == initialSize-1 || y == 0 || y == initialSize-1) {
                    tiles.get(i).add(new Wall(x, y));
                } else if (x%3!=0 && y!=1 && y!=initialSize-2) {
                    tiles.get(i).add(new Machine(x, y));
                } else {
                    tiles.get(i).add(new EmptyTile(x, y));
                }
            }
        }
        //defines the doorTile as the last tile in the building.
        tiles.get((int) (initialSize-1)).set((int) (initialSize-1), new DoorTile(initialSize-1, initialSize-1));

        //continue here.
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

                //determines if it's time to increase debt via interest.
                for (int i=0; i<customers.size(); i++) {
                    customers.get(i).think();
                }
            }
        }
    }

}
