package com.backend2.backend2_pensionat_with_maven.repos;

import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractCustomerRepo extends JpaRepository<ContractCustomer,Integer> {
}
