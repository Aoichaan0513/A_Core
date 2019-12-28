package org.aoichaan0513.a_core.Permissions.Permission;

import org.aoichaan0513.a_core.Permissions.IPermission;

import java.util.HashMap;

public class Owner extends IPermission {

    public Owner(String name, String prefix, String suffix, HashMap<String, Boolean> hashMap) {
        super(name, prefix, suffix, hashMap);
    }
}
