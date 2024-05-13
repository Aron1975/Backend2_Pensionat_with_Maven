package com.backend2.backend2_pensionat_with_maven.repos;


import com.backend2.backend2_pensionat_with_maven.models.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlacklistRepo extends JpaRepository<Blacklist, Integer> {
}
