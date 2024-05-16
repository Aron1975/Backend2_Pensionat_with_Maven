package com.backend2.backend2_pensionat_with_maven.repos;

import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RumEventRepo extends JpaRepository<RumEvent, Integer> {
}
