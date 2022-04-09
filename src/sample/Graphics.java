package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Graphics
{
    Stage primaryStage;
    public Graphics(Stage inPrimaryStage)
    {
        primaryStage = inPrimaryStage;
        showFxmlScene("mainMenu.fxml");
    }

    public void startGame()
    {
        showFxmlScene("gameScreen.fxml");
    }

    public void showFxmlScene(String fxmlName)
    {
        Parent root = null;
        try
        {
            root = FXMLLoader.load(getClass().getResource(fxmlName));
        }catch (Exception e)
        {
            System.out.println("Error loading fxml file");
        }

        primaryStage.setTitle("Sud Tycoon");
        Scene mainMenu = new Scene(root);
        primaryStage.setScene(mainMenu);
        primaryStage.show();
    }
}