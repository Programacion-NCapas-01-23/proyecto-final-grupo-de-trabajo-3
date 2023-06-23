package com.swifticket.web.utils;

import com.swifticket.web.models.entities.Role;

import java.util.Arrays;
import java.util.List;

public class RoleVerifier {
    public static Boolean userMatchesRoles(int[] validRoles, List<Role> userRoles) {
        List<Integer> userRolesIds = userRoles.stream().map(Role::getId).toList();
        // System.out.println(userRolesIds);
        // System.out.println(Arrays.toString(validRoles));

        boolean success = false;

        for (int validRole : validRoles) {
            if (userRolesIds.contains(validRole)) {
                success = true;
                break;
            }
        }

        return success;
    }
}
