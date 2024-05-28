package com.backend2.backend2_pensionat_with_maven.repos;


import com.backend2.backend2_pensionat_with_maven.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepo extends CrudRepository<Role, UUID> {

    Role findByName(String name);
}
