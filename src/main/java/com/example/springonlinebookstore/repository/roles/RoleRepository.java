package com.example.springonlinebookstore.repository.roles;

import com.example.springonlinebookstore.model.Role;
import com.example.springonlinebookstore.model.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByName(RoleName roleName);
}
