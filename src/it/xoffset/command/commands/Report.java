package it.xoffset.command.commands;

import it.xoffset.Main;
import it.xoffset.command.Command;
import it.xoffset.utils.InventoryUtils;
import it.xoffset.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Report extends Command {
    private HashMap<Integer, ItemStack> items = new HashMap();
    public Report(){
        super("report","ez.report","/report <Username>");
        items.put(3,InventoryUtils.getItem(Material.DIAMOND_SWORD,"§cCombat Hacks"));
        items.put(4,InventoryUtils.getItem(Material.DIAMOND_BOOTS,"§cMovements Hacks"));
        items.put(5,InventoryUtils.getItem(Material.SIGN,"§cInsult"));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        try{
            Player pl = ((Player)sender);
            if(args[0].equalsIgnoreCase("list") && pl.hasPermission("ez.reportview")){
                try{
                    Player p = Bukkit.getPlayer(args[1]);
                    Inventory inventory = InventoryUtils.getInventory("View Report "+p.getPlayer().getName(),54);
                    ConfigurationSection section = Main.getConfiguration().getConfigurationSection("reports."+args[1]);
                    section.getValues(false).keySet().
                            stream().
                            limit(54).
                            sorted(Comparator.reverseOrder()).
                            forEach(i-> inventory.addItem(InventoryUtils.getItem(Material.NAME_TAG,"&7#"+i+" Report",getDescription(args[1],Integer.parseInt(i)))));
                    pl.openInventory(inventory);
                    return false;
                }catch(Exception exc){pl.sendMessage("§cNo Report for that player"); return false;}
            }
            Player p = Bukkit.getPlayer(args[0]);
            Inventory inventory = InventoryUtils.getInventory("Report "+p.getPlayer().getName(),9);
            items.keySet().stream().forEach(i-> inventory.setItem(i,items.get(i)));
            pl.openInventory(inventory);
        }catch(NullPointerException e){
            sender.sendMessage(StringUtils.fromConfig("error-player"));
            return false;
        }
        return false;
    }

    @Override
    public void call(Event e) {
        if(e instanceof InventoryClickEvent){
           InventoryClickEvent ice = (InventoryClickEvent) e;
           if(ice.isLeftClick() && ice.getCurrentItem().getType() != Material.AIR && ice.getCurrentItem() != null && ice.getInventory().getTitle().startsWith("Report") && ice.getSlot() <= 8){
               String username = ice.getInventory().getTitle().split(" ")[1];
               String message = StringUtils.fromConfig("report.success").replace("%for",ice.getCurrentItem().getItemMeta().getDisplayName()).replace("%username",username);
               int index = 0;
               try{
                   index =  Main.getConfiguration().getConfigurationSection("reports."+username).getValues(false).size();
               }catch(Exception exc){}
               Main.getConfiguration().set("reports."+username+"."+index+".by",ice.getWhoClicked().getName());
               Main.getConfiguration().set("reports."+username+"."+index+".for", ChatColor.stripColor(ice.getCurrentItem().getItemMeta().getDisplayName()));
               DateFormat data = new SimpleDateFormat("dd/MM/yyyy HH:mm");
               Main.getConfiguration().set("reports."+username+"."+index+".time", data.format(new Date()));
               Main.getConfigMananger().save();
               ice.getWhoClicked().sendMessage(message);
               ice.getWhoClicked().closeInventory();
               ice.setCancelled(true);
           }else if(ice.getInventory().getTitle().startsWith("View Report")  && ice.getSlot() <=54){
               ice.setCancelled(true);
           }
        }
    }

    public String[] getDescription(String username,int i){
        String by = Main.getConfiguration().getString("reports."+username+"."+i+".by");
        String reason = Main.getConfiguration().getString("reports."+username+"."+i+".for");
        String time = Main.getConfiguration().getString("reports."+username+"."+i+".time");
        return new String[]{"§7By: §c"+by,"§7Reason: §c"+reason,"§7Time: §c"+time};
    }
}
