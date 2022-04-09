package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;


public class Graphics
{
    Stage primaryStage;
    Pane gamePane;
    GameEngine engine;
    public Graphics(Stage inPrimaryStage)
    {
        primaryStage = inPrimaryStage;
        primaryStage.setTitle("Sud Tycoon");
    }

    public void startGame(GameEngine inEngine)
    {
        Group root = new Group();
        gamePane = new Pane();
        engine = inEngine;

        Button upgrade = new Button();
        upgrade.setText("Upgrade");
        Button repair = new Button();
        repair.setText("Repair");
        Button close = new Button();
        close.setText("Close");
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(upgrade, repair, close);

        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(buttonBox, gamePane);

        Scene gameScene = new Scene(root, 550,500);
        root.getChildren().addAll(mainBox);
        showScene(gameScene);
    }

    public void draw()
    {
        gamePane.
        for (int i=0; i<engine.tiles.size(); i++) {
            for (int j=0; j<engine.tiles.get(i).size(); j++)
            {
                Rectangle r = new Rectangle(j*50, i*50, 50, 50);
                gamePane.getChildren().add();
            }
        }
    }

    public void showScene(Scene inScene)
    {
        primaryStage.setScene(inScene);
        primaryStage.show();
    }
}