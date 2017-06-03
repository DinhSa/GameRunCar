package com.example.kaios.runcar2;

/**
 * Created by kaios on 6/3/2017.
 */

public class TimeSpeedUp extends Thread {
    private boolean run;
    private int time;
    private int time_delay;

    public TimeSpeedUp() {
        time = 0;
        run = true;
        time_delay = 1000;
    }

    public void run() {
        while (run) {
            try {
                Thread.sleep(time_delay);
                if (time != 0) {
                    time--;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Control.thoat)
                return;
        }
    }

    // -------------------------------
    // Phương thức getTime
    public int getTime() {
        return time;
    }

    // -------------------------------
    // Phương thức setTime
    public void setTime(int time) {
        this.time = time;
    }

    // -------------------------------
}
