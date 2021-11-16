package com.gyl.library.repositories;

import com.gyl.library.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {



}
