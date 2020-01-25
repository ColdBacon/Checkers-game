package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {


    /*
    int secondsPassed = 0;
    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
        public void run(){
            secondsPassed++;
            System.out.println("Seconds past: " + secondsPassed);
        }
    };
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CHECKERS");
        primaryStage.setScene(new Scene(root, 600  , 600));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("sample/menu.png"));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e ->
        {
            e.consume();
            closeProgram(primaryStage);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void closeProgram(Stage primaryStage) {
        Boolean answer = ConfirmBox.display("EXIT", "Sure you want to exit?");
        if (answer)
            primaryStage.close();
    }

}