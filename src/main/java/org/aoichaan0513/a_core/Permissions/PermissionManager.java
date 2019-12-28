package org.aoichaan0513.a_core.Permissions;

import org.aoichaan0513.a_core.Main;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class PermissionManager {

    public static HashMap<UUID, PermissionAttachment> hashMap = new HashMap<>();

    public static PermissionAttachment addAttachment(Player p) {
        PermissionAttachment attachment = p.addAttachment(Main.getInstance());
        if (!isContainedMap(p)) {
            hashMap.put(p.getUniqueId(), attachment);
            return attachment;
        } else {
            return getAttachment(p, false);
        }
    }

    public static PermissionAttachment getAttachment(Player p) {
        return getAttachment(p, true);
    }

    public static PermissionAttachment getAttachment(Player p, boolean add) {
        if (isContainedMap(p)) {
            return hashMap.get(p.getUniqueId());
        } else {
            if (add)
                return addAttachment(p);
            else
                return null;
        }
    }

    public static void removeAttachment(Player p) {
        if (isContainedMap(p))
            hashMap.remove(p.getUniqueId());
        return;
    }

    private static boolean isContainedMap(Player p) {
        return hashMap.containsKey(p.getUniqueId());
    }
}
