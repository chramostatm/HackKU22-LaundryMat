package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.crypto.Mac;
import java.io.File;
import java.util.stream.Collectors;


public class Graphics
{
    Stage primaryStage;
    Pane gamePane;
    GameEngine engine;
    Image washer = new Image(new File("src/images/washing-machine.jpg").toURI().toString());

    //Image wall = new Image(new File("src/images/wall.jpg").toURI().toString());
    Image customer = new Image(new File("src/images/customer.jpg").toURI().toString());

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
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(upgrade, repair, close);

        repair.setOnAction(event ->
        {
            engine.tiles.stream().forEach(outerList -> outerList.stream().filter(tile -> !(tile instanceof Machine)).
                    collect(Collectors.toList()).stream().filter(e -> !((Machine)e).isSpecialTile()));
            buttonBox.setVisible(false);
        });
        close.setOnAction(event ->
        {
            buttonBox.setVisible(false);
        });
        upgrade.setOnAction(event ->
        {
            buttonBox.setVisible(false);
        });

        gamePane.setOnMouseMoved(event ->
        {
            if(!buttonBox.isVisible())
            {
                try
                {
                    ((Machine)engine.tiles.get((int) Math.floor(event.getY()/75))
                            .get((int) Math.floor(event.getX()/75))).setSpecialTile(true);
                }catch (Exception e){}

            }
        });

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(buttonBox, gamePane);

        Scene gameScene = new Scene(root, 900,750);

        root.getChildren().addAll(mainBox);
        showScene(gameScene);
    }

    public void draw()
    {
        gamePane.getChildren().clear();
        for (int i=0; i<engine.tiles.size(); i++) {
            for (int j=0; j<engine.tiles.get(i).size(); j++)
            {
                Tile t = engine.tiles.get(i).get(j);

                Node currentNode;

                if(t instanceof Machine)
                {
                    currentNode = new ImageView(washer);
                    ((ImageView) currentNode).setFitHeight(75);
                    ((ImageView) currentNode).setFitWidth(75);
                    ((ImageView) currentNode).setPreserveRatio(true);
                    ((ImageView) currentNode).setX(j*75);
                    ((ImageView) currentNode).setY(i*75);
                }
                else if(t instanceof Wall)
                {
                    currentNode = new Rectangle(j*75, i*75, 75, 75);
                    ((Rectangle) currentNode).setFill(Color.DARKGRAY);
                }else
                {
                    currentNode = new Rectangle(j*75, i*75, 75, 75);
                    ((Rectangle) currentNode).setFill(Color.WHITE);
                }
                gamePane.getChildren().add(currentNode);
            }
        }
        //draw customer
        engine.customers.forEach(e -> gamePane.getChildren().add(
                new Circle(e.getLocX(), e.getLocY(), 37.5F)
        ));

    }

    public void showScene(Scene inScene)
    {
        primaryStage.setScene(inScene);
        primaryStage.show();
    }
}