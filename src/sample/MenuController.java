package sample;

import javafx.scene.Scene;

public class MenuController
{
    static Graphics graphics;

    public static void initialize(Graphics inGraphics, Scene menuScene)
    {
        graphics = inGraphics;
        graphics.showScene(menuScene);
    }

    public void startGame()
    {
        GameController.initialize(graphics);
    }
}
