package org.aoichaan0513.a_core.Listeners;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.Command.Sort;
import org.aoichaan0513.a_core.Inventorys.MainInventory;
import org.aoichaan0513.a_core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        FileConfiguration fileConfiguration = Main.getMainConfig();

        if (fileConfiguration.getBoolean("messages.joinMessage.enabled"))
            e.setJoinMessage(MainAPI.getText(MainAPI.setPlaceholder(fileConfiguration.getString("messages.joinMessage.message"), p)));

        MainAPI.hidePlayers(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        FileConfiguration fileConfiguration = Main.getMainConfig();

        if (fileConfiguration.getBoolean("messages.quitMessage.enabled"))
            e.setQuitMessage(MainAPI.getText(MainAPI.setPlaceholder(fileConfiguration.getString("messages.quitMessage.message"), p)));
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage();

        FileConfiguration fileConfiguration = Main.getMainConfig();

        if (!MainAPI.isAdminMode(p)) {
            if (Bukkit.getPluginCommand(cmd.split(" ")[0].substring(1)) != null && Bukkit.getPluginCommand(cmd.split(" ")[0].substring(1)).getPermission() != null
                    && !p.hasPermission(Bukkit.getPluginCommand(cmd.split(" ")[0].substring(1)).getPermission())) {
                e.setCancelled(true);
                p.sendMessage(MainAPI.getText(MainAPI.setPlaceholder(fileConfiguration.getString("messages.commandMessage"), p)));
                return;
            } else {
                e.setCancelled(false);
                p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + p.getName() + ": " + cmd);
                return;
            }
        } else {
            e.setCancelled(false);
            for (Player player : Bukkit.getOnlinePlayers())
                if (MainAPI.isAdminMode(player))
                    player.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + p.getName() + ": " + cmd);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        Material material = e.getMaterial();

        if (action != Action.PHYSICAL && material == Main.menuItem) {
            if (!Main.getMainConfig().getBoolean("menu.enabled", true)) return;
            e.setCancelled(true);
            MainInventory.openInventory(p);
            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + ChatColor.UNDERLINE + "メニュー" + ChatColor.RESET + ChatColor.YELLOW + "を開きました。");
            return;
        }

        if (action == Action.LEFT_CLICK_BLOCK && material == Main.wandItem) {
            Block block = e.getClickedBlock();
            Inventory inventory;

            if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST || block.getType().toString().endsWith("SHULKER_BOX")) {
                inventory = ((InventoryHolder) block.getState()).getInventory();
            } else if (block.getType() == Material.ENDER_CHEST) {
                inventory = p.getEnderChest();
            } else {
                return;
            }

            Sort.sortInventory(inventory, 0, inventory.getSize());
            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + ChatColor.UNDERLINE + "チェスト" + ChatColor.RESET + ChatColor.YELLOW + "を整理しました。");
            return;
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        String[] lines = e.getLines();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            e.setLine(i, MainAPI.getText(line));
        }
    }
}
