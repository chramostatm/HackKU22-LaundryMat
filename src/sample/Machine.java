package sample;

import static sample.Constants.FRAME_RATE;

public class Machine extends Tile
{
    private boolean available = false; //stores if the machine is ready to be occupied with another user.
    private int timeUntilComplete = 0; // in frames
    private boolean done = false; //stores if the machine is ready to be unloaded.
    private boolean upgraded = false;
    public boolean broken = true;
    private int uses = 0;
    private boolean specialTile = false;
    public Machine(float inX,float inY) {
        super(inX, inY);
    }

    public boolean isBroken()
    {
        return broken;
    }

    public void setBroken(boolean inBool)
    {
        broken = inBool;
    }

    public boolean getAvailable() {
        return this.available;
    }

    public void setSpecialTile(boolean inBool)
    {
        specialTile = inBool;
    }

    public boolean isSpecialTile()
    {
        return specialTile;
    }

    public int getTimeUntilComplete() {
        return timeUntilComplete;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setTimeUntilComplete(int seconds) {
        timeUntilComplete = FRAME_RATE*seconds;
    }

    public void washMachine() {
        timeUntilComplete--;
        if (timeUntilComplete<=0) {
            done = true;
            uses++;
            if(uses > 10) {
                if (!broken)
                    broken = ((int)(Math.random()*10)!=1);
            }
        }
    }

    public boolean isUpgraded() {
        return upgraded;
    }
}
