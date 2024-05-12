package com.backend2.backend2_pensionat_with_maven.repos;

import com.backend2.backend2_pensionat_with_maven.models.ContractCustomer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractCustomerRepo extends JpaRepository<ContractCustomer,Integer> {

    List<ContractCustomer> findAllByCompanyNameContains(String companyName, Sort sort);

    List<ContractCustomer> findAll(Sort sort);
}
