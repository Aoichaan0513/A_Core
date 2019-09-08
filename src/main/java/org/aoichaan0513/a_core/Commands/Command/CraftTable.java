package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CraftTable extends ICommand {

    public CraftTable(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (MainAPI.isAdmin(sp) || sp.hasPermission("a_core.crafttable")) {
            sp.openWorkbench(null, true);
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + "作業台を開きました。");
            return;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
        return;
    }

    @Override
    public void onBlockCommand(BlockCommandSender bs, Command cmd, String label, String[] args) {
        bs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PLAYER.getMessage());
        return;
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender cs, Command cmd, String label, String[] args) {
        cs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PLAYER.getMessage());
        return;
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
