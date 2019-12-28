package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.aoichaan0513.a_core.Inventorys.MainInventory;
import org.aoichaan0513.a_core.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Menu extends ICommand {

    public Menu(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (Main.getMainConfig().getBoolean("menu.enabled", true)) {
            MainInventory.openInventory(sp);
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + ChatColor.UNDERLINE + "メニュー" + ChatColor.RESET + ChatColor.YELLOW + "を開きました。");
            return;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "メニュー機能が無効のため使用できません。\n" +
                MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "管理者にメニュー機能を有効にしてもらってください。");
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
