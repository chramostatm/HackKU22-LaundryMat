package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        Graphics graphics = new Graphics(primaryStage);
        Parent root = null;
        try
        {
            root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        }catch (Exception e)
        {
            System.out.println("Error loading fxml file");
        }

        Scene mainMenu = new Scene(root);
        mainMenu.getStylesheets().add(getClass().getResource("../stylesheets/mainMenu.css").toExternalForm());

        MenuController.initialize(graphics, mainMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
