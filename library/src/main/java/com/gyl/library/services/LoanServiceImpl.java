package com.gyl.library.services;

import com.gyl.library.entities.BookEntity;
import com.gyl.library.entities.CustomerEntity;
import com.gyl.library.entities.LoanEntity;
import com.gyl.library.repositories.LoanRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    BookServiceImpl bookService;


    @Override
    @Transactional(readOnly = true)
    public List<LoanEntity> getAllLoansOrderByLoanDateAndReturnDate() throws Exception {
        try {
            List<LoanEntity> list = loanRepository.findByOrderByLoanDateAscReturnDateAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Préstamos, ordenados cronológicamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void createLoan(LocalDate loanDate, LocalDate returnDate, BookEntity book, CustomerEntity customer) throws Exception {
        try {
            LoanEntity loanEntity = new LoanEntity();
            loanEntity.setLoanDate(loanDate);
            loanEntity.setReturnDate(returnDate);
            loanEntity.setBook(book);
            loanEntity.setCustomer(customer);
            loanEntity.setActivate(true);
            loanRepository.save(loanEntity);
            bookService.updateBook(book.getId_book(), book.getIsbn(), book.getTitle(), book.getYear(), book.getCopies(), book.getLoanedCopies() + 1, book.getRemainingCopies() - 1, book.getAuthor(), book.getEditorial(), book.getActivate());
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la creación de Préstamo en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateLoan(Integer id_loan, LocalDate loanDate, LocalDate returnDate, BookEntity book, CustomerEntity customer, Boolean activate) throws Exception {
        try {
            LoanEntity loanEntity = new LoanEntity();
            loanEntity.setId_loan(id_loan);
            loanEntity.setLoanDate(loanDate);
            loanEntity.setReturnDate(returnDate);
            loanEntity.setBook(book);
            loanEntity.setCustomer(customer);
            loanEntity.setActivate(activate);
            loanRepository.save(loanEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la actualización de Préstamo en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    public void deleteLoan(Integer id_loan) throws Exception {
        try {
            LoanEntity loanEntity = loanRepository.findById(id_loan).get();
            loanRepository.delete(loanEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la eliminación de Préstamo en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LoanEntity findLoanById(Integer id_loan) throws Exception {
        try {
            LoanEntity loan = loanRepository.findById(id_loan).get();
            if (loan == null) {
                throw new Exception("No se ha encontrado un Préstamo con el número de ID indicado");
            } else {
                return loan;
            }
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Préstamo, por ID, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanEntity> getAllLoansActivated() throws Exception {
        try {
            List<LoanEntity> list = loanRepository.findByActivateTrueOrderByLoanDateAscReturnDateAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Préstamos pendientes de devoución, ordenados cronológicamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanEntity> getAllLoansNoActivated() throws Exception {
        try {
            List<LoanEntity> list = loanRepository.findByActivateFalseOrderByLoanDateAscReturnDateAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Préstamos devueltos, ordenados cronológicamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanEntity> getAllLoansByBook(BookEntity bookEntity) throws Exception {
        try {
            List<LoanEntity> list = loanRepository.findByBookOrderByLoanDateAscReturnDateAsc(bookEntity);
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de préstamos, ordenados cronológicamente y según el Libro indicado, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanEntity> getAllLoansByCustomer(CustomerEntity customerEntity) throws Exception {
        try {
            List<LoanEntity> list = loanRepository.findByCustomerOrderByLoanDateAscReturnDateAsc(customerEntity);
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de préstamos, ordenados cronológicamente y según el Cliente indicado, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public boolean loanExist(BookEntity book, CustomerEntity customer) throws Exception {
        try {
            for (LoanEntity loan : this.getAllLoansActivated()) {
                if (loan.getBook().getIsbn().toString().equals(book.getIsbn().toString()) && loan.getCustomer().getDni().toString().equals(customer.getDni().toString())) {
                    return true;
                }
            }
            return false;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido verificar la existencia del Préstamo en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public void validateFormAndCreate(LocalDate loanDate, LocalDate returnDate, BookEntity book, CustomerEntity customer) throws Exception {
        try {
            if (this.loanExist(book, customer)) {
                throw new Exception("Ya existe un Préstamo registrado del mismo libro y el mismo cliente.");
            }
            if (loanDate.isAfter(LocalDate.now())) {
                throw new Exception("La fecha de préstamo no puede ser posterior a la actual.");
            }
            if (loanDate.isBefore(LocalDate.now())) {
                throw new Exception("La fecha de préstamo no puede ser anterior a la actual.");
            }
            if (loanDate.equals(returnDate)) {
                throw new Exception("La fecha de préstamo y devolución no pueden ser la misma");
            }
            if (returnDate.isBefore(loanDate)) {
                throw new Exception("La fecha de devolución no puede ser anterior a la de préstamo.");
            }
            this.createLoan(loanDate, returnDate, book, customer);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    public void validateFormAndUpdate(Integer id_loan, LocalDate loanDate, LocalDate returnDate, BookEntity book, CustomerEntity customer, Boolean activate) throws Exception {
        try {
            if (loanDate.isAfter(LocalDate.now())) {
                throw new Exception("La fecha de préstamo no puede ser posterior a la actual.");
            }
            if (loanDate.isBefore(LocalDate.now())) {
                throw new Exception("La fecha de préstamo no puede ser anterior a la actual.");
            }
            if (loanDate.equals(returnDate)) {
                throw new Exception("La fecha de préstamo y devolución no pueden ser la misma");
            }
            if (returnDate.isBefore(loanDate)) {
                throw new Exception("La fecha de devolución no puede ser anterior a la de préstamo.");
            }
            this.updateLoan(id_loan, loanDate, returnDate, book, customer, activate);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

}
