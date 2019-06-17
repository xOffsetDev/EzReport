package it.xoffset.utils;

import it.xoffset.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class EventManager implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Main.getCommadManager().getCommands().stream().forEach(command -> command.call(e));
    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent e){
        Main.getCommadManager().getCommands().stream().forEach(command -> command.call(e));
    }

}
