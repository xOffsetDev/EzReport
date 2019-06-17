package it.xoffset.command;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;

public abstract class Command {
    private String cmd;
    private String perm;
    private String syntax;

    public Command(String cmd, String perm,String syntax){
        this.cmd = cmd;
        this.perm = perm;
        this.syntax = syntax;
    }

    public String getCMD(){
        return cmd;
    }

    public String getPermission(){
        return perm;
    }

    public String getSyntax() { return syntax; }

    public abstract boolean execute(CommandSender sender, String[] args);

    public abstract void call(Event e);
}
