package com.gyl.library.repositories;

import com.gyl.library.entities.BookEntity;
import com.gyl.library.entities.CustomerEntity;
import com.gyl.library.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

    List<LoanEntity> findByOrderByLoanDateAscReturnDateAsc();

    List<LoanEntity> findByActivateTrueOrderByLoanDateAscReturnDateAsc();

    List<LoanEntity> findByActivateFalseOrderByLoanDateAscReturnDateAsc();

    List<LoanEntity> findByCustomerOrderByLoanDateAscReturnDateAsc(CustomerEntity customerEntity);

    List<LoanEntity> findByBookOrderByLoanDateAscReturnDateAsc(BookEntity bookEntity);

}
