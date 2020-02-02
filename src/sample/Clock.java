package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Clock extends Pane {

    private Timeline animation;
    private int minutes;
    private int seconds;
    private String S = "";
    private int Xvalue;
    private int Yvalue;

    Label label = new Label(minutes + " : " + seconds);

    Clock(int min, int sec, int x, int y){

        this.minutes = min;
        this.seconds = sec;
        this.Xvalue = x;
        this.Yvalue = y;

        label.setTranslateX(Xvalue);
        label.setTranslateY(Yvalue);
        label.setMinWidth(60);
        label.setMinHeight(30);
        label.setStyle("-fx-background-color: WHITE; -fx-font-size: 25");

        getChildren().add(label);
        animation = new Timeline(new KeyFrame(Duration.seconds(1), e->timeLabel()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void timeLabel(){
        if (seconds == 0 && minutes > 0){
            minutes -= 1;
            seconds = 59; }
        else if (seconds == 0 && minutes == 0){
            System.out.println("TIME IS OVER");
            animation.stop();
            endTime();
        }
        else {
            seconds -= 1;
        }
        if (seconds < 10) label.setText(minutes + " : 0" + seconds);
        else label.setText(minutes + " : " + seconds);
    }

    private void endTime(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Time is over!");
        alert.setHeaderText(null);
        alert.setContentText("The game is over! Click OK to exit.");

        alert.setOnHidden(evt -> Platform.exit());

        alert.show();
    }

    /*
        private void timeLabel(){

        if(time > 0){
            time--;
            S = time + "";
            label.setText(S);
        }
        if (time == 0){
            S = "GAME OVER";
            label.setText(S);
        }
    }
     */
}
