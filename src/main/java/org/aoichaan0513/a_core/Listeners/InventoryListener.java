package org.aoichaan0513.a_core.Listeners;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.Command.Sort;
import org.aoichaan0513.a_core.Inventorys.MainInventory;
import org.aoichaan0513.a_core.Inventorys.MenuInventoryHolder;
import org.aoichaan0513.a_core.Inventorys.WorldInventory;
import org.aoichaan0513.a_core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        int slot = e.getRawSlot();

        Player p = (Player) e.getWhoClicked();

        if (inv.getHolder() instanceof MenuInventoryHolder) {
            e.setCancelled(true);

            if (e.getView().getTitle().equalsIgnoreCase(MainInventory.getTitle())) {
                if (MainInventory.Item.getItem(slot) == MainInventory.Item.PLAYER_INFO) {
                    if (e.getClick() == ClickType.RIGHT) {
                        Bukkit.broadcastMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "が位置を共有しています。\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + "ワールド" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getWorld().getName() + "\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + "X" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getBlockX() + "\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + "Y" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getBlockY() + "\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + "Z" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getBlockZ());
                        p.closeInventory();
                        return;
                    }
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.WORLD_LOBBY) {
                    p.teleport(Bukkit.getWorld(Main.getMainConfig().getString("lobbyName")).getSpawnLocation());
                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + ChatColor.GOLD + ChatColor.UNDERLINE + "ロビー" + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.WORLD_LIFE) {
                    if (e.getClick() == ClickType.LEFT) {
                        FileConfiguration worldConfig = Main.getWorldConfig();

                        final String configPath = "worlds." + p.getUniqueId().toString() + "." + MainAPI.WorldType.WORLD_LIFE_MAIN.getType();

                        if (worldConfig.contains(configPath)) {
                            final MainAPI.WorldType worldType = MainAPI.WorldType.getWorld(worldConfig.getString(configPath));
                            final ChatColor chatColor = getWorldColor(worldType);

                            if (worldType.isWorldLoaded()) {
                                Location loc = worldType.getWorld().getSpawnLocation();
                                p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));

                                TextComponent textComponent1 = new TextComponent(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY));

                                TextComponent textComponent2 = new TextComponent("" + chatColor + ChatColor.UNDERLINE + worldType.getName());
                                textComponent2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + worldType.getId()).create()));

                                TextComponent textComponent3 = new TextComponent("" + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");

                                textComponent1.addExtra(textComponent2);
                                textComponent1.addExtra(textComponent3);

                                p.spigot().sendMessage(textComponent1);
                                return;
                            } else {
                                p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + ChatColor.UNDERLINE + worldType.getName() + ChatColor.RESET + ChatColor.RED + "というワールドはありません。\n" +
                                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "ワールドが読み込まれてないか、ワールドがない可能性があります。");
                                return;
                            }
                        } else {
                            runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_MAIN);
                            return;
                        }
                    } else if (e.getClick() == ClickType.RIGHT) {
                        WorldInventory.openInventory(p, WorldInventory.InventoryType.WORLD_LIFE);
                        return;
                    }
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.WORLD_LIFE_RESOURCE) {
                    if (e.getClick() == ClickType.LEFT) {
                        FileConfiguration worldConfig = Main.getWorldConfig();

                        final String configPath = "worlds." + p.getUniqueId().toString() + "." + MainAPI.WorldType.WORLD_LIFE_RESOURCE_MAIN.getType();

                        if (worldConfig.contains(configPath)) {
                            final MainAPI.WorldType worldType = MainAPI.WorldType.getWorld(worldConfig.getString(configPath));
                            final ChatColor chatColor = getWorldColor(worldType);

                            if (worldType.isWorldLoaded()) {
                                Location loc = worldType.getWorld().getSpawnLocation();
                                p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));

                                TextComponent textComponent1 = new TextComponent(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY));

                                TextComponent textComponent2 = new TextComponent("" + chatColor + ChatColor.UNDERLINE + worldType.getName());
                                textComponent2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + worldType.getId()).create()));

                                TextComponent textComponent3 = new TextComponent("" + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");

                                textComponent1.addExtra(textComponent2);
                                textComponent1.addExtra(textComponent3);

                                p.spigot().sendMessage(textComponent1);
                                return;
                            } else {
                                p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + ChatColor.UNDERLINE + worldType.getName() + ChatColor.RESET + ChatColor.RED + "というワールドはありません。\n" +
                                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "ワールドが読み込まれてないか、ワールドがない可能性があります。");
                                return;
                            }
                        } else {
                            runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_RESOURCE_MAIN);
                            return;
                        }
                    } else if (e.getClick() == ClickType.RIGHT) {
                        WorldInventory.openInventory(p, WorldInventory.InventoryType.WORLD_LIFE_RESOURCE);
                        return;
                    }
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.WORLDGUARD_MENU) {
                    p.performCommand("wggui");
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.MAIL_MENU) {
                    p.performCommand("cc open mail.yml");
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.CRAFTTABLE) {
                    if (MainAPI.isAdmin(p) || p.hasPermission("a_core.crafttable")) {
                        p.openWorkbench(null, true);
                        p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + "作業台を開きました。");
                        return;
                    }
                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.ENDER_CHEST) {
                    if (MainAPI.isAdmin(p) || p.hasPermission("a_core.enderchest")) {
                        p.openInventory(p.getEnderChest());
                        p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + "エンダーチェストを開きました。");
                        return;
                    }
                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.SORT_INVENTORY) {
                    if (MainAPI.isAdmin(p) || p.hasPermission("a_core.sort")) {
                        Sort.sortInventory(p.getInventory(), 0, 36);
                        p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + ChatColor.UNDERLINE + "インベントリ全体" + ChatColor.RESET + ChatColor.YELLOW + "を整理しました。");
                        return;
                    }
                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.SORT_CHEST) {
                    if (MainAPI.isAdmin(p) || p.hasPermission("a_core.sort")) {
                        Block block = p.getTargetBlock(null, 4);

                        if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST || block.getType() == Material.ENDER_CHEST || block.getType().toString().endsWith("SHULKER_BOX")) {
                            Bukkit.getPluginManager().callEvent(new PlayerInteractEvent(p, Action.LEFT_CLICK_BLOCK, new ItemStack(Main.wandItem), block, BlockFace.SELF));
                            return;
                        } else {
                            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "目線の先にチェストがないため実行できません。");
                            return;
                        }
                    }
                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PERMISSION.getMessage());
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.HOME_1) {
                    runHome(p, "home1");
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.HOME_2) {
                    runHome(p, "home2");
                    return;
                } else if (MainInventory.Item.getItem(slot) == MainInventory.Item.HOME_3) {
                    runHome(p, "home3");
                    return;
                }
            } else if (e.getView().getTitle().equalsIgnoreCase(WorldInventory.getTitle())) {
                if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.PLAYER_INFO) {
                    if (e.getClick() == ClickType.RIGHT) {
                        Bukkit.broadcastMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + p.getName() + ChatColor.YELLOW + "が位置を共有しています。\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + "ワールド" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getWorld().getName() + "\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + "X" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getBlockX() + "\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + "Y" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getBlockY() + "\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + "Z" + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getBlockZ());
                        p.closeInventory();
                        return;
                    }
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LOBBY) {
                    p.teleport(Bukkit.getWorld(Main.getMainConfig().getString("lobbyName")).getSpawnLocation());
                    p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + ChatColor.GOLD + ChatColor.UNDERLINE + "ロビー" + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");
                    return;
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LIFE) {
                    if (e.getClick() == ClickType.LEFT) {
                        FileConfiguration worldConfig = Main.getWorldConfig();

                        final String configPath = "worlds." + p.getUniqueId().toString() + "." + MainAPI.WorldType.WORLD_LIFE_MAIN.getType();

                        if (worldConfig.contains(configPath)) {
                            final MainAPI.WorldType worldType = MainAPI.WorldType.getWorld(worldConfig.getString(configPath));
                            final ChatColor chatColor = getWorldColor(worldType);

                            if (worldType.isWorldLoaded()) {
                                Location loc = worldType.getWorld().getSpawnLocation();
                                p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));

                                TextComponent textComponent1 = new TextComponent(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY));

                                TextComponent textComponent2 = new TextComponent("" + chatColor + ChatColor.UNDERLINE + worldType.getName());
                                textComponent2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + worldType.getId()).create()));

                                TextComponent textComponent3 = new TextComponent("" + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");

                                textComponent1.addExtra(textComponent2);
                                textComponent1.addExtra(textComponent3);

                                p.spigot().sendMessage(textComponent1);
                                return;
                            } else {
                                p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + ChatColor.UNDERLINE + worldType.getName() + ChatColor.RESET + ChatColor.RED + "というワールドはありません。\n" +
                                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "ワールドが読み込まれてないか、ワールドがない可能性があります。");
                                return;
                            }
                        } else {
                            runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_MAIN);
                            return;
                        }
                    } else if (e.getClick() == ClickType.RIGHT) {
                        WorldInventory.openInventory(p, WorldInventory.InventoryType.WORLD_LIFE);
                        return;
                    }
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LIFE_RESOURCE) {
                    if (e.getClick() == ClickType.LEFT) {
                        FileConfiguration worldConfig = Main.getWorldConfig();

                        final String configPath = "worlds." + p.getUniqueId().toString() + "." + MainAPI.WorldType.WORLD_LIFE_RESOURCE_MAIN.getType();

                        if (worldConfig.contains(configPath)) {
                            final MainAPI.WorldType worldType = MainAPI.WorldType.getWorld(worldConfig.getString(configPath));
                            final ChatColor chatColor = getWorldColor(worldType);

                            if (worldType.isWorldLoaded()) {
                                Location loc = worldType.getWorld().getSpawnLocation();
                                p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));

                                TextComponent textComponent1 = new TextComponent(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY));

                                TextComponent textComponent2 = new TextComponent("" + chatColor + ChatColor.UNDERLINE + worldType.getName());
                                textComponent2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + worldType.getId()).create()));

                                TextComponent textComponent3 = new TextComponent("" + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");

                                textComponent1.addExtra(textComponent2);
                                textComponent1.addExtra(textComponent3);

                                p.spigot().sendMessage(textComponent1);
                                return;
                            } else {
                                p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + ChatColor.UNDERLINE + worldType.getName() + ChatColor.RESET + ChatColor.RED + "というワールドはありません。\n" +
                                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "ワールドが読み込まれてないか、ワールドがない可能性があります。");
                                return;
                            }
                        } else {
                            runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_RESOURCE_MAIN);
                            return;
                        }
                    } else if (e.getClick() == ClickType.RIGHT) {
                        WorldInventory.openInventory(p, WorldInventory.InventoryType.WORLD_LIFE_RESOURCE);
                        return;
                    }
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LIFE_MAIN) {
                    runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_MAIN);
                    return;
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LIFE_SUB) {
                    runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_SUB);
                    return;
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LIFE_RESOURCE_MAIN) {
                    runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_RESOURCE_MAIN);
                    return;
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LIFE_RESOURCE_SUB) {
                    runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_RESOURCE_SUB);
                    return;
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LIFE_RESOURCE_NETHER) {
                    runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_RESOURCE_NETHER);
                    return;
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.WORLD_LIFE_RESOURCE_END) {
                    runWorldTeleport(p, MainAPI.WorldType.WORLD_LIFE_RESOURCE_END);
                    return;
                } else if (WorldInventory.Item.getItem(slot) == WorldInventory.Item.HOME) {
                    MainInventory.openInventory(p);
                    return;
                }
            }
        }
    }

    private void runWorldTeleport(Player p, MainAPI.WorldType worldType) {
        FileConfiguration worldConfig = Main.getWorldConfig();

        final String configPath = "worlds." + p.getUniqueId().toString() + "." + worldType.getType();
        final ChatColor chatColor = getWorldColor(worldType);

        if (worldType.isWorldLoaded()) {
            worldConfig.set(configPath, worldType.getId());
            Main.setWorldConfig();

            Location loc = worldType.getWorld().getSpawnLocation();
            p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));

            TextComponent textComponent1 = new TextComponent(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY));

            TextComponent textComponent2 = new TextComponent("" + chatColor + ChatColor.UNDERLINE + worldType.getName());
            textComponent2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + worldType.getId()).create()));

            TextComponent textComponent3 = new TextComponent("" + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");

            textComponent1.addExtra(textComponent2);
            textComponent1.addExtra(textComponent3);

            p.spigot().sendMessage(textComponent1);
            return;
        } else {
            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + ChatColor.UNDERLINE + worldType.getName() + ChatColor.RESET + ChatColor.RED + "というワールドはありません。\n" +
                    MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "ワールドが読み込まれてないか、ワールドがない可能性があります。");
            return;
        }
    }

    private ChatColor getWorldColor(MainAPI.WorldType worldType) {
        if (worldType.getType().equalsIgnoreCase("lobby"))
            return ChatColor.GOLD;
        else if (worldType.getType().equalsIgnoreCase("life"))
            return ChatColor.YELLOW;
        else if (worldType.getType().equalsIgnoreCase("lifeResource"))
            return ChatColor.GREEN;
        else if (worldType.getType().equalsIgnoreCase("other"))
            return ChatColor.AQUA;
        else
            return ChatColor.AQUA;
    }

    private void runHome(Player p, String homeName) {
        FileConfiguration homeConfig = Main.getHomeConfig();

        if (homeConfig.contains("homes." + p.getUniqueId().toString() + "." + homeName)) {
            Location loc = new Location(
                    Bukkit.getWorld(homeConfig.getString("homes." + p.getUniqueId().toString() + "." + homeName + ".world")),
                    homeConfig.getDouble("homes." + p.getUniqueId().toString() + "." + homeName + ".x"),
                    homeConfig.getDouble("homes." + p.getUniqueId().toString() + "." + homeName + ".y"),
                    homeConfig.getDouble("homes." + p.getUniqueId().toString() + "." + homeName + ".z"),
                    homeConfig.getInt("homes." + p.getUniqueId().toString() + "." + homeName + ".yaw"),
                    homeConfig.getInt("homes." + p.getUniqueId().toString() + "." + homeName + ".pitch")
            );

            p.teleport(loc);
            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + ChatColor.GREEN + ChatColor.UNDERLINE + homeName + ChatColor.RESET + ChatColor.GRAY + "にテレポートしました。");
            return;
        } else {
            if (!homeConfig.contains("homes." + p.getUniqueId().toString()) || homeConfig.getConfigurationSection("homes." + p.getUniqueId().toString()).getKeys(false).size() < Main.getMainConfig().getInt("maxHomeCount")) {
                Location loc = p.getLocation();
                homeConfig.set("homes." + p.getUniqueId().toString() + "." + homeName + ".world", loc.getWorld().getName());
                homeConfig.set("homes." + p.getUniqueId().toString() + "." + homeName + ".x", loc.getX());
                homeConfig.set("homes." + p.getUniqueId().toString() + "." + homeName + ".y", loc.getY());
                homeConfig.set("homes." + p.getUniqueId().toString() + "." + homeName + ".z", loc.getZ());
                homeConfig.set("homes." + p.getUniqueId().toString() + "." + homeName + ".yaw", loc.getYaw());
                homeConfig.set("homes." + p.getUniqueId().toString() + "." + homeName + ".pitch", loc.getPitch());

                Main.setHomeConfig();
                p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "現在位置を" + ChatColor.GOLD + ChatColor.UNDERLINE + homeName + ChatColor.RESET + ChatColor.YELLOW + "として設定しました。");
                MainInventory.openInventory(p);
                return;
            }
            p.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + Main.getMainConfig().getInt("maxHomeCount") + "以上はホームとして設定できません。");
            return;
        }
    }
}
