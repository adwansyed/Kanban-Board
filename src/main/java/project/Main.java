package project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Author(s): Adwan Syed, Andrew Selvarajah, Ahmed Naeem, Yi Guo
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Server server = new Server(2222);
        server.handleRequest();
        //call splash
        IntroSplash splash = new IntroSplash(5000);
        splash.showSplash();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root, 900, 650));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }


    public static void main(String[] args) {
        launch(args);
    }
}