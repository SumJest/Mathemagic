package ru.romaaaka.mathemagic.utils;
import android.os.CountDownTimer;

public class TimerHelper {
    private CountDownTimer timer;
    private TimerListener listener;

    public TimerHelper(int totalTimeInSeconds, TimerListener listener) {
        this.listener = listener;
        this.timer = new CountDownTimer(totalTimeInSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                listener.onTick((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                listener.onFinish();
            }
        };
    }

    public void start() {
        timer.start();
    }

    public void cancel() {
        timer.cancel();
    }

    public interface TimerListener {
        void onTick(int secondsLeft);
        void onFinish();
    }
}

