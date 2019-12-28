package org.aoichaan0513.a_core.Listeners;

import net.milkbowl.vault.economy.Economy;
import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Gate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GateListener implements Listener {

    private static final String split = "-_-";

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        ItemStack itemStack = e.getItem();

        FileConfiguration mainConfig = Main.getMainConfig();
        FileConfiguration gateConfig = Main.getGateConfig();

        if (!mainConfig.getBoolean("hooks.vault", false)) return;

        if (action == Action.RIGHT_CLICK_BLOCK) {
            Block block = e.getClickedBlock();
            Location loc = block.getLocation();

            if (block.getType() == Material.ACACIA_FENCE_GATE || block.getType() == Material.BIRCH_FENCE_GATE
                    || block.getType() == Material.DARK_OAK_FENCE_GATE || block.getType() == Material.JUNGLE_FENCE_GATE
                    || block.getType() == Material.OAK_FENCE_GATE || block.getType() == Material.SPRUCE_FENCE_GATE) {
                if (gateConfig.contains("gates." + loc.getWorld().getName() + split + loc.getBlockX() + split + loc.getBlockY() + split + loc.getBlockZ())) {
                    BlockState blockState = block.getState();
                    Gate gate = (Gate) blockState.getBlockData();

                    if (!gate.isOpen()) {
                        if (itemStack != null) {
                            if (itemStack.hasItemMeta()) {
                                ItemMeta itemMeta = itemStack.getItemMeta();

                                if (itemStack.getType() == Main.gateItem) {
                                    Economy economy = Main.getEconomy();
                                    final double money = gateConfig.getDouble("gates." + loc.getWorld().getName() + split + loc.getBlockX() + split + loc.getBlockY() + split + loc.getBlockZ() + ".money", 200);
                                    final String name = "&a&lSuica";
                                    final String displayName = MainAPI.getText(mainConfig.getString("ticketGate.card.name", name));

                                    if (itemMeta.getDisplayName().equalsIgnoreCase(displayName)) {
                                        if (!gateConfig.getBoolean("gates." + loc.getWorld().getName() + split + loc.getBlockX() + split + loc.getBlockY() + split + loc.getBlockZ() + ".quitOnly", false)) {
                                            if (economy.has(p, money)) {
                                                e.setCancelled(false);

                                                List<String> lores = new ArrayList<>();
                                                for (String str : mainConfig.getStringList("ticketGate.card.lores"))
                                                    lores.add(MainAPI.getText(str));

                                                economy.withdrawPlayer(p, money);
                                                itemMeta.setDisplayName(ChatColor.GOLD + "* " + MainAPI.getText(displayName));
                                                itemMeta.setLore(lores);
                                                itemStack.setItemMeta(itemMeta);
                                                p.updateInventory();

                                                // setSigns(gateConfig, loc, "" + ChatColor.RED + ChatColor.BOLD + "✘");

                                                ItemStack frameStack1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                                ItemMeta frameMeta1 = frameStack1.getItemMeta();
                                                frameMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                                frameMeta1.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "✘");
                                                frameStack1.setItemMeta(frameMeta1);

                                                setItems(gateConfig, loc, frameStack1);
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        // setSigns(gateConfig, loc, "" + ChatColor.GREEN + ChatColor.BOLD + "←");

                                                        ItemStack frameStack2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                                        ItemMeta frameMeta2 = frameStack2.getItemMeta();
                                                        frameMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                                        frameMeta2.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + "←");
                                                        frameStack2.setItemMeta(frameMeta2);

                                                        setItems(gateConfig, loc, frameStack2);

                                                        gate.setOpen(false);
                                                        blockState.setBlockData(gate);
                                                        blockState.update();
                                                    }
                                                }.runTaskLater(Main.getInstance(), 20 * 3);

                                                p.sendMessage(MainAPI.getText(mainConfig.getString("messages.ticketGate.card").replace("\\n", "\n").replace("%money%", String.valueOf(economy.getBalance(p)))));
                                                playSound(p, loc, true);
                                                return;
                                            } else {
                                                e.setCancelled(true);

                                                // setSigns(gateConfig, loc, "" + ChatColor.RED + ChatColor.BOLD + "✘");

                                                ItemStack frameStack1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                                ItemMeta frameMeta1 = frameStack1.getItemMeta();
                                                frameMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                                frameMeta1.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "✘");
                                                frameStack1.setItemMeta(frameMeta1);

                                                setItems(gateConfig, loc, frameStack1);
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        // setSigns(gateConfig, loc, "" + ChatColor.GREEN + ChatColor.BOLD + "←");

                                                        ItemStack frameStack2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                                        ItemMeta frameMeta2 = frameStack2.getItemMeta();
                                                        frameMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                                        frameMeta2.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + "←");
                                                        frameStack2.setItemMeta(frameMeta2);

                                                        setItems(gateConfig, loc, frameStack2);
                                                    }
                                                }.runTaskLater(Main.getInstance(), 20 * 3);

                                                p.sendMessage(MainAPI.getText(mainConfig.getString("messages.ticketGate.errors.noHand").replace("\\n", "\n")));
                                                playSound(p, loc, false);
                                                return;
                                            }
                                        } else {
                                            e.setCancelled(true);

                                            // setSigns(gateConfig, loc, "" + ChatColor.RED + ChatColor.BOLD + "✘");

                                            ItemStack frameStack1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                            ItemMeta frameMeta1 = frameStack1.getItemMeta();
                                            frameMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                            frameMeta1.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "✘");
                                            frameStack1.setItemMeta(frameMeta1);

                                            setItems(gateConfig, loc, frameStack1);
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    // setSigns(gateConfig, loc, "" + ChatColor.GREEN + ChatColor.BOLD + "←");

                                                    ItemStack frameStack2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                                    ItemMeta frameMeta2 = frameStack2.getItemMeta();
                                                    frameMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                                    frameMeta2.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + "←");
                                                    frameStack2.setItemMeta(frameMeta2);

                                                    setItems(gateConfig, loc, frameStack2);
                                                }
                                            }.runTaskLater(Main.getInstance(), 20 * 3);

                                            p.sendMessage(MainAPI.getText(mainConfig.getString("messages.ticketGate.errors.quitOnly").replace("\\n", "\n")));
                                            playSound(p, loc, false);
                                            return;
                                        }
                                    } else if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "* " + displayName)) {
                                        e.setCancelled(false);

                                        List<String> lores = new ArrayList<>();
                                        for (String str : mainConfig.getStringList("ticketGate.card.lores"))
                                            lores.add(MainAPI.getText(str));

                                        itemMeta.setDisplayName(displayName);
                                        itemMeta.setLore(lores);
                                        itemStack.setItemMeta(itemMeta);
                                        p.updateInventory();

                                        // setSigns(gateConfig, loc, "" + ChatColor.RED + ChatColor.BOLD + "✘");

                                        ItemStack frameStack1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                        ItemMeta frameMeta1 = frameStack1.getItemMeta();
                                        frameMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                        frameMeta1.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "✘");
                                        frameStack1.setItemMeta(frameMeta1);

                                        setItems(gateConfig, loc, frameStack1);
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                // setSigns(gateConfig, loc, "" + ChatColor.GREEN + ChatColor.BOLD + "←");

                                                ItemStack frameStack2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                                ItemMeta frameMeta2 = frameStack2.getItemMeta();
                                                frameMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                                frameMeta2.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + "←");
                                                frameStack2.setItemMeta(frameMeta2);

                                                setItems(gateConfig, loc, frameStack2);

                                                gate.setOpen(false);
                                                blockState.setBlockData(gate);
                                                blockState.update();
                                            }
                                        }.runTaskLater(Main.getInstance(), 20 * 3);

                                        p.sendMessage(MainAPI.getText(mainConfig.getString("messages.ticketGate.card").replace("\\n", "\n").replace("%money%", String.valueOf(economy.getBalance(p)))));
                                        playSound(p, loc, true);
                                        return;
                                    }
                                } else {
                                    e.setCancelled(true);

                                    // setSigns(gateConfig, loc, "" + ChatColor.RED + ChatColor.BOLD + "✘");

                                    ItemStack frameStack1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                    ItemMeta frameMeta1 = frameStack1.getItemMeta();
                                    frameMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                    frameMeta1.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "✘");
                                    frameStack1.setItemMeta(frameMeta1);

                                    setItems(gateConfig, loc, frameStack1);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            // setSigns(gateConfig, loc, "" + ChatColor.GREEN + ChatColor.BOLD + "←");

                                            ItemStack frameStack2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                            ItemMeta frameMeta2 = frameStack2.getItemMeta();
                                            frameMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                            frameMeta2.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + "←");
                                            frameStack2.setItemMeta(frameMeta2);

                                            setItems(gateConfig, loc, frameStack2);
                                        }
                                    }.runTaskLater(Main.getInstance(), 20 * 3);

                                    p.sendMessage(MainAPI.getText(mainConfig.getString("messages.ticketGate.errors.noHand").replace("\\n", "\n")));
                                    playSound(p, loc, false);
                                    return;
                                }
                            } else {
                                e.setCancelled(true);

                                // setSigns(gateConfig, loc, "" + ChatColor.RED + ChatColor.BOLD + "✘");

                                ItemStack frameStack1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                ItemMeta frameMeta1 = frameStack1.getItemMeta();
                                frameMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                frameMeta1.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "✘");
                                frameStack1.setItemMeta(frameMeta1);

                                setItems(gateConfig, loc, frameStack1);
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        // setSigns(gateConfig, loc, "" + ChatColor.GREEN + ChatColor.BOLD + "←");

                                        ItemStack frameStack2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                        ItemMeta frameMeta2 = frameStack2.getItemMeta();
                                        frameMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                        frameMeta2.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + "←");
                                        frameStack2.setItemMeta(frameMeta2);

                                        setItems(gateConfig, loc, frameStack2);
                                    }
                                }.runTaskLater(Main.getInstance(), 20 * 3);

                                p.sendMessage(MainAPI.getText(mainConfig.getString("messages.ticketGate.errors.noHand").replace("\\n", "\n")));
                                playSound(p, loc, false);
                                return;
                            }
                        } else {
                            e.setCancelled(true);

                            // setSigns(gateConfig, loc, "" + ChatColor.RED + ChatColor.BOLD + "✘");

                            ItemStack frameStack1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            ItemMeta frameMeta1 = frameStack1.getItemMeta();
                            frameMeta1.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                            frameMeta1.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "✘");
                            frameStack1.setItemMeta(frameMeta1);

                            setItems(gateConfig, loc, frameStack1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    // setSigns(gateConfig, loc, "" + ChatColor.GREEN + ChatColor.BOLD + "←");

                                    ItemStack frameStack2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                    ItemMeta frameMeta2 = frameStack2.getItemMeta();
                                    frameMeta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                                    frameMeta2.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + "←");
                                    frameStack2.setItemMeta(frameMeta2);

                                    setItems(gateConfig, loc, frameStack2);
                                }
                            }.runTaskLater(Main.getInstance(), 20 * 3);

                            p.sendMessage(MainAPI.getText(mainConfig.getString("messages.ticketGate.errors.noHand").replace("\\n", "\n")));
                            playSound(p, loc, false);
                            return;
                        }
                    } else {
                        e.setCancelled(true);
                        return;
                    }
                } else {
                    e.setCancelled(false);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        Location loc = block.getLocation();

        FileConfiguration mainConfig = Main.getMainConfig();
        FileConfiguration gateConfig = Main.getGateConfig();

        if (!mainConfig.getBoolean("hooks.vault", false)) return;

        if (block.getType() == Material.ACACIA_FENCE_GATE || block.getType() == Material.BIRCH_FENCE_GATE
                || block.getType() == Material.DARK_OAK_FENCE_GATE || block.getType() == Material.JUNGLE_FENCE_GATE
                || block.getType() == Material.OAK_FENCE_GATE || block.getType() == Material.SPRUCE_FENCE_GATE) {
            if (gateConfig.contains("gates." + loc.getWorld().getName() + split + loc.getBlockX() + split + loc.getBlockY() + split + loc.getBlockZ())) {
                if (MainAPI.isAdmin(p) && p.getGameMode() == GameMode.CREATIVE) return;
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity remover = e.getRemover();

        FileConfiguration mainConfig = Main.getMainConfig();
        FileConfiguration gateConfig = Main.getGateConfig();

        if (!mainConfig.getBoolean("hooks.vault", false) || !(entity instanceof ItemFrame)) return;

        if (remover instanceof Player) {
            Player p = (Player) remover;

            if (MainAPI.isAdmin(p) && p.getGameMode() == GameMode.CREATIVE) return;

            for (String name : gateConfig.getConfigurationSection("gates").getKeys(false))
                if (gateConfig.getStringList("gates." + name + ".signs").contains(entity.getLocation().getWorld().getName() + split + entity.getLocation().getBlockX() + split + entity.getLocation().getBlockY() + split + entity.getLocation().getBlockZ()))
                    e.setCancelled(true);
        } else {
            for (String name : gateConfig.getConfigurationSection("gates").getKeys(false))
                if (gateConfig.getStringList("gates." + name + ".signs").contains(entity.getLocation().getWorld().getName() + split + entity.getLocation().getBlockX() + split + entity.getLocation().getBlockY() + split + entity.getLocation().getBlockZ()))
                    e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFrameEntity(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamager();

        FileConfiguration mainConfig = Main.getMainConfig();
        FileConfiguration gateConfig = Main.getGateConfig();

        if (!mainConfig.getBoolean("hooks.vault", false) || !(entity instanceof ItemFrame)) return;

        if (damager instanceof Player) {
            Player p = (Player) damager;

            if (MainAPI.isAdmin(p) && p.getGameMode() == GameMode.CREATIVE) return;

            for (String name : gateConfig.getConfigurationSection("gates").getKeys(false)) {
                if (gateConfig.getStringList("gates." + name + ".signs").contains(entity.getLocation().getWorld().getName() + split + entity.getLocation().getBlockX() + split + entity.getLocation().getBlockY() + split + entity.getLocation().getBlockZ())) {
                    e.setCancelled(true);
                }
            }
        } else {
            for (String name : gateConfig.getConfigurationSection("gates").getKeys(false)) {
                if (gateConfig.getStringList("gates." + name + ".signs").contains(entity.getLocation().getWorld().getName() + split + entity.getLocation().getBlockX() + split + entity.getLocation().getBlockY() + split + entity.getLocation().getBlockZ())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private void playSound(Player p, Location loc, boolean isSuccess) {
        if (isSuccess) {
            new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    if (i == 0 || i == 2)
                        p.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BELL, 2, 1);

                    if (i == 2)
                        cancel();
                    else
                        i++;
                }
            }.runTaskTimer(Main.getInstance(), 2, 1);
        } else {
            new BukkitRunnable() {
                int count = 0;

                public void run() {
                    count++;
                    if (count == 10 || count == 11 || count == 12 || count == 13 || count == 14 || count == 15) {
                    } else if (count == 25) {
                        cancel();
                    } else {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2, 1);
                    }
                }
            }.runTaskTimer(Main.getInstance(), 3, 3);
        }
    }

    private void setItems(FileConfiguration fileConfiguration, Location loc, ItemStack itemStack) {
        if (fileConfiguration.contains("gates." + loc.getWorld().getName() + split + loc.getBlockX() + split + loc.getBlockY() + split + loc.getBlockZ() + ".signs")) {
            for (String str : fileConfiguration.getStringList("gates." + loc.getWorld().getName() + split + loc.getBlockX() + split + loc.getBlockY() + split + loc.getBlockZ() + ".signs")) {
                String[] strs = str.split(split);

                Location signLoc = new Location(Bukkit.getWorld(strs[0]), Double.parseDouble(strs[1]), Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));

                /*
                Block signBlock = signLoc.getBlock();
                if (signBlock.getType() == Material.ACACIA_SIGN || signBlock.getType() == Material.ACACIA_WALL_SIGN
                        || signBlock.getType() == Material.BIRCH_SIGN || signBlock.getType() == Material.BIRCH_WALL_SIGN
                        || signBlock.getType() == Material.DARK_OAK_SIGN || signBlock.getType() == Material.DARK_OAK_WALL_SIGN
                        || signBlock.getType() == Material.JUNGLE_SIGN || signBlock.getType() == Material.JUNGLE_WALL_SIGN
                        || signBlock.getType() == Material.OAK_SIGN || signBlock.getType() == Material.OAK_WALL_SIGN
                        || signBlock.getType() == Material.SPRUCE_SIGN || signBlock.getType() == Material.SPRUCE_WALL_SIGN) {
                    ItemFrame itemFrame = (ItemFrame) signBlock;
                    itemFrame.setItem(new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
                    itemFrame.setRotation(Rotation.NONE);
                    Sign sign = (Sign) signBlock.getState();
                    sign.setLine(1, text);
                    sign.update();
                }
                */

                for (Entity entity : signLoc.getChunk().getEntities()) {
                    if (entity instanceof ItemFrame && entity.getLocation().getBlockX() == signLoc.getBlockX()
                            && entity.getLocation().getBlockY() == signLoc.getBlockY() && entity.getLocation().getBlockZ() == signLoc.getBlockZ()) {
                        ItemFrame itemFrame = (ItemFrame) entity;
                        itemFrame.setItem(itemStack, false);
                        itemFrame.setRotation(Rotation.NONE);
                    }
                }
            }
        }
    }
}
