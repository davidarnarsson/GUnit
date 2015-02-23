package edu.chl.gunit.core.gamification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 23.2.2015.
 */
public class Runner implements Runnable {

    public interface OnCompleteListener {
        public void onComplete();
    }

    private final List<OnCompleteListener> listeners = new ArrayList<>();

    public void addCompleteListener(OnCompleteListener l) {
        listeners.add(l);
    }

    public Runner() {

    }

    @Override
    public void run() {

    }
}
