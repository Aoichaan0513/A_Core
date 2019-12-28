package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.bukkit.ChatColor;
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
        if (label.equalsIgnoreCase("time")) {
            if (MainAPI.isAdmin(sp)) {
                if (args.length != 0) {
                    try {
                        long l = Long.parseLong(args[0]);
                        sp.getWorld().setTime(l);

                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + sp.getWorld().getName() + ChatColor.YELLOW + " の時間を" + ChatColor.GOLD + ChatColor.UNDERLINE + sp.getWorld().getTime() + "秒" + ChatColor.RESET + ChatColor.YELLOW + "に設定しました。");
                        return;
                    } catch (NumberFormatException e) {
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.NUMBER.getMessage());
                        return;
                    }
                }
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
            return;
        } else if (label.equalsIgnoreCase("morning")) {
            if (MainAPI.isAdmin(sp)) {
                sp.getWorld().setTime(0);
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + sp.getWorld().getName() + ChatColor.YELLOW + " の時間を" + ChatColor.GOLD + ChatColor.UNDERLINE + "朝" + ChatColor.RESET + ChatColor.YELLOW + "に設定しました。");
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
            return;
        } else if (label.equalsIgnoreCase("day")) {
            if (MainAPI.isAdmin(sp)) {
                sp.getWorld().setTime(6000);
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + sp.getWorld().getName() + ChatColor.YELLOW + " の時間を" + ChatColor.GOLD + ChatColor.UNDERLINE + "昼" + ChatColor.RESET + ChatColor.YELLOW + "に設定しました。");
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
            return;
        } else if (label.equalsIgnoreCase("sunset")) {
            if (MainAPI.isAdmin(sp)) {
                sp.getWorld().setTime(12500);
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + sp.getWorld().getName() + ChatColor.YELLOW + " の時間を" + ChatColor.GOLD + ChatColor.UNDERLINE + "夕方" + ChatColor.RESET + ChatColor.YELLOW + "に設定しました。");
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
            return;
        } else if (label.equalsIgnoreCase("night")) {
            if (MainAPI.isAdmin(sp)) {
                sp.getWorld().setTime(14000);
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + sp.getWorld().getName() + ChatColor.YELLOW + " の時間を" + ChatColor.GOLD + ChatColor.UNDERLINE + "夜" + ChatColor.RESET + ChatColor.YELLOW + "に設定しました。");
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
            return;
        } else if (label.equalsIgnoreCase("midnight")) {
            if (MainAPI.isAdmin(sp)) {
                sp.getWorld().setTime(18000);
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + sp.getWorld().getName() + ChatColor.YELLOW + " の時間を" + ChatColor.GOLD + ChatColor.UNDERLINE + "夜中" + ChatColor.RESET + ChatColor.YELLOW + "に設定しました。");
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
            return;
        }
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
