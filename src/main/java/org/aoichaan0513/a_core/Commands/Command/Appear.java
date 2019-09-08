package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Appear extends ICommand {

    public Appear(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (MainAPI.isAdmin(sp)) {
            runCommand(sp, cmd, label, args);
            return;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
        return;
    }

    @Override
    public void onBlockCommand(BlockCommandSender bs, Command cmd, String label, String[] args) {
        runCommand(bs, cmd, label, args);
        return;
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender cs, Command cmd, String label, String[] args) {
        runCommand(cs, cmd, label, args);
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

    private void runCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player sp = (Player) sender;

            final String type = "表示";

            if (args.length > 0) {
                for (String name : args) {
                    if (Bukkit.getPlayerExact(name) != null) {
                        Player p = Bukkit.getPlayerExact(name);

                        if (MainAPI.isHidePlayer(p)) {
                            MainAPI.removeHidePlayer(p);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたを" + ChatColor.UNDERLINE + type + ChatColor.RESET + ChatColor.GRAY + "しました。");
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + p.getName() + "を" + ChatColor.GOLD + ChatColor.UNDERLINE + type + ChatColor.RESET + ChatColor.YELLOW + "しました。");
                            continue;
                        } else {
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + p.getName() + "はすでに" + type + "されています。");
                            continue;
                        }
                    } else {
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                        continue;
                    }
                }
                return;
            } else {
                if (MainAPI.isHidePlayer(sp)) {
                    MainAPI.removeHidePlayer(sp);
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたを" + ChatColor.GOLD + ChatColor.UNDERLINE + type + ChatColor.RESET + ChatColor.YELLOW + "しました。");
                    return;
                } else {
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "あなたはすでに" + type + "されています。");
                    return;
                }
            }
        } else {
            final String type = "表示";

            if (args.length > 0) {
                for (String name : args) {
                    if (Bukkit.getPlayerExact(name) != null) {
                        Player p = Bukkit.getPlayerExact(name);

                        if (MainAPI.isHidePlayer(p)) {
                            MainAPI.removeHidePlayer(p);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたを" + ChatColor.UNDERLINE + type + ChatColor.RESET + ChatColor.GRAY + "しました。");
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + p.getName() + "を" + ChatColor.GOLD + ChatColor.UNDERLINE + type + ChatColor.RESET + ChatColor.YELLOW + "しました。");
                            continue;
                        } else {
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + p.getName() + "はすでに" + type + "されています。");
                            continue;
                        }
                    } else {
                        sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                        continue;
                    }
                }
                return;
            }
            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
            return;
        }
    }
}
