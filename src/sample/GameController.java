package sample;

public class GameController
{
    static GameEngine gameEngine;
    static Graphics graphics;

    public static void initialize(Graphics inGraphics)
    {
        graphics = inGraphics;
        gameEngine = new GameEngine();
        graphics.startGame();
        gameEngine.start();
    }

}
