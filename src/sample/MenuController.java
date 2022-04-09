package sample;

public class MenuController
{
    static Graphics graphics;

    public static void initialize(Graphics inGraphics)
    {
        graphics = inGraphics;
        graphics.showFxmlScene("mainMenu.fxml");
    }

    public void startGame()
    {
        GameController.initialize(graphics);
        graphics.startGame();
    }
}
