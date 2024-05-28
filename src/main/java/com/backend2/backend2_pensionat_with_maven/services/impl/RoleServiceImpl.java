package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.models.Role;
import com.backend2.backend2_pensionat_with_maven.repos.RoleRepo;
import com.backend2.backend2_pensionat_with_maven.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    @Override
    public Set<Role> getAllRoles() {
        Iterable<Role> roles = roleRepo.findAll();
        return StreamSupport.stream(roles.spliterator(), false)
                .collect(Collectors.toSet());
    }
}