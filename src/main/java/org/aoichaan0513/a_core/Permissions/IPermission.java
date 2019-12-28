package org.aoichaan0513.a_core.Permissions;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public class IPermission {

    private final String name;
    private String prefix;
    private String suffix;

    private HashMap<String, Boolean> hashMap = new HashMap<>();
    private List<UUID> list = new ArrayList<>();

    public IPermission(String name, String prefix, String suffix, HashMap<String, Boolean> hashMap) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;

        this.hashMap = hashMap;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<UUID> getList() {
        return list;
    }

    public HashMap<String, Boolean> getMap() {
        return hashMap;
    }

    public void addPermission(String permissionKey, boolean permissionValue) {
        hashMap.put(permissionKey, permissionValue);
    }

    public void removePermission(String permission) {
        if (hashMap.containsKey(permission))
            hashMap.remove(permission);
    }

    public void setPermissions(Player p) {
        PermissionAttachment permissionAttachment = PermissionManager.getAttachment(p);

        for (Map.Entry<String, Boolean> entry : hashMap.entrySet())
            permissionAttachment.setPermission(entry.getKey(), entry.getValue());

        list.add(p.getUniqueId());
    }

    public void unsetPermissions(Player p) {
        PermissionAttachment permissionAttachment = PermissionManager.getAttachment(p);

        for (Map.Entry<String, Boolean> entry : hashMap.entrySet())
            permissionAttachment.unsetPermission(entry.getKey());

        list.remove(p.getUniqueId());
    }
}
