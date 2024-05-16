package com.backend2.backend2_pensionat_with_maven.repos;


import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.models.Shipper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


public interface ShipperRepo extends JpaRepository<Shipper, Integer> {

    @Modifying
    @Transactional
    @Query("update Shipper s set s.companyName=?1, s.phone=?2 where s.id = ?3")
    public void updateShipperById(String companyName, String phone, Integer id);
}
