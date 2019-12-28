package org.aoichaan0513.a_core;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.WorldGuard;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.aoichaan0513.a_core.API.IConfig;
import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.Command.*;
import org.aoichaan0513.a_core.Listeners.GateListener;
import org.aoichaan0513.a_core.Listeners.InventoryListener;
import org.aoichaan0513.a_core.Listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public final class Main extends JavaPlugin {

    private static JavaPlugin plugin;

    private static FileConfiguration mainConfig;

    private static IConfig homeConfig;
    private static IConfig worldConfig;
    private static IConfig gateConfig;

    public static Material menuItem;
    public static Material wandItem;
    public static Material gateItem;

    private static LuckPermsApi luckPerms;
    private static WorldEdit worldEdit;
    private static WorldGuard worldGuard;

    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "プラグインを起動しました。");

        saveDefaultConfig();
        mainConfig = getConfig();
        homeConfig = loadConfig("homes");
        worldConfig = loadConfig("worlds");
        gateConfig = loadConfig("gates");

        if (mainConfig.getBoolean("hooks.luckPerms", false)) {
            final String pluginName = "LuckPerms";

            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能が有効になっています。");
            if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
                Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + pluginName + " が有効になっています。\n" +
                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.GREEN + ChatColor.UNDERLINE + "有効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");

                luckPerms = LuckPerms.getApi();
            } else {
                Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + pluginName + " が導入されてないか有効になっていません。\n" +
                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.RED + ChatColor.UNDERLINE + "無効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");
            }
        }
        if (mainConfig.getBoolean("hooks.worldEdit", false)) {
            final String pluginName = "WorldEdit";

            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能が有効になっています。");
            if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
                Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + pluginName + " が有効になっています。\n" +
                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.GREEN + ChatColor.UNDERLINE + "有効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");

                worldEdit = WorldEdit.getInstance();
            } else {
                Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + pluginName + " が導入されてないか有効になっていません。\n" +
                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.RED + ChatColor.UNDERLINE + "無効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");
            }
        }
        if (mainConfig.getBoolean("hooks.worldGuard", false)) {
            final String pluginName = "WorldGuard";

            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能が有効になっています。");
            if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
                Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + pluginName + " が有効になっています。\n" +
                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.GREEN + ChatColor.UNDERLINE + "有効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");

                worldGuard = WorldGuard.getInstance();
            } else {
                Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + pluginName + " が導入されてないか有効になっていません。\n" +
                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.RED + ChatColor.UNDERLINE + "無効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");
            }
        }
        if (mainConfig.getBoolean("hooks.vault", false)) {
            final String pluginName = "Vault";

            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能が有効になっています。");
            if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
                if (setupEconomy()) {
                    Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + pluginName + " が有効になっています。\n" +
                            MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.GREEN + ChatColor.UNDERLINE + "有効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");

                    setupPermissions();
                    setupChat();
                } else {
                    Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + pluginName + " が導入されてないか有効になっていません。\n" +
                            MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.RED + ChatColor.UNDERLINE + "無効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");
                }
            } else {
                Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + pluginName + " が導入されてないか有効になっていません。\n" +
                        MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + pluginName + " との連携機能を" + ChatColor.RED + ChatColor.UNDERLINE + "無効" + ChatColor.RESET + ChatColor.GRAY + "にして起動します。");
            }
        }

        menuItem = Material.matchMaterial(mainConfig.getString("menu.item", "NETHER_STAR"));
        wandItem = Material.matchMaterial(mainConfig.getString("inventorySort.wandItem", "STICK"));
        gateItem = Material.matchMaterial(mainConfig.getString("ticketGate.card.item", "PAPER"));

        getCommand("mode").setExecutor(new Mode("mode"));

        getCommand("gamemode").setExecutor(new Gamemode("gamemode"));
        getCommand("disappear").setExecutor(new Disappear("disappear"));
        getCommand("appear").setExecutor(new Appear("appear"));

        getCommand("time").setExecutor(new Time("time"));
        getCommand("send").setExecutor(new Send("send"));

        getCommand("menu").setExecutor(new Menu("menu"));

        getCommand("crafttable").setExecutor(new CraftTable("crafttable"));
        getCommand("enderchest").setExecutor(new EnderChest("enderchest"));
        getCommand("sort").setExecutor(new Sort("sort"));

        getCommand("home").setExecutor(new Home("home"));

        getCommand("lobby").setExecutor(new Lobby("lobby"));
        getCommand("spawn").setExecutor(new Spawn("spawn"));


        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);

        pluginManager.registerEvents(new GateListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "プラグインを終了しました。");
    }

    public static JavaPlugin getInstance() {
        return plugin;
    }

    public static LuckPermsApi getLuckPerms() {
        return luckPerms;
    }

    public static WorldEdit getWorldEdit() {
        return worldEdit;
    }

    public static WorldGuard getWorldGuard() {
        return worldGuard;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    public static FileConfiguration getMainConfig() {
        return mainConfig;
    }

    public static FileConfiguration setMainConfig() {
        getInstance().saveConfig();
        mainConfig = getInstance().getConfig();
        return mainConfig;
    }

    public static FileConfiguration getHomeConfig() {
        return homeConfig.getConfig();
    }

    public static FileConfiguration setHomeConfig() {
        try {
            homeConfig.getConfig().save(homeConfig.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeConfig = loadConfig("homes");
        return homeConfig.getConfig();
    }

    public static FileConfiguration getWorldConfig() {
        return worldConfig.getConfig();
    }

    public static FileConfiguration setWorldConfig() {
        try {
            worldConfig.getConfig().save(worldConfig.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        worldConfig = loadConfig("worlds");
        return worldConfig.getConfig();
    }

    public static FileConfiguration getGateConfig() {
        return gateConfig.getConfig();
    }

    public static FileConfiguration setGateConfig() {
        try {
            gateConfig.getConfig().save(gateConfig.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gateConfig = loadConfig("gates");
        return gateConfig.getConfig();
    }

    private static IConfig loadConfig(String fileName) {
        final String filePath = ".yml";

        File file = new File(getInstance().getDataFolder() + FileSystems.getDefault().getSeparator() + fileName + filePath);
        if (file.exists()) {
            return new IConfig(fileName + filePath, file, YamlConfiguration.loadConfiguration(file));
        } else {
            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "設定ファイル\"" + fileName + filePath + "\"を作成します…");
            getInstance().saveResource(fileName + filePath, false);
            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + "設定ファイル\"" + fileName + filePath + "\"を作成しました。");
            return new IConfig(fileName + filePath, file, YamlConfiguration.loadConfiguration(file));
        }
    }

    private static void loadFile(String folderName, String fileName) {
        File file = new File(getInstance().getDataFolder() + FileSystems.getDefault().getSeparator() + folderName, fileName);
        if (!file.exists()) {
            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "ファイル\"" + fileName + "\"を作成します…");
            getInstance().saveResource(folderName + FileSystems.getDefault().getSeparator() + fileName, false);
            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + "ファイル\"" + fileName + "\"を作成しました。");
        } else {
            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.PRIMARY) + "ファイル\"" + fileName + "\"が見つかったためスルーしました。");
        }
    }

    private static void loadFolder(String folderName) {
        File file = new File(getInstance().getDataFolder() + FileSystems.getDefault().getSeparator() + folderName);
        if (file.mkdir())
            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + "フォルダ\"" + folderName + "\"を作成しました。");
        else
            Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "フォルダ\"" + folderName + "\"を作成できませんでした。");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                econ = rsp.getProvider();
                return econ != null;
            }
        }
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}
