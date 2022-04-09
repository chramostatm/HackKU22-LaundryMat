package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Graphics
{
    Stage primaryStage;
    Pane gamePane;
    public Graphics(Stage inPrimaryStage)
    {
        primaryStage = inPrimaryStage;
        primaryStage.setTitle("Sud Tycoon");
    }

    public void startGame()
    {
        Group root = new Group();
        gamePane = new Pane();
        Pane tileSpace = new Pane();

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

        tileSpace.setPrefSize(500, 500);
        Scene gameScene = new Scene(root, 550,500);
        root.getChildren().addAll(mainBox);
        showScene(gameScene);
    }

    public void draw()
    {

    }

    public void showScene(Scene inScene)
    {
        primaryStage.setScene(inScene);
        primaryStage.show();
    }
}