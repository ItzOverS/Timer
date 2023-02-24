package me.overlight.timer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerTimer {
    private final String name;
    private int sec, min, hour, day, month, year;
    private Player who;
    private int taskID;
    private TimerStats stats;

    public PlayerTimer(Player who) {
        this.who = who;
        name = who.getName();
    }

    public PlayerTimer(String who, int year, int month, int day, int hour, int min, int sec) {
        this.who = Bukkit.getPlayer(who);
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
        this.name = who;
    }

    public void start() {
        stats = TimerStats.Active;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Timer.INS, () -> {
            if (stats == TimerStats.Active) {
                sec++;
            }

            while (sec >= 60) {
                sec -= 60;
                min += 1;
            }
            while (min >= 60) {
                min -= 60;
                hour += 1;
            }
            while (hour >= 24) {
                hour -= 24;
                day += 1;
            }
            while (day >= 31) {
                day -= 30;
                month += 1;
            }
            while (month >= 12) {
                month -= 12;
                year += 1;
            }
            if (who != null) {
                if (who.isOnline())
                    who.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatColor.GREEN + "" + getTime()).create());
            } else {
                who = Bukkit.getPlayer(name);
            }
        }, 20, 20);
    }

    public boolean isStarted() {
        return stats == TimerStats.Active;
    }

    public boolean isPaused() {
        return stats == TimerStats.Paused;
    }

    public void pause() {
        stats = TimerStats.Paused;
    }

    public void resume() {
        if (!isPaused()) return;
        stats = TimerStats.Active;
    }

    public TimerStats getStats() {
        return this.stats;
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
        stats = TimerStats.UnActive;
        sec = 0;
        min = 0;
        hour = 0;
        day = 0;
        month = 0;
        year = 0;
    }

    public String getTime() {
        return year + "." + month + "." + day + " " + hour + ":" + min + ":" + sec;
    }

    public enum TimerStats {
        Paused,
        Active,
        UnActive
    }
}
