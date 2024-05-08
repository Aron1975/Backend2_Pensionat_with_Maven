package com.backend2.backend2_pensionat_with_maven.repos;


import com.backend2.backend2_pensionat_with_maven.models.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipperRepo extends JpaRepository<Shipper, Integer> {
}
