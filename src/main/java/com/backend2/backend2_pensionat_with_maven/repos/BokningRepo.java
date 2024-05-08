package com.backend2.backend2_pensionat_with_maven.repos;


import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.models.Kund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BokningRepo extends JpaRepository<Bokning, Long> {

    @Query("select kund from Bokning")
    public List<Kund> getKundIdList();

    //@Query("SELECT b FROM Bokning b WHERE b.kund IS NULL")
    //List<Bokning> getNullBokning();


}
