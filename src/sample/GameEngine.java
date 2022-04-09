package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.ArrayList;

public class GameEngine
{
    final float initialSize = 10;
    ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    ArrayList<ArrayList<Customer>> customers = new ArrayList<>();


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

    /*
    private class MyTimer extends AnimationTimer {

        //Keeps track of time
        long lastUpdate = 0;

        //Keeps track of when to stop.
        int order;

        //constructor method.
        public MyTimer(int order) {
            this.order = order;
            if (mazePane.isRunning()) {
                mazePane.setStopped(true);
                mazePane.setRunning(false);
            }

            if (mazePane.robot.getType() != "PortalRobot") {
                mazePane.maze.setUsedPortal(false);
            }
            mazePane.maze.resetMaze();
            mazePane.robot.resetRobot();
        }

        /
          Clock that calls the robot method to solve the maze. Respects the rules about when to do so as well.
          @param now
         /
        @Override
        public void handle(long now) {
            if (now - lastUpdate >= 100_000_000) {
                if (!mazePane.robot.solved() && !mazePane.isStopped() && order == getOrder()) {
                    lastUpdate = now;
                    mazePane.robot.chooseMoveDirection();
                    mazePane.paint();
                } else {
                    stop();
                    mazePane.setStopped(false);
                    mazePane.setRunning(false);
                    Dialog<String> dialog = new Dialog<>();

                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().add(type);

                    if (mazePane.robot.solved()) {
                        dialog.setContentText(mazePane.robot.getEndMsg());
                        dialog.setOnCloseRequest(dialogEvent -> dialog.close());
                        dialog.show();
                    }
                }
            }
        }
    }
    */
}
