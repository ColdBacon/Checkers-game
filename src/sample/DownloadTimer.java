package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class DownloadTimer{
    private int minutes;
    private int seconds;
    private Timer innerTimer = new Timer();
    private TimerTask innerTask;
    private boolean isActive;
    private String S ="";

    public DownloadTimer(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.isActive = false;
    }

    //Label label = new Label (minutes + " : " + seconds);

    public void start() {
        innerTask = new TimerTask() {
            @Override
            public void run() {
                isActive = true;
                //System.out.println(getTime());
                if (seconds == 0 && minutes > 0){
                    minutes -= 1;
                    seconds = 59; }
                else if (seconds == 0 && minutes == 0){
                    isActive = false;
                    innerTimer.cancel();
                    innerTimer.purge();
                    System.out.println("TIME IS OVER");
                }
                else {
                    seconds -= 1; }
               // label.setText(minutes + " : " + seconds);
                System.out.println(minutes + " : " + seconds);
             }
        };
        innerTimer.scheduleAtFixedRate(innerTask, 0, 1000);
    }

    private void endTime(){
        Boolean answer = ConfirmBox.display("THE GAME IS OVER", "THE TIME IS OVER!", "NEW GAME", "EXIT");
        System.out.println(answer);
        if (!answer){
            Platform.exit();
            System.exit(0);
        }
    }
}