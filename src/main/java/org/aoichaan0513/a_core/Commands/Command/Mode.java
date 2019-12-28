package org.aoichaan0513.a_core.Commands.Command;

import org.aoichaan0513.a_core.API.MainAPI;
import org.aoichaan0513.a_core.Commands.ICommand;
import org.aoichaan0513.a_core.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class Mode extends ICommand {

    public Mode(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        FileConfiguration fileConfiguration = Main.getMainConfig();

        if (MainAPI.isAdmin(sp) && fileConfiguration.getStringList("isAllowChangeModes").contains(sp.getUniqueId().toString())) {
            if (!MainAPI.isAdminPlayer(sp))
                MainAPI.addAdminPlayer(sp);
            else
                MainAPI.removeAdminPlayer(sp);
            sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "モードを" + ChatColor.GOLD + ChatColor.UNDERLINE + (MainAPI.isAdminPlayer(sp) ? "運営" : "メンバー") + ChatColor.RESET + ChatColor.GRAY + "に変更しました。");
            return;
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
}
