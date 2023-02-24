package me.overlight.timer;

import java.util.HashMap;
import java.util.Set;

public class TimerManager {
    private static final HashMap<String, PlayerTimer> timer = new HashMap<>();

    public static PlayerTimer getTimer(String forWho) {
        return timer.get(forWho);
    }

    public static void setTimer(String forWho, PlayerTimer timer) {
        TimerManager.timer.put(forWho, timer);
    }

    public static void removeTimer(String forWho) {
        TimerManager.timer.remove(forWho);
    }

    public static boolean isTimerCreated(String forWho) {
        return timer.containsKey(forWho);
    }

    public static Set<String> getKeySet() {
        return timer.keySet();
    }
}
