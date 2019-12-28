package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Send extends ICommand {

    public Send(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player != null) {
                if (args.length != 1) {
                    if (args[1].equalsIgnoreCase("title")) {
                        if (args.length > 2) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 2; i < args.length; i++)
                                stringBuffer.append(args[i] + " ");

                            player.sendTitle(MainAPI.getText(stringBuffer.toString().trim()), "", 10, 70, 20);
                            return;
                        }
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                        return;
                    } else if (args[1].equalsIgnoreCase("subtitle")) {
                        if (args.length > 2) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 2; i < args.length; i++)
                                stringBuffer.append(args[i] + " ");

                            player.sendTitle("", MainAPI.getText(stringBuffer.toString().trim()), 10, 70, 20);
                            return;
                        }
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                        return;
                    } else if (args[1].equalsIgnoreCase("actionbar")) {
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "実装中");
                        return;
                    }
                }
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + args[0] + "はオフラインです。");
            return;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
        return;
    }

    @Override
    public void onBlockCommand(BlockCommandSender bs, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player != null) {
                if (args.length != 1) {
                    if (args[1].equalsIgnoreCase("title")) {
                        if (args.length > 2) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 2; i < args.length; i++)
                                stringBuffer.append(args[i] + " ");

                            player.sendTitle(MainAPI.getText(stringBuffer.toString().trim()), "", 10, 70, 20);
                            return;
                        }
                        bs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                        return;
                    } else if (args[1].equalsIgnoreCase("subtitle")) {
                        if (args.length > 2) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 2; i < args.length; i++)
                                stringBuffer.append(args[i] + " ");

                            player.sendTitle("", MainAPI.getText(stringBuffer.toString().trim()), 10, 70, 20);
                            return;
                        }
                        bs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                        return;
                    } else if (args[1].equalsIgnoreCase("actionbar")) {
                        bs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "実装中");
                        return;
                    }
                }
                bs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            }
            bs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + args[0] + "はオフラインです。");
            return;
        }
        bs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
        return;
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
