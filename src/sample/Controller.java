package sample;

public class Controller
{
    GameEngine gameEngine;
    public void startGame()
    {
        gameEngine = new GameEngine();
        gameEngine.start();
    }
}
