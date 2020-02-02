package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.StringTokenizer;

public class OptionsController {

    @FXML private VBox optionsWindow;
    @FXML private AnchorPane gameWindow;
    @FXML private CheckBox singlePlayerButton, multiPlayerButton;
    @FXML private ComboBox rozmiarCombo, colorCombo;
    @FXML private Slider clock;

    private Options options = new Options("WHITE", "RED",8,1,"Multicd Player");

    @FXML
    public void loadGame() throws IOException {
        int tile;
        if (options.size == 8) tile = 68;
        else tile = 55;
        Checkers checkers = new Checkers(tile, options.size,options.color1,options.color2);
        gameWindow = FXMLLoader.load(getClass().getResource("game.fxml"));
        gameWindow.getChildren().add(checkers.createContent());
        optionsWindow.getChildren().setAll(gameWindow);
        DownloadTimer timer = new DownloadTimer(options.time, 0);
        timer.start();
    }

    @FXML
    private void handleSingleplayerBox() {
        if (singlePlayerButton.isSelected()) {
            multiPlayerButton.setSelected(false);
        }
        //System.out.println("SinglePlayer");
        options.setGameMode("SinglePlayer");
    }

    @FXML
    private void handleMultiplayerBox() {
        if (multiPlayerButton.isSelected()) {
            singlePlayerButton.setSelected(false);
        }
        //System.out.println("MultiPlayer");
        options.setGameMode("MultiPlayer");
    }

    @FXML
    public void board_size() {
        String size = rozmiarCombo.getSelectionModel().getSelectedItem().toString();
        int board_size = Integer.parseInt(String.valueOf(size.charAt(0)));
        if (board_size == 1) board_size = 10;
        //System.out.println(board_size);
        options.setSize(board_size);
    }

    @FXML
    public void colors() {
        String colors = colorCombo.getSelectionModel().getSelectedItem().toString();

        // Tworzymy obiekt StringTokenizer
        StringTokenizer stringTokenizer = new StringTokenizer(colors, " x ");
        while (stringTokenizer.hasMoreTokens()) {
            String color1 = stringTokenizer.nextToken();
            String color2 = stringTokenizer.nextToken();
            System.out.println(color1);
            System.out.println(color2);
            options.setColor1(color1);
            options.setColor2(color2);
        }
    }

    @FXML
    public void timer() {
        int time = (int)clock.getValue();
        //System.out.println(time);
        options.setTime(time);
    }
}

class Options{
    String color1;
    String color2;
    int size;
    int time;
    String gameMode;

    public Options(String c1, String c2, int s, int t, String g){
        this.color1 = c1;
        this.color2 = c2;
        this.size = s;
        this.time = t;
        this.gameMode = g;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getSize() {
        return size;
    }
}

