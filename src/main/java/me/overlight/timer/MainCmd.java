package me.overlight.timer;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCmd
        implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 1) {
            switch (args[0]) {
                case "start":
                    if (!TimerManager.isTimerCreated(sender.getName())) {
                        TimerManager.setTimer(sender.getName(), new PlayerTimer((Player) sender));
                        TimerManager.getTimer(sender.getName()).start();
                        sender.sendMessage("new timer created for you!");
                    } else {
                        sender.sendMessage("An active timer currently exists");
                    }
                    break;
                case "stop":
                    if (TimerManager.isTimerCreated(sender.getName())) {
                        TimerManager.getTimer(sender.getName()).stop();
                        TimerManager.removeTimer(sender.getName());
                        sender.sendMessage("Your timer has stopped");
                    } else {
                        sender.sendMessage("You has no timer to stop");
                    }
                    break;
                case "pause":
                    if (TimerManager.isTimerCreated(sender.getName())
                            && !TimerManager.getTimer(sender.getName()).isPaused()) {
                        TimerManager.getTimer(sender.getName()).pause();
                        sender.sendMessage("Timer simplify paused");
                    } else {
                        if (!TimerManager.isTimerCreated(sender.getName()))
                            sender.sendMessage("You has no timer to pause");
                        else
                            sender.sendMessage("Timer is already paused");
                    }
                    break;
                case "resume":
                    if (TimerManager.isTimerCreated(sender.getName())
                            && TimerManager.getTimer(sender.getName()).isPaused()) {
                        TimerManager.getTimer(sender.getName()).resume();
                        sender.sendMessage("Timer simplify resumed");
                    } else {
                        if (!TimerManager.isTimerCreated(sender.getName()))
                            sender.sendMessage("You has no timer to resume");
                        else
                            sender.sendMessage("Timer is already active");
                    }
                    break;
            }
        }
        return false;
    }
}
