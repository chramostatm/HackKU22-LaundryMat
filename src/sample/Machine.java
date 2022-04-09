package sample;

public class Machine extends Tile
{
    private boolean available = false;
    private boolean active = false;
    private int timeUntilComplete = 0;

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

}
