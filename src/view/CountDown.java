package view;

import controller.StaticPieces;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class CountDown {
    private int minute, second;
    private final Timer timer;
    private Boolean pause;
    private Boolean fullTime;
    private FullTimeListener listener;
    private JLabel label;

    public CountDown() {
        this.pause = true;
        this.fullTime = false;
        this.timer = new Timer();
    }

    public void start(JLabel label, final FullTimeListener listener) {
        this.listener = listener;
        this.label = label;
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        }, 0, 1000);
    }

    public void setTime(int minute, int second) {
        this.minute = minute;
        this.second = second;
        this.pause = true;
        this.fullTime = false;
    }

    private void updateTime() {
        if (!pause && !fullTime) {
            if (this.minute == 0 && this.second == 0) {
                this.fullTime = true;
                listener.onFullTime();
            } else {
                if (this.second == 0) {
                    this.minute--;
                    this.second = 59;
                } else {
                    this.second--;
                }
                String temp1 = (this.minute < 10) ? "0" : "";
                String temp2 = (this.second < 10) ? "0" : "";
                label.setText(temp1 + this.minute + " : " + temp2 + this.second);
            }
        }
    }

    public void setFullTime(Boolean fullTime) {
        this.fullTime = fullTime;
    }

    public Boolean getFullTime() {
        return fullTime;
    }

    public void stop() {
        this.pause = true;
        this.minute = StaticPieces.getMinute();
        this.second = StaticPieces.getSecond();
        String temp1 = (this.minute < 10) ? "0" : "";
        String temp2 = (this.second < 10) ? "0" : "";
        label.setText(temp1 + this.minute + " : " + temp2 + this.second);
    }

    public void resume() {
        this.pause = false;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public interface FullTimeListener {
        void onFullTime();
    }
}
