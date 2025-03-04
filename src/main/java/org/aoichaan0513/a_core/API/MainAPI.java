package org.aoichaan0513.a_core.API;

import org.aoichaan0513.a_core.Main;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class MainAPI {

    public static int makePositive(int i) {
        return i < 0 ? 0 : i;
    }

    /**
     * プレイヤー非表示関連
     */
    private static List<UUID> hidePlayerList = new ArrayList<>();

    public static void hidePlayers(Player p) {
        for (UUID uuid : hidePlayerList) {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null) continue;
            p.hidePlayer(player);
        }
    }

    public static List<UUID> getHidePlayerList() {
        return hidePlayerList;
    }

    public static boolean isHidePlayer(Player p) {
        return hidePlayerList.contains(p.getUniqueId());
    }

    public static void addHidePlayer(Player p) {
        addHidePlayer(p, true);
    }

    public static void addHidePlayer(Player p, boolean b) {
        if (hidePlayerList.contains(p.getUniqueId())) return;
        hidePlayerList.add(p.getUniqueId());

        if (b) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                hidePlayers(player);
            }
        }
    }

    public static void removeHidePlayer(Player p) {
        removeHidePlayer(p, true);
    }

    public static void removeHidePlayer(Player p, boolean b) {
        if (!hidePlayerList.contains(p.getUniqueId())) return;
        hidePlayerList.remove(p.getUniqueId());

        if (b) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.showPlayer(p);
                hidePlayers(player);
            }
        }
    }

    /**
     * 権限関係
     */
    private static List<UUID> adminPlayerList = new ArrayList<>();

    public static boolean isAdminMode(Player p) {
        return p.isOp() && adminPlayerList.contains(p.getUniqueId());
    }

    public static List<UUID> getAdminPlayerList() {
        return adminPlayerList;
    }

    public static boolean isAdminPlayer(Player p) {
        return adminPlayerList.contains(p.getUniqueId());
    }

    public static void addAdminPlayer(Player p) {
        if (adminPlayerList.contains(p.getUniqueId())) return;
        adminPlayerList.add(p.getUniqueId());
    }

    public static void removeAdminPlayer(Player p) {
        if (!adminPlayerList.contains(p.getUniqueId())) return;
        adminPlayerList.remove(p.getUniqueId());
    }

    public static boolean isAdmin(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            return isAdmin(p);
        } else {
            return true;
        }
    }

    public static boolean isAdmin(Player p) {
        return p.isOp() || p.hasPermission("a_core.*");
    }

    /**
     * プレイヤー系
     */
    public static boolean isOnlinePlayer(Player p) {
        return p != null;
    }

    public static boolean isOnlinePlayer(OfflinePlayer p) {
        return p != null;
    }

    public static boolean isOnlinePlayer(String name) {
        Player target = Bukkit.getPlayerExact(name);
        return target != null;
    }

    public static boolean isPlayed(Player p) {
        return p.getPlayer() != null || p.hasPlayedBefore();
    }

    public static boolean isPlayed(OfflinePlayer p) {
        return p.getPlayer() != null || p.hasPlayedBefore();
    }

    /**
     * アドレス系
     */
    public static int getRegion(InetSocketAddress address) {
        try {
            URL url = new URL("http://ip-api.com/json/" + address.getHostName());
            BufferedReader stream = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            StringBuilder entirePage = new StringBuilder();
            String inputLine;
            while ((inputLine = stream.readLine()) != null)
                entirePage.append(inputLine);
            stream.close();

            final String type = AddressAPIType.REGION.getName();

            JSONObject jsonObject = new JSONObject(entirePage.toString());
            if (jsonObject.has(type))
                return jsonObject.getInt(type);
            else
                return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getRegionName(InetSocketAddress address) {
        try {
            URL url = new URL("http://ip-api.com/json/" + address.getHostName());
            BufferedReader stream = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            StringBuilder entirePage = new StringBuilder();
            String inputLine;
            while ((inputLine = stream.readLine()) != null)
                entirePage.append(inputLine);
            stream.close();

            final String type = AddressAPIType.REGION_NAME.getName();

            JSONObject jsonObject = new JSONObject(entirePage.toString());
            if (jsonObject.has(type))
                return jsonObject.getString(type);
            else
                return "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String getCountry(InetSocketAddress address) {
        try {
            URL url = new URL("http://ip-api.com/json/" + address.getHostName());
            BufferedReader stream = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            StringBuilder entirePage = new StringBuilder();
            String inputLine;
            while ((inputLine = stream.readLine()) != null)
                entirePage.append(inputLine);
            stream.close();

            final String type = AddressAPIType.COUNTRY.getName();

            JSONObject jsonObject = new JSONObject(entirePage.toString());
            if (jsonObject.has(type))
                return jsonObject.getString(type);
            else
                return "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String getCountryCode(InetSocketAddress address) {
        try {
            URL url = new URL("http://ip-api.com/json/" + address.getHostName());
            BufferedReader stream = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            StringBuilder entirePage = new StringBuilder();
            String inputLine;
            while ((inputLine = stream.readLine()) != null)
                entirePage.append(inputLine);
            stream.close();

            final String type = AddressAPIType.COUNTRY_CODE.getName();

            JSONObject jsonObject = new JSONObject(entirePage.toString());
            if (jsonObject.has(type))
                return jsonObject.getString(type);
            else
                return "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    public enum AddressAPIType {
        REGION("region"),
        REGION_NAME("regionName"),
        COUNTRY("country"),
        COUNTRY_CODE("countryCode");

        private final String name;

        private AddressAPIType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * プレースホルダー
     */
    public static String setPlaceholder(String str, Player player) {
        HashMap<Pattern, String> hashMap = new HashMap<Pattern, String>() {
            {
                put(Pattern.compile("%player:name%"), player.getName());
                put(Pattern.compile("%player:uuid%"), player.getUniqueId().toString());
                put(Pattern.compile("%player:locale%"), player.getLocale());
                put(Pattern.compile("%player:address%"), player.getAddress().getAddress().getHostAddress());
                put(Pattern.compile("%player:displayName%"), player.getDisplayName());
                put(Pattern.compile("%player:tabListName%"), player.getPlayerListName());
                put(Pattern.compile("%country%"), getCountry(player.getAddress()));
                put(Pattern.compile("%country%"), getCountry(player.getAddress()));
                put(Pattern.compile("%countryCode%"), getCountryCode(player.getAddress()));
                put(Pattern.compile("%region%"), String.valueOf(getRegion(player.getAddress())));
                put(Pattern.compile("%regionName%"), getRegionName(player.getAddress()));
            }
        };

        String s = str;
        for (Map.Entry<Pattern, String> entry : hashMap.entrySet())
            s = entry.getKey().matcher(s).replaceFirst(entry.getValue());

        return s;
    }

    public static String getText(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * プレフィックス系
     */
    public static String getPrefix(PrefixType prefixType) {
        return getPrefix(prefixType.getBackColor(), prefixType.getForwardColor());
    }

    public static String getPrefix(ChatColor color) {
        return getPrefix(color, color);
    }

    public static String getPrefix(ChatColor backColor, ChatColor forwardColor) {
        return backColor + "> " + forwardColor;
    }

    public enum PrefixType {
        PRIMARY(ChatColor.BLUE),
        SECONDARY(ChatColor.GRAY),
        SUCCESS(ChatColor.GREEN),
        WARNING(ChatColor.YELLOW),
        ERROR(ChatColor.RED);

        private final ChatColor backColor;
        private final ChatColor forwardColor;

        private PrefixType(ChatColor backColor) {
            this.backColor = backColor;
            this.forwardColor = backColor;
        }

        private PrefixType(ChatColor backColor, ChatColor forwardColor) {
            this.backColor = backColor;
            this.forwardColor = forwardColor;
        }

        public ChatColor getBackColor() {
            return backColor;
        }

        public ChatColor getForwardColor() {
            return forwardColor;
        }
    }

    public enum ErrorType {
        ARGS(ChatColor.RED + "引数が不正です。"),
        PERMISSION(ChatColor.RED + "権限がありません。"),
        PLAYER(ChatColor.RED + "プレイヤー以外は実行できません。"),
        NUMBER(ChatColor.RED + "数値以外は指定できません。");

        private final String message;

        private ErrorType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum Gamemode {
        SURVIVAL(GameMode.SURVIVAL, 0, "s", "サバイバル"),
        CREATIVE(GameMode.CREATIVE, 1, "c", "クリエイティブ"),
        ADVENTURE(GameMode.ADVENTURE, 2, "a", "アドベンチャー"),
        SPECTATOR(GameMode.SPECTATOR, 3, "sp", "スペクテイター");

        private final GameMode gameMode;
        private final int modeId;
        private final String modeAlias;
        private final String name;

        private Gamemode(GameMode gameMode, int modeId, String modeAlias, String name) {
            this.gameMode = gameMode;
            this.modeId = modeId;
            this.modeAlias = modeAlias;
            this.name = name;
        }

        public GameMode getGameMode() {
            return gameMode;
        }

        public int getModeId() {
            return modeId;
        }

        public String getModeAlias() {
            return modeAlias;
        }

        public String getName() {
            return name;
        }

        public static Gamemode getGamemode(GameMode gameMode) {
            for (Gamemode gamemode : Gamemode.values())
                if (gamemode.getGameMode() == gameMode)
                    return gamemode;
            return Gamemode.SURVIVAL;
        }
    }

    public enum WorldType {
        WORLD_LOBBY(Main.getMainConfig().getString("lobbyName"), "ロビー", "lobby"),
        WORLD_LIFE_MAIN("lifeMain", "生活メイン", "life"),
        WORLD_LIFE_SUB("lifeSub", "生活サブ", "life"),
        WORLD_LIFE_RESOURCE_MAIN("lifeResourceMain", "資源メイン", "lifeResource"),
        WORLD_LIFE_RESOURCE_SUB("lifeResourceSub", "資源サブ", "lifeResource"),
        WORLD_LIFE_RESOURCE_NETHER("lifeResourceNether", "資源ネザー", "lifeResource"),
        WORLD_LIFE_RESOURCE_END("lifeResourceEnd", "資源エンド", "lifeResource");

        private final String id;
        private final String name;
        private final String type;

        private WorldType(String id, String name, String type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public boolean isWorldLoaded() {
            return Bukkit.getWorld(id) != null;
        }

        public World getWorld() {
            return Bukkit.getWorld(id);
        }

        public static WorldType getWorld(String id) {
            for (WorldType worldType : WorldType.values())
                if (worldType.getId().equalsIgnoreCase(id))
                    return worldType;
            return null;
        }
    }
}
