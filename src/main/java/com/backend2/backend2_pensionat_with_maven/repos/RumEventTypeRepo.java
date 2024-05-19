package com.backend2.backend2_pensionat_with_maven.repos;

import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RumEventTypeRepo extends JpaRepository<RumEvent.RumEventType, Long> {

    List<RumEvent.RumEventType> findAllByRoomNo(int roomNo);
}
