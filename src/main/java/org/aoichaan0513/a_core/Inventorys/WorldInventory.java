package org.aoichaan0513.a_core.Inventorys;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;

public class WorldInventory {

    private static final String title = ChatColor.DARK_GRAY + "> " + ChatColor.BOLD + ChatColor.UNDERLINE + "ホーム" + ChatColor.RESET + ChatColor.DARK_GRAY + " > " + ChatColor.BOLD + ChatColor.UNDERLINE + "ワールドメニュー";
    private static final int invSize = 9 * 6;

    private static final Material separatorMaterial = Material.BLACK_STAINED_GLASS_PANE;
    private static final byte separatorDurability = new Wool(DyeColor.BLACK).getData();

    public static Inventory getInventory(Player p, InventoryType inventoryType) {
        Inventory inv = Bukkit.createInventory(new MenuInventoryHolder(invSize), invSize, title);
        ItemFlag[] itemFlags = new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS};

        FileConfiguration worldConfig = Main.getWorldConfig();

        ItemStack itemStack1 = new ItemStack(Item.PLAYER_INFO.getMaterial(), 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta itemMeta1 = (SkullMeta) itemStack1.getItemMeta();
        itemMeta1.setDisplayName("" + ChatColor.BLUE + ChatColor.BOLD + ChatColor.UNDERLINE + "あなたの情報");
        itemMeta1.setLore(
                Arrays.asList(
                        "" + ChatColor.BLUE + ChatColor.UNDERLINE + "ユーザー名" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getName(),
                        "" + ChatColor.BLUE + ChatColor.UNDERLINE + "ランク" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.YELLOW + (Main.getLuckPerms() != null ? (Main.getLuckPerms().getUser(p.getUniqueId()).getPrimaryGroup() != null ? Main.getLuckPerms().getGroup(Main.getLuckPerms().getUser(p.getUniqueId()).getPrimaryGroup()).getDisplayName() : "なし") : "無効"),
                        "" + ChatColor.BLUE + ChatColor.UNDERLINE + "ゲームモード" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.YELLOW + MainAPI.Gamemode.getGamemode(p.getGameMode()).getName(),
                        "",
                        "" + ChatColor.BLUE + ChatColor.UNDERLINE + "ワールド" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getWorld().getName(),
                        "" + ChatColor.BLUE + ChatColor.UNDERLINE + "座標" + ChatColor.RESET + ChatColor.GRAY + ": " + ChatColor.YELLOW + p.getLocation().getBlockX() + ChatColor.GRAY + ", " + ChatColor.YELLOW + p.getLocation().getBlockY() + ChatColor.GRAY + ", " + ChatColor.YELLOW + p.getLocation().getBlockZ(),
                        "",
                        ChatColor.GOLD + "左クリック" + ChatColor.GRAY + ": " + ChatColor.BLUE + ChatColor.UNDERLINE + "",
                        ChatColor.GOLD + "右クリック" + ChatColor.GRAY + ": " + ChatColor.BLUE + ChatColor.UNDERLINE + "位置を共有"
                )
        );
        itemMeta1.addItemFlags(itemFlags);
        itemMeta1.setOwningPlayer(p);
        itemStack1.setItemMeta(itemMeta1);
        inv.setItem(Item.PLAYER_INFO.getIndex(), itemStack1);


        // ロビー
        ItemStack itemStack2 = new ItemStack(Item.WORLD_LOBBY.getMaterial());
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + ChatColor.UNDERLINE + "ロビー");
        itemMeta2.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.GOLD + ChatColor.UNDERLINE + "ロビー" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                        "",
                        ChatColor.GRAY + "\"" + ChatColor.UNDERLINE + "/lobby" + ChatColor.RESET + ChatColor.GRAY + "\"と実行してもテレポートができます。"
                )
        );
        itemMeta2.addItemFlags(itemFlags);
        itemStack2.setItemMeta(itemMeta2);
        inv.setItem(Item.WORLD_LOBBY.getIndex(), itemStack2);


        // 生活ワールド
        ItemStack itemStack3 = new ItemStack(Item.WORLD_LIFE.getMaterial());
        ItemMeta itemMeta3 = itemStack3.getItemMeta();
        itemMeta3.setDisplayName("" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "生活ワールド");

        if (worldConfig.contains("worlds." + p.getUniqueId().toString() + "." + MainAPI.WorldType.WORLD_LIFE_MAIN.getType())) {
            final MainAPI.WorldType worldType = MainAPI.WorldType.getWorld(worldConfig.getString("worlds." + p.getUniqueId().toString() + "." + MainAPI.WorldType.WORLD_LIFE_MAIN.getType()));

            if (worldType.isWorldLoaded()) {
                itemMeta3.setLore(
                        Arrays.asList(
                                "" + ChatColor.GRAY + ChatColor.YELLOW + ChatColor.UNDERLINE + "生活ワールド" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                                "",
                                ChatColor.GOLD + "左クリック" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.UNDERLINE + worldType.getName() + "にテレポート",
                                ChatColor.GOLD + "右クリック" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.UNDERLINE + "ワールドメニューを開く"
                        )
                );
            } else {
                itemMeta3.setLore(
                        Arrays.asList(
                                "" + ChatColor.GRAY + ChatColor.YELLOW + ChatColor.UNDERLINE + "生活ワールド" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                                "",
                                ChatColor.GOLD + "左クリック" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.UNDERLINE + "生活メインにテレポート",
                                ChatColor.GOLD + "右クリック" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.UNDERLINE + "ワールドメニューを開く"
                        )
                );
            }
        } else {
            itemMeta3.setLore(
                    Arrays.asList(
                            "" + ChatColor.GRAY + ChatColor.YELLOW + ChatColor.UNDERLINE + "生活ワールド" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                            "",
                            ChatColor.GOLD + "左クリック" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.UNDERLINE + "生活メインにテレポート",
                            ChatColor.GOLD + "右クリック" + ChatColor.GRAY + ": " + ChatColor.YELLOW + ChatColor.UNDERLINE + "ワールドメニューを開く"
                    )
            );
        }

        itemMeta3.addItemFlags(itemFlags);
        itemStack3.setItemMeta(itemMeta3);
        inv.setItem(Item.WORLD_LIFE.getIndex(), itemStack3);


        // 資源ワールド
        ItemStack itemStack4 = new ItemStack(Item.WORLD_LIFE_RESOURCE.getMaterial());
        ItemMeta itemMeta4 = itemStack4.getItemMeta();
        itemMeta4.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "資源ワールド");

        if (worldConfig.contains("worlds." + p.getUniqueId().toString() + "." + MainAPI.WorldType.WORLD_LIFE_RESOURCE_MAIN.getType())) {
            final MainAPI.WorldType worldType = MainAPI.WorldType.getWorld(worldConfig.getString("worlds." + p.getUniqueId().toString() + "." + MainAPI.WorldType.WORLD_LIFE_RESOURCE_MAIN.getType()));

            if (worldType.isWorldLoaded()) {
                itemMeta4.setLore(
                        Arrays.asList(
                                "" + ChatColor.GRAY + ChatColor.GREEN + ChatColor.UNDERLINE + "資源ワールド" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                                "",
                                ChatColor.GOLD + "左クリック" + ChatColor.GRAY + ": " + ChatColor.GREEN + ChatColor.UNDERLINE + worldType.getName() + "にテレポート",
                                ChatColor.GOLD + "右クリック" + ChatColor.GRAY + ": " + ChatColor.GREEN + ChatColor.UNDERLINE + "ワールドメニューを開く"
                        )
                );
            } else {
                itemMeta4.setLore(
                        Arrays.asList(
                                "" + ChatColor.GRAY + ChatColor.GREEN + ChatColor.UNDERLINE + "資源ワールド" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                                "",
                                ChatColor.GOLD + "左クリック" + ChatColor.GRAY + ": " + ChatColor.GREEN + ChatColor.UNDERLINE + "資源メインにテレポート",
                                ChatColor.GOLD + "右クリック" + ChatColor.GRAY + ": " + ChatColor.GREEN + ChatColor.UNDERLINE + "ワールドメニューを開く"
                        )
                );
            }
        } else {
            itemMeta4.setLore(
                    Arrays.asList(
                            "" + ChatColor.GRAY + ChatColor.GREEN + ChatColor.UNDERLINE + "資源ワールド" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                            "",
                            ChatColor.GOLD + "左クリック" + ChatColor.GRAY + ": " + ChatColor.GREEN + ChatColor.UNDERLINE + "資源メインにテレポート",
                            ChatColor.GOLD + "右クリック" + ChatColor.GRAY + ": " + ChatColor.GREEN + ChatColor.UNDERLINE + "ワールドメニューを開く"
                    )
            );
        }

        itemMeta4.addItemFlags(itemFlags);
        itemStack4.setItemMeta(itemMeta4);
        inv.setItem(Item.WORLD_LIFE_RESOURCE.getIndex(), itemStack4);


        // その他ワールド
        ItemStack itemStack5 = new ItemStack(Item.WORLD_OTHER.getMaterial());
        ItemMeta itemMeta5 = itemStack5.getItemMeta();
        itemMeta5.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "その他ワールド");
        itemMeta5.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.AQUA + ChatColor.UNDERLINE + "その他ワールド" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                        "",
                        ChatColor.GOLD + "左クリック" + ChatColor.GRAY + ": " + ChatColor.AQUA + ChatColor.UNDERLINE + "前回のワールドにテレポート",
                        ChatColor.GOLD + "右クリック" + ChatColor.GRAY + ": " + ChatColor.AQUA + ChatColor.UNDERLINE + "ワールドメニューを開く"
                )
        );
        itemMeta5.addItemFlags(itemFlags);
        itemStack5.setItemMeta(itemMeta5);
        inv.setItem(Item.WORLD_OTHER.getIndex(), itemStack5);

        ItemStack itemStack6 = new ItemStack(Item.NOTIFICATION.getMaterial());
        ItemMeta itemMeta6 = itemStack6.getItemMeta();
        itemMeta6.setDisplayName("" + ChatColor.BLUE + ChatColor.BOLD + ChatColor.UNDERLINE + "通知");
        itemMeta6.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.BLUE + ChatColor.UNDERLINE + "通知メニュー" + ChatColor.RESET + ChatColor.GRAY + "を開きます。"
                )
        );
        itemMeta6.addItemFlags(itemFlags);
        itemStack6.setItemMeta(itemMeta6);
        inv.setItem(Item.NOTIFICATION.getIndex(), itemStack6);

        ItemStack itemStackHome = new ItemStack(Item.HOME.getMaterial());
        ItemMeta itemMetaHome = itemStackHome.getItemMeta();
        itemMetaHome.setDisplayName("" + ChatColor.BLUE + ChatColor.BOLD + ChatColor.UNDERLINE + "ホームに戻る");
        itemMetaHome.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.BLUE + ChatColor.UNDERLINE + "ホームメニュー" + ChatColor.RESET + ChatColor.GRAY + "に戻ります。"
                )
        );
        itemMetaHome.addItemFlags(itemFlags);
        itemStackHome.setItemMeta(itemMetaHome);

        inv.setItem(Item.HOME.getIndex(), itemStackHome);

        ItemStack itemStack7 = new ItemStack(separatorMaterial);
        itemStack7.setDurability(separatorDurability);
        ItemMeta itemMeta7 = itemStack7.getItemMeta();
        itemMeta7.setDisplayName(" ");
        itemMeta7.addItemFlags(itemFlags);
        itemStack7.setItemMeta(itemMeta7);

        for (int i = 9; i < 18; i++)
            inv.setItem(i, itemStack7);
        for (int i = 45; i < 49; i++)
            inv.setItem(i, itemStack7);
        for (int i = 50; i < 54; i++)
            inv.setItem(i, itemStack7);


        if (inventoryType == InventoryType.WORLD_LIFE) {
            // 生活メイン
            ItemStack itemStack8 = new ItemStack(Item.WORLD_LIFE_MAIN.getMaterial());
            ItemMeta itemMeta8 = itemStack8.getItemMeta();
            itemMeta8.setDisplayName("" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "生活メイン");
            itemMeta8.setLore(
                    Arrays.asList(
                            "" + ChatColor.GRAY + ChatColor.YELLOW + ChatColor.UNDERLINE + "生活メイン" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                            "",
                            ChatColor.GRAY + "WorldName: " + ChatColor.UNDERLINE + "lifeMain"
                    )
            );
            itemMeta8.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta8.addItemFlags(itemFlags);
            itemStack8.setItemMeta(itemMeta8);
            inv.setItem(Item.WORLD_LIFE_MAIN.getIndex(), itemStack8);


            // 生活サブ
            ItemStack itemStack9 = new ItemStack(Item.WORLD_LIFE_SUB.getMaterial());
            ItemMeta itemMeta9 = itemStack9.getItemMeta();
            itemMeta9.setDisplayName("" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.UNDERLINE + "生活サブ");
            itemMeta9.setLore(
                    Arrays.asList(
                            "" + ChatColor.GRAY + ChatColor.YELLOW + ChatColor.UNDERLINE + "生活サブ" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                            "",
                            ChatColor.GRAY + "WorldName: " + ChatColor.UNDERLINE + "lifeSub"
                    )
            );
            itemMeta9.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta9.addItemFlags(itemFlags);
            itemStack9.setItemMeta(itemMeta9);
            inv.setItem(Item.WORLD_LIFE_SUB.getIndex(), itemStack9);
        } else if (inventoryType == InventoryType.WORLD_LIFE_RESOURCE) {
            // 資源メイン
            ItemStack itemStack8 = new ItemStack(Item.WORLD_LIFE_RESOURCE_MAIN.getMaterial());
            ItemMeta itemMeta8 = itemStack8.getItemMeta();
            itemMeta8.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "資源メイン");
            itemMeta8.setLore(
                    Arrays.asList(
                            "" + ChatColor.GRAY + ChatColor.GREEN + ChatColor.UNDERLINE + "資源メイン" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                            "",
                            ChatColor.GRAY + "WorldName: " + ChatColor.UNDERLINE + "lifeResourceMain"
                    )
            );
            itemMeta8.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta8.addItemFlags(itemFlags);
            itemStack8.setItemMeta(itemMeta8);
            inv.setItem(Item.WORLD_LIFE_RESOURCE_MAIN.getIndex(), itemStack8);


            // 資源サブ
            ItemStack itemStack9 = new ItemStack(Item.WORLD_LIFE_RESOURCE_SUB.getMaterial());
            ItemMeta itemMeta9 = itemStack9.getItemMeta();
            itemMeta9.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "資源サブ");
            itemMeta9.setLore(
                    Arrays.asList(
                            "" + ChatColor.GRAY + ChatColor.GREEN + ChatColor.UNDERLINE + "資源サブ" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                            "",
                            ChatColor.GRAY + "WorldName: " + ChatColor.UNDERLINE + "lifeResourceSub"
                    )
            );
            itemMeta9.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta9.addItemFlags(itemFlags);
            itemStack9.setItemMeta(itemMeta9);
            inv.setItem(Item.WORLD_LIFE_RESOURCE_SUB.getIndex(), itemStack9);


            // 資源ネザー
            ItemStack itemStack10 = new ItemStack(Item.WORLD_LIFE_RESOURCE_NETHER.getMaterial());
            ItemMeta itemMeta10 = itemStack10.getItemMeta();
            itemMeta10.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "資源ネザー");
            itemMeta10.setLore(
                    Arrays.asList(
                            "" + ChatColor.GRAY + ChatColor.GREEN + ChatColor.UNDERLINE + "資源ネザー" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                            "",
                            ChatColor.GRAY + "WorldName: " + ChatColor.UNDERLINE + "lifeResourceNether"
                    )
            );
            itemMeta10.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta10.addItemFlags(itemFlags);
            itemStack10.setItemMeta(itemMeta10);
            inv.setItem(Item.WORLD_LIFE_RESOURCE_NETHER.getIndex(), itemStack10);


            // 資源エンド
            ItemStack itemStack11 = new ItemStack(Item.WORLD_LIFE_RESOURCE_END.getMaterial());
            ItemMeta itemMeta11 = itemStack11.getItemMeta();
            itemMeta11.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "資源エンド");
            itemMeta11.setLore(
                    Arrays.asList(
                            "" + ChatColor.GRAY + ChatColor.GREEN + ChatColor.UNDERLINE + "資源エンド" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                            "",
                            ChatColor.GRAY + "WorldName: " + ChatColor.UNDERLINE + "lifeResourceEnd"
                    )
            );
            itemMeta11.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta11.addItemFlags(itemFlags);
            itemStack11.setItemMeta(itemMeta11);
            inv.setItem(Item.WORLD_LIFE_RESOURCE_END.getIndex(), itemStack11);
        }
        return inv;
    }

    public static String getTitle() {
        return title;
    }

    public static int getSize() {
        return invSize;
    }

    public static void openInventory(Player p, InventoryType inventoryType) {
        p.openInventory(getInventory(p, inventoryType));
    }

    public enum Item {
        PLAYER_INFO(1, 1, Material.PLAYER_HEAD),
        WORLD_LOBBY(3, 1, Material.ENDER_PEARL),
        WORLD_LIFE(5, 1, Material.GOLDEN_AXE),
        WORLD_LIFE_RESOURCE(6, 1, Material.IRON_PICKAXE),
        WORLD_OTHER(7, 1, Material.DIAMOND_SWORD),
        NOTIFICATION(9, 1, Material.CHEST),
        WORLD_LIFE_MAIN(3, 4, Material.OAK_PLANKS),
        WORLD_LIFE_SUB(7, 4, Material.STONE_BRICKS),
        WORLD_LIFE_RESOURCE_MAIN(2, 4, Material.GRASS_BLOCK),
        WORLD_LIFE_RESOURCE_SUB(4, 4, Material.STONE),
        WORLD_LIFE_RESOURCE_NETHER(6, 4, Material.NETHERRACK),
        WORLD_LIFE_RESOURCE_END(8, 4, Material.END_STONE),
        HOME(5, 6, Material.BLUE_STAINED_GLASS_PANE),
        UNKNOWN(0, 0, Material.AIR);

        private final int x;
        private final int y;
        private final Material material;

        private Item(int x, int y, Material material) {
            this.x = x;
            this.y = y;
            this.material = material;
        }

        public int getIndex() {
            return getX() + getY();
        }

        public int getX() {
            return MainAPI.makePositive(x - 1);
        }

        public int getY() {
            return MainAPI.makePositive(y - 1) * 9;
        }

        public Material getMaterial() {
            return material;
        }

        public static Item getItem(int slot) {
            for (Item item : Item.values())
                if (item.getIndex() == slot)
                    return item;
            return Item.UNKNOWN;
        }
    }

    public enum InventoryType {
        WORLD_LIFE,
        WORLD_LIFE_RESOURCE,
        WORLD_OTHER
    }
}
