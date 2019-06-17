package it.xoffset.utils;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


public class ConfigManager {
    private Plugin p;
    private FileConfiguration fileConfiguration;
    private File file;

    public ConfigManager(Plugin p){
        this.p = p;
        fileConfiguration = p.getConfig();
        fileConfiguration.options().copyDefaults(true);
        file = new File(p.getDataFolder(),"config.yml");
        save();
    }

    public void save(){
        try{
            fileConfiguration.save(file);
        }catch(Exception e){}
    }

    public FileConfiguration getConfig(){
        return fileConfiguration;
    }

    public void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}