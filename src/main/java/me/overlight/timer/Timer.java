package me.overlight.timer;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Timer extends JavaPlugin {

    public static Timer INS;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INS = this;
        getServer().getPluginCommand("timer").setExecutor(new MainCmd());

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File("plugins\\Timer\\timers.yml"));
        for (String name : yml.getKeys(false)) {
            String value = yml.getString(name),
                    date = value.split(" ")[0],
                    time = value.split(" ")[1];
            TimerManager.setTimer(name, new PlayerTimer(name, Integer.parseInt(date.split("\\.")[0]), Integer.parseInt(date.split("\\.")[1]),
                    Integer.parseInt(date.split("\\.")[2]), Integer.parseInt(time.split(":")[0]), Integer.parseInt(time.split(":")[1]),
                    Integer.parseInt(time.split(":")[2])));
            TimerManager.getTimer(name).start();
            TimerManager.getTimer(name).pause();
        }
    }

    @Override
    public void onDisable() {
        YamlConfiguration config = new YamlConfiguration();
        for (String name : TimerManager.getKeySet()) config.set(name, TimerManager.getTimer(name).getTime());
        if (!new File("plugins\\Timer").exists())
            new File("plugins\\Timer").mkdirs();
        try {
            config.save(new File("plugins\\Timer\\timers.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
