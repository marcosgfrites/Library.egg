package com.gyl.library.services;

import com.gyl.library.entities.BookEntity;
import com.gyl.library.entities.CustomerEntity;
import com.gyl.library.entities.LoanEntity;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {

    public List<LoanEntity> getAllLoansOrderByLoanDateAndReturnDate() throws Exception;
    public void createLoan(LocalDate loanDate, LocalDate returnDate, BookEntity book, CustomerEntity customer) throws Exception;
    public void updateLoan(Integer id_loan, LocalDate loanDate, LocalDate returnDate, BookEntity book, CustomerEntity customer, Boolean activate) throws Exception;
    public void deleteLoan(Integer id_loan) throws Exception;
    public LoanEntity findLoanById(Integer id_loan) throws Exception;
    public List<LoanEntity> getAllLoansActivated() throws Exception;
    public List<LoanEntity> getAllLoansNoActivated() throws Exception;
    public List<LoanEntity> getAllLoansByBook(BookEntity bookEntity) throws Exception;
    public List<LoanEntity> getAllLoansByCustomer(CustomerEntity customerEntity) throws Exception;

}
