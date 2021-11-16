package com.gyl.library.repositories;

import com.gyl.library.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    List<CustomerEntity> findByOrderByLastNameAscFirstNameAsc();

    List<CustomerEntity> findByActivateTrueOrderByLastNameAscFirstNameAsc();

    List<CustomerEntity> findByActivateFalseOrderByLastNameAscFirstNameAsc();

    CustomerEntity findByLastNameAndFirstNameIgnoreCase(String firstName, String lastName);

    CustomerEntity findByDni(Long dni);

}
