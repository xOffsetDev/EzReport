package it.xoffset;

import it.xoffset.command.CommandManager;
import it.xoffset.utils.ConfigManager;
import it.xoffset.utils.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main main;
    private static CommandManager commadManager;
    private static ConfigManager configMananger;
    private static EventManager eventManager;


    @Override
    public void onEnable() {
        main = this;
        this.configMananger = new ConfigManager(this);
        this.commadManager = new CommandManager();
        this.eventManager = new EventManager();

        initConfig();
        Bukkit.getPluginManager().registerEvents(eventManager,this);
    }


    public static CommandManager getCommadManager(){
        return commadManager;
    }

    public static ConfigManager getConfigMananger(){
        return configMananger;
    }

    public static Configuration getConfiguration(){
        return configMananger.getConfig();
    }

    public static Main getInstance(){
        return main;
    }

    private void initConfig(){
        getConfiguration().addDefault("error-perms","&cYou have not permission to execute this command!");
        getConfiguration().addDefault("error-syntax","&cSyntax Error!\nUsage: %s");

        getConfiguration().addDefault("error-player","&cThat player is not online");
        getConfiguration().addDefault("report.success","&7You reported &c%username &7for &c%for");

        getConfigMananger().save();
    }
}
