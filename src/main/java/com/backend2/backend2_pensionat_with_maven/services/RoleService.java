package com.backend2.backend2_pensionat_with_maven.services;

import com.backend2.backend2_pensionat_with_maven.models.Role;

import java.util.Set;



public interface RoleService {
    Set<Role> getAllRoles();
}