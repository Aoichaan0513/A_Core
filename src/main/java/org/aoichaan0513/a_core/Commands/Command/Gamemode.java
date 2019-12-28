package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Gamemode extends ICommand {

    public Gamemode(String name) {
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
        if (MainAPI.isAdmin(sp)) {
            if (alias.equalsIgnoreCase("gamemode") || alias.equalsIgnoreCase("gm")) {
                if (args.length == 1) {
                    if (args[0].length() == 0) {
                        return Arrays.asList("survival", "creative", "adventure", "spectator");
                    } else {
                        if ("survival".startsWith(args[0])) {
                            return Collections.singletonList("survival");
                        } else if ("creative".startsWith(args[0])) {
                            return Collections.singletonList("creative");
                        } else if ("adventure".startsWith(args[0])) {
                            return Collections.singletonList("adventure");
                        } else if ("spectator".startsWith(args[0])) {
                            return Collections.singletonList("spectator");
                        }
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }

    @Override
    public List<String> onBlockTabComplete(BlockCommandSender bs, Command cmd, String alias, String[] args) {
        if (alias.equalsIgnoreCase("gamemode") || alias.equalsIgnoreCase("gm")) {
            if (args.length == 1) {
                if (args[0].length() == 0) {
                    return Arrays.asList("survival", "creative", "adventure", "spectator");
                } else {
                    if ("survival".startsWith(args[0])) {
                        return Collections.singletonList("survival");
                    } else if ("creative".startsWith(args[0])) {
                        return Collections.singletonList("creative");
                    } else if ("adventure".startsWith(args[0])) {
                        return Collections.singletonList("adventure");
                    } else if ("spectator".startsWith(args[0])) {
                        return Collections.singletonList("spectator");
                    }
                }
            }
            return null;
        }
        return null;
    }

    @Override
    public List<String> onConsoleTabComplete(ConsoleCommandSender cs, Command cmd, String alias, String[] args) {
        if (alias.equalsIgnoreCase("gamemode") || alias.equalsIgnoreCase("gm")) {
            if (args.length == 1) {
                if (args[0].length() == 0) {
                    return Arrays.asList("survival", "creative", "adventure", "spectator");
                } else {
                    if ("survival".startsWith(args[0])) {
                        return Collections.singletonList("survival");
                    } else if ("creative".startsWith(args[0])) {
                        return Collections.singletonList("creative");
                    } else if ("adventure".startsWith(args[0])) {
                        return Collections.singletonList("adventure");
                    } else if ("spectator".startsWith(args[0])) {
                        return Collections.singletonList("spectator");
                    }
                }
            }
            return null;
        }
        return null;
    }

    private void runCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player sp = (Player) sender;

            if (label.equalsIgnoreCase("gamemode") || label.equalsIgnoreCase("gm")) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                        final GameMode modeType = MainAPI.Gamemode.SURVIVAL.getGameMode();
                        final String modeName = MainAPI.Gamemode.SURVIVAL.getName();

                        if (args.length > 1) {
                            for (int i = 1; i < args.length; i++) {
                                final String name = args[i];

                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player p = Bukkit.getPlayerExact(name);

                                    p.setGameMode(modeType);
                                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                                    continue;
                                } else {
                                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                                    continue;
                                }
                            }
                            return;
                        } else {
                            sp.setGameMode(modeType);
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたのゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            return;
                        }
                    } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                        final GameMode modeType = MainAPI.Gamemode.CREATIVE.getGameMode();
                        final String modeName = MainAPI.Gamemode.CREATIVE.getName();

                        if (args.length > 1) {
                            for (int i = 1; i < args.length; i++) {
                                final String name = args[i];

                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player p = Bukkit.getPlayerExact(name);

                                    p.setGameMode(modeType);
                                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                                    continue;
                                } else {
                                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                                    continue;
                                }
                            }
                            return;
                        } else {
                            sp.setGameMode(modeType);
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたのゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            return;
                        }
                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                        final GameMode modeType = MainAPI.Gamemode.ADVENTURE.getGameMode();
                        final String modeName = MainAPI.Gamemode.ADVENTURE.getName();

                        if (args.length > 1) {
                            for (int i = 1; i < args.length; i++) {
                                final String name = args[i];

                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player p = Bukkit.getPlayerExact(name);

                                    p.setGameMode(modeType);
                                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                                    continue;
                                } else {
                                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                                    continue;
                                }
                            }
                            return;
                        } else {
                            sp.setGameMode(modeType);
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたのゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            return;
                        }
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                        final GameMode modeType = MainAPI.Gamemode.SPECTATOR.getGameMode();
                        final String modeName = MainAPI.Gamemode.SPECTATOR.getName();

                        if (args.length > 1) {
                            for (int i = 1; i < args.length; i++) {
                                final String name = args[i];

                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player p = Bukkit.getPlayerExact(name);

                                    p.setGameMode(modeType);
                                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                                    continue;
                                } else {
                                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                                    continue;
                                }
                            }
                            return;
                        } else {
                            sp.setGameMode(modeType);
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたのゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            return;
                        }
                    }
                }
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            } else if (label.equalsIgnoreCase("gms") || label.equalsIgnoreCase("gm0")) {
                final GameMode modeType = MainAPI.Gamemode.SURVIVAL.getGameMode();
                final String modeName = MainAPI.Gamemode.SURVIVAL.getName();

                if (args.length > 0) {
                    for (String name : args) {
                        if (Bukkit.getPlayerExact(name) != null) {
                            Player p = Bukkit.getPlayerExact(name);

                            p.setGameMode(modeType);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            continue;
                        } else {
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                            continue;
                        }
                    }
                    return;
                } else {
                    sp.setGameMode(modeType);
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたのゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                    return;
                }
            } else if (label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase("gm1")) {
                final GameMode modeType = MainAPI.Gamemode.CREATIVE.getGameMode();
                final String modeName = MainAPI.Gamemode.CREATIVE.getName();

                if (args.length > 0) {
                    for (String name : args) {
                        if (Bukkit.getPlayerExact(name) != null) {
                            Player p = Bukkit.getPlayerExact(name);

                            p.setGameMode(modeType);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            continue;
                        } else {
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                            continue;
                        }
                    }
                    return;
                } else {
                    sp.setGameMode(modeType);
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたのゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                    return;
                }
            } else if (label.equalsIgnoreCase("gma") || label.equalsIgnoreCase("gm2")) {
                final GameMode modeType = MainAPI.Gamemode.ADVENTURE.getGameMode();
                final String modeName = MainAPI.Gamemode.ADVENTURE.getName();

                if (args.length > 0) {
                    for (String name : args) {
                        if (Bukkit.getPlayerExact(name) != null) {
                            Player p = Bukkit.getPlayerExact(name);

                            p.setGameMode(modeType);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            continue;
                        } else {
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                            continue;
                        }
                    }
                    return;
                } else {
                    sp.setGameMode(modeType);
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたのゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                    return;
                }
            } else if (label.equalsIgnoreCase("gmsp") || label.equalsIgnoreCase("gm3")) {
                final GameMode modeType = MainAPI.Gamemode.SPECTATOR.getGameMode();
                final String modeName = MainAPI.Gamemode.SPECTATOR.getName();

                if (args.length > 0) {
                    for (String name : args) {
                        if (Bukkit.getPlayerExact(name) != null) {
                            Player p = Bukkit.getPlayerExact(name);

                            p.setGameMode(modeType);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            continue;
                        } else {
                            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                            continue;
                        }
                    }
                    return;
                } else {
                    sp.setGameMode(modeType);
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "あなたのゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                    return;
                }
            }
        } else {
            if (label.equalsIgnoreCase("gamemode") || label.equalsIgnoreCase("gm")) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                        final GameMode modeType = GameMode.SURVIVAL;
                        final String modeName = "サバイバル";

                        if (args.length > 1) {
                            for (int i = 1; i < args.length; i++) {
                                final String name = args[i];

                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player p = Bukkit.getPlayerExact(name);

                                    p.setGameMode(modeType);
                                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                                    sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                                    continue;
                                } else {
                                    sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                                    continue;
                                }
                            }
                            return;
                        }
                        sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                        return;
                    } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                        final GameMode modeType = GameMode.CREATIVE;
                        final String modeName = "クリエイティブ";

                        if (args.length > 1) {
                            for (int i = 1; i < args.length; i++) {
                                final String name = args[i];

                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player p = Bukkit.getPlayerExact(name);

                                    p.setGameMode(modeType);
                                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                                    sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                                    continue;
                                } else {
                                    sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                                    continue;
                                }
                            }
                            return;
                        }
                        sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                        return;
                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                        final GameMode modeType = GameMode.ADVENTURE;
                        final String modeName = "アドベンチャー";

                        if (args.length > 1) {
                            for (int i = 1; i < args.length; i++) {
                                final String name = args[i];

                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player p = Bukkit.getPlayerExact(name);

                                    p.setGameMode(modeType);
                                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                                    sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                                    continue;
                                } else {
                                    sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                                    continue;
                                }
                            }
                            return;
                        }
                        sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                        return;
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                        final GameMode modeType = GameMode.SPECTATOR;
                        final String modeName = "スペクテイター";

                        if (args.length > 1) {
                            for (int i = 1; i < args.length; i++) {
                                final String name = args[i];

                                if (Bukkit.getPlayerExact(name) != null) {
                                    Player p = Bukkit.getPlayerExact(name);

                                    p.setGameMode(modeType);
                                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                                    sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                                    continue;
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
                sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            } else if (label.equalsIgnoreCase("gms") || label.equalsIgnoreCase("gm0")) {
                final GameMode modeType = GameMode.SURVIVAL;
                final String modeName = "サバイバル";

                if (args.length > 0) {
                    for (String name : args) {
                        if (Bukkit.getPlayerExact(name) != null) {
                            Player p = Bukkit.getPlayerExact(name);

                            p.setGameMode(modeType);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            continue;
                        } else {
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                            continue;
                        }
                    }
                    return;
                }
                sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            } else if (label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase("gm1")) {
                final GameMode modeType = GameMode.CREATIVE;
                final String modeName = "クリエイティブ";

                if (args.length > 0) {
                    for (String name : args) {
                        if (Bukkit.getPlayerExact(name) != null) {
                            Player p = Bukkit.getPlayerExact(name);

                            p.setGameMode(modeType);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            continue;
                        } else {
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                            continue;
                        }
                    }
                    return;
                }
                sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            } else if (label.equalsIgnoreCase("gma") || label.equalsIgnoreCase("gm2")) {
                final GameMode modeType = GameMode.ADVENTURE;
                final String modeName = "アドベンチャー";

                if (args.length > 0) {
                    for (String name : args) {
                        if (Bukkit.getPlayerExact(name) != null) {
                            Player p = Bukkit.getPlayerExact(name);

                            p.setGameMode(modeType);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            continue;
                        } else {
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + name + "はオフラインです。");
                            continue;
                        }
                    }
                    return;
                }
                sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            } else if (label.equalsIgnoreCase("gmsp") || label.equalsIgnoreCase("gm3")) {
                final GameMode modeType = GameMode.SPECTATOR;
                final String modeName = "スペクテイター";

                if (args.length > 0) {
                    for (String name : args) {
                        if (Bukkit.getPlayerExact(name) != null) {
                            Player p = Bukkit.getPlayerExact(name);

                            p.setGameMode(modeType);
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのゲームモードを" + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.GRAY + "モードに変更しました。");
                            sender.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "のゲームモードを" + ChatColor.GOLD + ChatColor.UNDERLINE + modeName + ChatColor.RESET + ChatColor.YELLOW + "モードに変更しました。");
                            continue;
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
}
