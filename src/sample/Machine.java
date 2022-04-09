package sample;

import static sample.Constants.FRAME_RATE;

public class Machine extends Tile
{
    private boolean available = false; //stores if the machine is ready to be occupied with another user.
    private boolean active = false;
    private int timeUntilComplete = 0; // in frames
    private boolean done = false; //stores if the machine is ready to be unloaded.

    public Machine(float inX,float inY) {
        super(inX, inY);
    }

    public boolean getAvailable() {
        return this.available;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTimeUntilComplete(int seconds) {
        timeUntilComplete = FRAME_RATE*seconds;
    }

    public void washMachine() {
        timeUntilComplete--;
        if (timeUntilComplete<=0) {
            done = true;
        }
    }

}
