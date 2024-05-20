package com.backend2.backend2_pensionat_with_maven.repos;

import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RumEventTypeRepo extends JpaRepository<RumEvent.RumEventType, Long> {

    //"SELECT e FROM Employee e WHERE e.empNumber = ?1", Employee.class);

   /* @Query(value = "SELECT * FROM rum_event$rum_event_type where roomNo=:room_no", nativeQuery = true)
    List<RumEvent.RumEventType> findAllByRoomNo(@Param("room_no") int roomNo);*/

    //List<RumEvent.RumEventType> findByRoomNo();
}
