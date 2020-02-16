package sample;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.StringTokenizer;

public class Controller {

    private static Scene init_scene;

    @FXML
    private Button exitButton;
    @FXML
    private VBox startWindow, optionsWindow;
    @FXML
    private AnchorPane gameWindow;

    @FXML
    private void closeButtonAction(ActionEvent actionEvent) {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void loadSecond(ActionEvent actionEvent) throws IOException {
        optionsWindow = FXMLLoader.load(getClass().getResource("options.fxml"));
        startWindow.getChildren().setAll(optionsWindow);
    }

    @FXML
    private void reloadStart(ActionEvent actionEvent) throws IOException{
        startWindow = FXMLLoader.load(getClass().getResource("sample.fxml"));
        gameWindow.getChildren().setAll(startWindow);
    }

    @FXML
    public void loadGame(ActionEvent actionEvent) throws IOException {
        Checkers checkers = new Checkers(68, 8, "WHITE", "RED",false);
        Clock clock = new Clock(2,0,330,10);
        gameWindow = FXMLLoader.load(getClass().getResource("game.fxml"));
        gameWindow.getChildren().addAll(checkers.createContent(),clock);
        startWindow.getChildren().setAll(gameWindow);
    }

    public static void init(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Controller.class.getResource("sample.fxml"));
        primaryStage.setTitle("CHECKERS");
        init_scene = new Scene(root,700,700);
        //primaryStage.setScene(new Scene(root, 700  , 700));
        primaryStage.setScene(init_scene);
        primaryStage.setResizable(false);

        primaryStage.getIcons().add(new Image("sample/pictures/menu.png"));
        primaryStage.show();
    }

}

