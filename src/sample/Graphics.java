package sample;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.File;
import java.util.stream.Collectors;


public class Graphics
{
    Stage primaryStage;
    Pane gamePane;
    GameEngine engine;
    Image washer = new Image(new File("src/images/washingMachine.png").toURI().toString());
    Image brokenWasher = new Image(new File("src/images/brokenMachine.png").toURI().toString());
    Menu balanceMenu;
    Menu debtMenu;

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

        //brokenWasher.se

        // Create the menu bar.
        MenuBar menuBar = new MenuBar();

        // Create the File menu.
        Menu fileMenu = new Menu("_File");
        MenuItem aboutItem = new MenuItem("_About");
        MenuItem exitItem = new MenuItem("E_xit");
        fileMenu.getItems().addAll(aboutItem, exitItem);

        // Create the Dialog scene to display for the About menu item.
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("About Sud Tycoon!");
        ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText("This JavaFX game is a submission for the Education Track of the " +
                "2022 HackKU (https://hackku.org) competition in Lawrence, KS. The group members " +
                "are all UNK students:\n\tTrenton Chramosta\n\tBlake Meyer\n\tJohn Reagan\n\t" +
                "Matthew Rokusek\n\tJustin Tilkens\n");
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);

        // Register an event handler for the about menu item.
        aboutItem.setOnAction(event ->
        {
            dialog.showAndWait();
        });

        // Register an event handler for the exit item.
        exitItem.setOnAction(event ->
        {
           primaryStage.close();
        });

        // Create an empty Label to display the owner's balance.
        balanceMenu = new Menu();
        debtMenu = new Menu();

        // Add the File menu to the menu bar.
        menuBar.getMenus().addAll(fileMenu, balanceMenu, debtMenu);

//        Button upgrade = new Button();
//        upgrade.setText("Upgrade");

        Button repair = new Button();
        repair.setText("Repair");

        Button close = new Button();
        close.setText("Close");

        HBox buttonBox = new HBox();
        buttonBox.setVisible(false);
        buttonBox.setAlignment(Pos.CENTER);
//        buttonBox.getChildren().addAll(upgrade, repair, close);
        buttonBox.getChildren().addAll(repair, close);
        HBox bigButtonBox = new HBox();
        bigButtonBox.setAlignment(Pos.CENTER);
        Button pay50 = new Button();
        pay50.setText("Pay $50 off loan");
        pay50.setOnAction(event ->
        {
            if (engine.balanceSheet.currentCapital >= 50)
            {
                engine.balanceSheet.currentCapital -=50;
                engine.balanceSheet.debt -=50;
            }
        });

        Button advertise = new Button();
        advertise.setText("Advertise!");
        advertise.setOnAction(event ->
        {
            GameController.gameEngine.speedModifier+=0.1F;

        });
        bigButtonBox.getChildren().addAll(buttonBox, pay50, advertise);

        repair.setOnAction(event ->
        {
            engine.repair();
            buttonBox.setVisible(false);
        });
        close.setOnAction(event ->
        {
            buttonBox.setVisible(false);
        });
//        upgrade.setOnAction(event ->
//        {
//            buttonBox.setVisible(false);
//        });

        gamePane.setOnMouseMoved(event ->
        {
            if(!buttonBox.isVisible())
            {
                try
                {
                    engine.tiles.forEach(e -> e.stream().filter(tile -> tile instanceof Machine).
                            collect(Collectors.toList()).forEach(machine -> ((Machine)machine).setSpecialTile(false)));
                    ((Machine)engine.tiles.get((int) Math.floor(event.getY()/75))
                            .get((int) Math.floor(event.getX()/75))).setSpecialTile(true);

                    ((Machine)engine.tiles.get((int) Math.floor(event.getY()/75))
                            .get((int) Math.floor(event.getX()/75))).setSpecialTile(true);
                }catch (Exception ignored)
                {}
            }
        });

        gamePane.setOnMouseClicked(event ->
        {
            try
            {
                engine.tiles.forEach(e -> e.stream().filter(tile -> tile instanceof Machine).
                        collect(Collectors.toList()).forEach(machine -> ((Machine)machine).setSpecialTile(false)));
                ((Machine)engine.tiles.get((int) Math.floor(event.getY()/75))
                        .get((int) Math.floor(event.getX()/75))).setSpecialTile(true);

                ((Machine)engine.tiles.get((int) Math.floor(event.getY()/75))
                        .get((int) Math.floor(event.getX()/75))).setSpecialTile(true);
                buttonBox.setVisible(true);
            }catch (Exception ignored)
            {}
        });

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(menuBar, bigButtonBox, gamePane);
        root.getChildren().addAll(mainBox);

        // Create a Scene and display it.
        Scene scene = new Scene(root, 900,750);
        primaryStage.setScene(scene);
        primaryStage.show();
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
                    Image machineImage;
                    if (((Machine)t).isBroken())
                    {
                        machineImage = brokenWasher;
                    } else
                    {
                        machineImage = washer;
                    }

                    if(((Machine)t).isSpecialTile())
                    {
                        currentNode = new ImageView(machineImage);
                        ((ImageView) currentNode).setFitHeight(95);
                        ((ImageView) currentNode).setFitWidth(95);
                        ((ImageView) currentNode).setPreserveRatio(true);
                        ((ImageView) currentNode).setX((j-1)*75 +65);
                        ((ImageView) currentNode).setY((i-1)*75 +65);
                    }else
                    {
                        currentNode = new ImageView(machineImage);
                        ((ImageView) currentNode).setFitHeight(75);
                        ((ImageView) currentNode).setFitWidth(75);
                        ((ImageView) currentNode).setPreserveRatio(true);
                        ((ImageView) currentNode).setX(j * 75);
                        ((ImageView) currentNode).setY(i * 75);
                    }
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

                if (t instanceof Machine && !((Machine)t).getAvailable())
                {
                    Arc waitDisplay = new Arc(j*75+32.5,i*75+37.5,25,25,90,(((Machine)t).timeUntilComplete));
                    waitDisplay.setType(ArcType.ROUND);
                    waitDisplay.setFill(Color.BLUE);
                    gamePane.getChildren().add(waitDisplay);
                }
            }
        }
        //draw customer
        engine.customers.forEach(e -> gamePane.getChildren().add(
                new Circle(e.getLocX()*75+37.5F, e.getLocY()*75+37.5F, 37.5F)
        ));

        balanceMenu.setText(String.format("balance: $%.2f", engine.balanceSheet.currentCapital));
        debtMenu.setText(String.format("debt: $%.2f", engine.balanceSheet.debt));
    }
    public void showScene(Scene inScene)
    {
        primaryStage.setScene(inScene);
        primaryStage.show();
    }
}