package org.aoichaan0513.a_core.Inventorys;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;

public class MainInventory {

    private static final String title = ChatColor.DARK_GRAY + "> " + ChatColor.BOLD + ChatColor.UNDERLINE + "ホーム";
    private static final int invSize = 9 * 6;

    private static final Material separatorMaterial = Material.BLACK_STAINED_GLASS_PANE;
    private static final byte separatorDurability = new Wool(DyeColor.BLACK).getData();

    public static Inventory getInventory(Player p) {
        Inventory inv = Bukkit.createInventory(new MenuInventoryHolder(invSize), invSize, title);
        ItemFlag[] itemFlags = new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS};

        FileConfiguration homeConfig = Main.getHomeConfig();
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

        ItemStack itemStack7 = new ItemStack(separatorMaterial);
        itemStack7.setDurability(separatorDurability);
        ItemMeta itemMeta7 = itemStack7.getItemMeta();
        itemMeta7.setDisplayName(" ");
        itemMeta7.addItemFlags(itemFlags);
        itemStack7.setItemMeta(itemMeta7);

        for (int i = 9; i < 18; i++)
            inv.setItem(i, itemStack7);
        for (int i = 45; i < 54; i++)
            inv.setItem(i, itemStack7);

        // メール
        ItemStack itemStack8 = new ItemStack(Item.WORLDGUARD_MENU.getMaterial());
        ItemMeta itemMeta8 = itemStack8.getItemMeta();
        itemMeta8.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ChatColor.UNDERLINE + "保護管理メニュー");
        itemMeta8.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "保護管理メニュー" + ChatColor.RESET + ChatColor.GRAY + "を開きます。",
                        ChatColor.GRAY + "保護の" + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "登録" + ChatColor.RESET + ChatColor.GRAY + "・" + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "編集" + ChatColor.RESET + ChatColor.GRAY + "などが簡単にできます。"
                )
        );
        itemMeta8.addItemFlags(itemFlags);
        itemStack8.setItemMeta(itemMeta8);
        inv.setItem(Item.WORLDGUARD_MENU.getIndex(), itemStack8);


        // メール
        ItemStack itemStack9 = new ItemStack(Item.MAIL_MENU.getMaterial());
        ItemMeta itemMeta9 = itemStack9.getItemMeta();
        itemMeta9.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ChatColor.UNDERLINE + "メール");
        itemMeta9.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "メールメニュー" + ChatColor.RESET + ChatColor.GRAY + "を開きます。",
                        ChatColor.GRAY + "メールの" + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "送信" + ChatColor.RESET + ChatColor.GRAY + "・" + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "確認" + ChatColor.RESET + ChatColor.GRAY + "などが簡単にできます。"
                )
        );
        itemMeta9.addItemFlags(itemFlags);
        itemStack9.setItemMeta(itemMeta9);
        inv.setItem(Item.MAIL_MENU.getIndex(), itemStack9);


        // 作業台
        ItemStack itemStack10 = new ItemStack(Item.CRAFTTABLE.getMaterial());
        ItemMeta itemMeta10 = itemStack10.getItemMeta();
        itemMeta10.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ChatColor.UNDERLINE + "作業台");
        itemMeta10.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "作業台" + ChatColor.RESET + ChatColor.GRAY + "を開きます。"
                )
        );
        itemMeta10.addItemFlags(itemFlags);
        itemStack10.setItemMeta(itemMeta10);
        inv.setItem(Item.CRAFTTABLE.getIndex(), itemStack10);


        // メール
        ItemStack itemStack11 = new ItemStack(Item.ENDER_CHEST.getMaterial());
        ItemMeta itemMeta11 = itemStack11.getItemMeta();
        itemMeta11.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ChatColor.UNDERLINE + "エンダーチェスト");
        itemMeta11.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "エンダーチェスト" + ChatColor.RESET + ChatColor.GRAY + "を開きます。"
                )
        );
        itemMeta11.addItemFlags(itemFlags);
        itemStack11.setItemMeta(itemMeta11);
        inv.setItem(Item.ENDER_CHEST.getIndex(), itemStack11);


        // インベントリを整理
        ItemStack itemStack12 = new ItemStack(Item.SORT_INVENTORY.getMaterial());
        ItemMeta itemMeta12 = itemStack12.getItemMeta();
        itemMeta12.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ChatColor.UNDERLINE + "インベントリを整理");
        itemMeta12.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "インベントリを整理" + ChatColor.RESET + ChatColor.GRAY + "します。",
                        "",
                        ChatColor.GRAY + "\"" + ChatColor.UNDERLINE + "/sort all" + ChatColor.RESET + ChatColor.GRAY + "\"と実行しても整理ができます。"
                )
        );
        itemMeta12.addItemFlags(itemFlags);
        itemStack12.setItemMeta(itemMeta12);
        inv.setItem(Item.SORT_INVENTORY.getIndex(), itemStack12);


        // チェストを整理
        ItemStack itemStack13 = new ItemStack(Item.SORT_CHEST.getMaterial());
        ItemMeta itemMeta13 = itemStack13.getItemMeta();
        itemMeta13.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ChatColor.UNDERLINE + "チェストを整理");
        itemMeta13.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "チェストを整理" + ChatColor.RESET + ChatColor.GRAY + "します。",
                        ChatColor.GRAY + "整理したいチェストに目線を合わせて実行してください。",
                        "",
                        "" + ChatColor.GRAY + ChatColor.DARK_PURPLE + ChatColor.UNDERLINE + "整理できるブロック",
                        "" + ChatColor.RESET + ChatColor.GRAY + "・チェスト",
                        "" + ChatColor.RESET + ChatColor.GRAY + "・トラップチェスト",
                        "" + ChatColor.RESET + ChatColor.GRAY + "・エンダーチェスト",
                        "" + ChatColor.RESET + ChatColor.GRAY + "・シュルカーボックス",
                        "",
                        ChatColor.GRAY + "\"" + ChatColor.UNDERLINE + "/sort chest" + ChatColor.RESET + ChatColor.GRAY + "\"と実行しても整理ができます。"
                )
        );
        itemMeta13.addItemFlags(itemFlags);
        itemStack13.setItemMeta(itemMeta13);
        inv.setItem(Item.SORT_CHEST.getIndex(), itemStack13);


        // ホーム1
        ItemStack itemStack14 = new ItemStack(homeConfig.contains("homes." + p.getUniqueId().toString() + ".home1") ? Item.HOME_1.getMaterial() : Material.WHITE_BED);
        // itemStack14.setDurability(homeConfig.contains("homes." + p.getUniqueId().toString() + ".home1") ? new Wool(DyeColor.RED).getData() : new Wool(DyeColor.WHITE).getData());
        ItemMeta itemMeta14 = itemStack14.getItemMeta();
        itemMeta14.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "ホーム1");
        itemMeta14.setLore(
                homeConfig.contains("homes." + p.getUniqueId().toString() + ".home1") ?
                        Arrays.asList(
                                "" + ChatColor.GRAY + ChatColor.RED + ChatColor.UNDERLINE + "ホーム1" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                                "",
                                ChatColor.GRAY + "設定・削除をする場合は右の" + ChatColor.RED + ChatColor.UNDERLINE + "ホーム設定" + ChatColor.RESET + ChatColor.GRAY + "から実行してください。"
                        )
                        :
                        Arrays.asList(
                                ChatColor.GRAY + "" + ChatColor.RED + ChatColor.UNDERLINE + "ホーム1" + ChatColor.RESET + ChatColor.GRAY + "を現在の位置に設定します。"
                        )
        );
        itemMeta14.addItemFlags(itemFlags);
        itemStack14.setItemMeta(itemMeta14);
        inv.setItem(Item.HOME_1.getIndex(), itemStack14);


        // ホーム2
        ItemStack itemStack15 = new ItemStack(homeConfig.contains("homes." + p.getUniqueId().toString() + ".home2") ? Item.HOME_2.getMaterial() : Material.WHITE_BED);
        // itemStack15.setDurability(homeConfig.contains("homes." + p.getUniqueId().toString() + ".home2") ? new Wool(DyeColor.ORANGE).getData() : new Wool(DyeColor.WHITE).getData());
        ItemMeta itemMeta15 = itemStack15.getItemMeta();
        itemMeta15.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "ホーム2");
        itemMeta15.setLore(
                homeConfig.contains("homes." + p.getUniqueId().toString() + ".home2") ?
                        Arrays.asList(
                                "" + ChatColor.GRAY + ChatColor.RED + ChatColor.UNDERLINE + "ホーム2" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                                "",
                                ChatColor.GRAY + "設定・削除をする場合は右の" + ChatColor.RED + ChatColor.UNDERLINE + "ホーム設定" + ChatColor.RESET + ChatColor.GRAY + "から実行してください。"
                        )
                        :
                        Arrays.asList(
                                ChatColor.GRAY + "" + ChatColor.RED + ChatColor.UNDERLINE + "ホーム2" + ChatColor.RESET + ChatColor.GRAY + "を現在の位置に設定します。"
                        )
        );
        itemMeta15.addItemFlags(itemFlags);
        itemStack15.setItemMeta(itemMeta15);
        inv.setItem(Item.HOME_2.getIndex(), itemStack15);


        // ホーム3
        ItemStack itemStack16 = new ItemStack(homeConfig.contains("homes." + p.getUniqueId().toString() + ".home3") ? Item.HOME_3.getMaterial() : Material.WHITE_BED);
        // itemStack16.setDurability(homeConfig.contains("homes." + p.getUniqueId().toString() + ".home3") ? new Wool(DyeColor.YELLOW).getData() : new Wool(DyeColor.WHITE).getData());
        ItemMeta itemMeta16 = itemStack16.getItemMeta();
        itemMeta16.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "ホーム3");
        itemMeta16.setLore(
                homeConfig.contains("homes." + p.getUniqueId().toString() + ".home3") ?
                        Arrays.asList(
                                "" + ChatColor.GRAY + ChatColor.RED + ChatColor.UNDERLINE + "ホーム3" + ChatColor.RESET + ChatColor.GRAY + "にテレポートします。",
                                "",
                                ChatColor.GRAY + "設定・削除をする場合は右の" + ChatColor.RED + ChatColor.UNDERLINE + "ホーム設定" + ChatColor.RESET + ChatColor.GRAY + "から実行してください。"
                        )
                        :
                        Arrays.asList(
                                ChatColor.GRAY + "" + ChatColor.RED + ChatColor.UNDERLINE + "ホーム3" + ChatColor.RESET + ChatColor.GRAY + "を現在の位置に設定します。"
                        )
        );
        itemMeta13.addItemFlags(itemFlags);
        itemStack16.setItemMeta(itemMeta16);
        inv.setItem(Item.HOME_3.getIndex(), itemStack16);


        // ホーム設定
        ItemStack itemStack17 = new ItemStack(Item.HOME_MENU.getMaterial());
        // itemStack17.setDurability(new Wool(DyeColor.LIME).getData());
        ItemMeta itemMeta17 = itemStack17.getItemMeta();
        itemMeta17.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "ホーム設定");
        itemMeta17.setLore(
                Arrays.asList(
                        "" + ChatColor.GRAY + ChatColor.RED + ChatColor.UNDERLINE + "ホーム設定" + ChatColor.RESET + ChatColor.GRAY + "を開きます。"
                )
        );
        itemMeta17.addItemFlags(itemFlags);
        itemStack17.setItemMeta(itemMeta17);
        itemStack17.setItemMeta(itemMeta17);
        inv.setItem(Item.HOME_MENU.getIndex(), itemStack17);
        return inv;
    }

    public static String getTitle() {
        return title;
    }

    public static int getSize() {
        return invSize;
    }

    public static void openInventory(Player p) {
        p.openInventory(getInventory(p));
    }

    public enum Item {
        PLAYER_INFO(1, 1, Material.PLAYER_HEAD),
        WORLD_LOBBY(3, 1, Material.ENDER_PEARL),
        WORLD_LIFE(5, 1, Material.GOLDEN_AXE),
        WORLD_LIFE_RESOURCE(6, 1, Material.IRON_PICKAXE),
        WORLD_OTHER(7, 1, Material.DIAMOND_SWORD),
        NOTIFICATION(9, 1, Material.CHEST),
        WORLDGUARD_MENU(1, 3, Material.WOODEN_AXE),
        MAIL_MENU(3, 3, Material.PAPER),
        CRAFTTABLE(5, 3, Material.CRAFTING_TABLE),
        ENDER_CHEST(6, 3, Material.ENDER_CHEST),
        SORT_INVENTORY(8, 3, Material.STICK),
        SORT_CHEST(9, 3, Material.WHITE_SHULKER_BOX),
        HOME_1(1, 5, Material.RED_BED),
        HOME_2(2, 5, Material.ORANGE_BED),
        HOME_3(3, 5, Material.YELLOW_BED),
        HOME_MENU(5, 5, Material.LIME_BED),
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
}
