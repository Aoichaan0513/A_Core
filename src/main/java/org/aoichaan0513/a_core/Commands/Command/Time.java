package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.Commands.ICommand;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Time extends ICommand {

    public Time(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("day")) {
            if (args.length != 0) {
                try {
                    long l = Long.parseLong(args[0]);
                    sp.getWorld().setTime(l);

                } catch (NumberFormatException e) {

                }
            }
        } else if (label.equalsIgnoreCase("night")) {

        }
    }

    @Override
    public void onBlockCommand(BlockCommandSender bs, Command cmd, String label, String[] args) {

    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender cs, Command cmd, String label, String[] args) {

    }

    @Override
    public List<String> onPlayerTabComplete(Player sp, Command cmd, String alias, String[] args) {
        return null;
    }

    @Override
    public List<String> onBlockTabComplete(BlockCommandSender bs, Command cmd, String alias, String[] args) {
        return null;
    }

    @Override
    public List<String> onConsoleTabComplete(ConsoleCommandSender cs, Command cmd, String alias, String[] args) {
        return null;
    }
}
