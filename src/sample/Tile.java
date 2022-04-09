package sample;

public class Tile
{
    final float width = 10f;
    final float height = 10f;
    float x;
    float y;


    public Tile(float inX, float inY)
    {
        x = inX;
        y = inY;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public float getX(float inSize)
    {
        return x;
    }

    public float getY(float inSize)
    {
        return y;
    }

}
