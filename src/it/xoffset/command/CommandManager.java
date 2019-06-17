package it.xoffset.command;

import it.xoffset.Main;
import it.xoffset.command.commands.Report;
import it.xoffset.utils.StringUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private static ArrayList<Command> cmds = new ArrayList<>();

    public CommandManager(){
        addCommand(new Report());
    }

    public ArrayList<Command> getCommands(){
        return cmds;
    }

    public void addCommand(Command c){
        this.cmds.add(c);
        Main.getInstance().getCommand(c.getCMD()).setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String arg, String[] args) {
                cmds.stream().forEach(c -> {
                    try {
                        if(cmd.getName().equalsIgnoreCase(c.getCMD())){
                            if(!sender.hasPermission(c.getPermission())){
                                sender.sendMessage(StringUtils.translate(Main.getConfiguration().getString("error-perms")));
                                return;
                            }

                            int realArgs = c.getSyntax().split(" ").length-1;
                            if(args.length < realArgs){
                                sender.sendMessage(StringUtils.translate(Main.getConfiguration().getString("error-syntax").replace("%s",c.getSyntax())));
                                return;
                            }

                            c.execute(sender,args);
                        }
                    } catch (IllegalArgumentException exc) { }
                });
        return false;
    }
}
