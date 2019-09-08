package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.ItemComparator;
import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.aoichaan0513.a_core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort extends ICommand {

    public Sort(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (MainAPI.isAdmin(sp) || sp.hasPermission("a_core.sort")) {
            Block block = sp.getTargetBlock(null, 4);

            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("chest")) {
                    if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST || block.getType() == Material.ENDER_CHEST || block.getType().toString().endsWith("SHULKER_BOX")) {
                        Bukkit.getPluginManager().callEvent(new PlayerInteractEvent(sp, Action.LEFT_CLICK_BLOCK, new ItemStack(Main.wandItem), block, BlockFace.SELF));
                        return;
                    } else {
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "目線の先にチェストがないため実行できません。");
                        return;
                    }
                } else if (args[0].equalsIgnoreCase("top")) {
                    sortInventory(sp.getInventory(), 9, 36);
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + ChatColor.UNDERLINE + "インベントリ" + ChatColor.RESET + ChatColor.YELLOW + "を整理しました。");
                    return;
                } else if (args[0].equalsIgnoreCase("all")) {
                    sortInventory(sp.getInventory(), 0, 36);
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + ChatColor.UNDERLINE + "インベントリ全体" + ChatColor.RESET + ChatColor.YELLOW + "を整理しました。");
                    return;
                } else if (args[0].equalsIgnoreCase("hot")) {
                    sortInventory(sp.getInventory(), 0, 9);
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + ChatColor.GOLD + ChatColor.UNDERLINE + "ホットバー" + ChatColor.RESET + ChatColor.YELLOW + "を整理しました。");
                    return;
                }
                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                return;
            } else {
                if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST || block.getType() == Material.ENDER_CHEST || block.getType().toString().endsWith("SHULKER_BOX")) {
                    sp.performCommand("sort chest");
                    return;
                } else {
                    sp.performCommand("sort top");
                    return;
                }
            }
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
        if (MainAPI.isAdmin(sp) || sp.hasPermission("a_core.sort")) {
            if (args.length == 1) {
                if (args[0].length() == 0) {
                    return Arrays.asList("chest", "top", "all", "hot");
                } else {
                    if ("chest".startsWith(args[0])) {
                        return Collections.singletonList("chest");
                    } else if ("top".startsWith(args[0])) {
                        return Collections.singletonList("top");
                    } else if ("all".startsWith(args[0])) {
                        return Collections.singletonList("all");
                    } else if ("hot".startsWith(args[0])) {
                        return Collections.singletonList("hot");
                    }
                }
            }
        }
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

    public static void sortInventory(Inventory inventory, int startIndex, int endIndex) {
        ItemStack[] items = inventory.getContents();
        boolean stackAll = Main.getMainConfig().getBoolean("inventorySort.stackAll");

        for (int i = startIndex; i < endIndex; i++) {
            ItemStack item1 = items[i];

            if (item1 == null)
                continue;

            int maxStackSize = stackAll ? 64 : item1.getMaxStackSize();

            if (item1.getAmount() <= 0 || maxStackSize == 1)
                continue;

            if (item1.getAmount() < maxStackSize) {
                int needed = maxStackSize - item1.getAmount();

                for (int j = i + 1; j < endIndex; j++) {
                    ItemStack item2 = items[j];

                    if (item2 == null || item2.getAmount() <= 0 || maxStackSize == 1) {
                        continue;
                    }

                    if (item2.getType() == item1.getType()
                            && item1.getDurability() == item2.getDurability()
                            && item1.getEnchantments().equals(item2.getEnchantments())
                            && item1.getItemMeta().equals(item2.getItemMeta())) {
                        if (item2.getAmount() > needed) {
                            item1.setAmount(maxStackSize);
                            item2.setAmount(item2.getAmount() - needed);
                            break;
                        } else {
                            items[j] = null;
                            item1.setAmount(item1.getAmount() + item2.getAmount());
                            needed = maxStackSize - item1.getAmount();
                        }
                    }
                }
            }
        }

        Arrays.sort(items, startIndex, endIndex, new ItemComparator());
        inventory.setContents(items);
    }
}
