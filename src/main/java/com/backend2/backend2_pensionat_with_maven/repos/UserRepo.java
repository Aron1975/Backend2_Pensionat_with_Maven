package com.backend2.backend2_pensionat_with_maven.repos;


import com.backend2.backend2_pensionat_with_maven.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);
}
