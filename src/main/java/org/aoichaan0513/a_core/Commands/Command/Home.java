package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.aoichaan0513.a_core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class Home extends ICommand {

    public Home(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("home")) {
            teleportHome(sp, cmd, label, args);
            return;
        } else if (label.equalsIgnoreCase("sethome")) {
            setHome(sp, cmd, label, args);
            return;
        } else if (label.equalsIgnoreCase("delhome")) {
            removeHome(sp, cmd, label, args);
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

    private void teleportHome(Player sp, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            final String homeName = args[0];

            FileConfiguration homeConfig = Main.getHomeConfig();

            if (homeConfig.contains("homes." + sp.getUniqueId().toString() + "." + homeName)) {
                Location loc = new Location(
                        Bukkit.getWorld(homeConfig.getString("homes." + sp.getUniqueId().toString() + "." + homeName + ".world")),
                        homeConfig.getDouble("homes." + sp.getUniqueId().toString() + "." + homeName + ".x"),
                        homeConfig.getDouble("homes." + sp.getUniqueId().toString() + "." + homeName + ".y"),
                        homeConfig.getDouble("homes." + sp.getUniqueId().toString() + "." + homeName + ".z"),
                        homeConfig.getInt("homes." + sp.getUniqueId().toString() + "." + homeName + ".yaw"),
                        homeConfig.getInt("homes." + sp.getUniqueId().toString() + "." + homeName + ".pitch")
                );

                sp.teleport(loc);
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + ChatColor.UNDERLINE + homeName + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + ChatColor.UNDERLINE + homeName + ChatColor.RESET + ChatColor.RED + "は設定されていないためテレポートできません。");
            return;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
        return;
    }

    private void setHome(Player sp, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            final String homeName = args[0];

            FileConfiguration homeConfig = Main.getHomeConfig();

            if (!homeConfig.contains("homes." + sp.getUniqueId().toString()) || homeConfig.getConfigurationSection("homes." + sp.getUniqueId().toString()).getKeys(false).size() < Main.getMainConfig().getInt("maxHomeCount")) {
                Location loc = sp.getLocation();
                homeConfig.set("homes." + sp.getUniqueId().toString() + "." + homeName + ".world", loc.getWorld().getName());
                homeConfig.set("homes." + sp.getUniqueId().toString() + "." + homeName + ".x", loc.getX());
                homeConfig.set("homes." + sp.getUniqueId().toString() + "." + homeName + ".y", loc.getY());
                homeConfig.set("homes." + sp.getUniqueId().toString() + "." + homeName + ".z", loc.getZ());
                homeConfig.set("homes." + sp.getUniqueId().toString() + "." + homeName + ".yaw", loc.getYaw());
                homeConfig.set("homes." + sp.getUniqueId().toString() + "." + homeName + ".pitch", loc.getPitch());

                Main.setHomeConfig();
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "現在位置を" + ChatColor.GOLD + ChatColor.UNDERLINE + homeName + ChatColor.RESET + ChatColor.YELLOW + "として設定しました。");
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + Main.getMainConfig().getInt("maxHomeCount") + "以上はホームとして設定できません。");
            return;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
        return;
    }

    private void removeHome(Player sp, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            final String homeName = args[0];

            FileConfiguration homeConfig = Main.getHomeConfig();

            if (homeConfig.contains("homes." + sp.getUniqueId().toString() + "." + homeName)) {
                Location loc = sp.getLocation();
                homeConfig.set("homes." + sp.getUniqueId().toString() + "." + homeName, null);

                Main.setHomeConfig();
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + ChatColor.UNDERLINE + homeName + ChatColor.RESET + ChatColor.YELLOW + "を削除しました。");
                return;
            }
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + ChatColor.UNDERLINE + homeName + ChatColor.RESET + ChatColor.RED + "は設定されていないため削除できません。");
            return;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
        return;
    }
}
