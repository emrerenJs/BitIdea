package com.emrerenjs.bitidea.DataAccess.Abstract;

import com.emrerenjs.bitidea.Entity.MySQL.Role;

public interface RoleDAL {
    Role getRole(String role);
}
