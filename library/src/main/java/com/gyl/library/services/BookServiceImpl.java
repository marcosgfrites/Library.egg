package com.gyl.library.services;

import com.gyl.library.entities.AuthorEntity;
import com.gyl.library.entities.BookEntity;
import com.gyl.library.entities.EditorialEntity;
import com.gyl.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksOrderByTitle() { return bookRepository.findByOrderByTitleAsc(); }

    @Override
    @Transactional
    public void createBook(Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity authorEntity, EditorialEntity editorialEntity, Boolean activate) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn(isbn);
        bookEntity.setTitle(title.toUpperCase());
        bookEntity.setYear(year);
        bookEntity.setCopies(copies);
        bookEntity.setLoanedCopies(loanedCopies);
        bookEntity.setRemainingCopies(remainingCopies);
        bookEntity.setAuthor(authorEntity);
        bookEntity.setEditorial(editorialEntity);
        bookEntity.setActivate(activate);
        bookRepository.save(bookEntity);
    }

    @Override
    @Transactional
    public void updateBook(Integer id_book, Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity authorEntity, EditorialEntity editorialEntity, Boolean activate) {
        BookEntity bookEntity = bookRepository.findById(id_book).get();
        bookEntity.setId_book(id_book);
        bookEntity.setIsbn(isbn);
        bookEntity.setTitle(title.toUpperCase());
        bookEntity.setYear(year);
        bookEntity.setCopies(copies);
        bookEntity.setLoanedCopies(loanedCopies);
        bookEntity.setRemainingCopies(remainingCopies);
        bookEntity.setAuthor(authorEntity);
        bookEntity.setEditorial(editorialEntity);
        bookEntity.setActivate(activate);
        bookRepository.save(bookEntity);
    }

    @Override
    @Transactional
    public void deleteBook(Integer id_book) {
        BookEntity bookEntity = bookRepository.findById(id_book).get();
        bookRepository.delete(bookEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public BookEntity findBookById(Integer id_book) { return bookRepository.findById(id_book).get(); }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksActivated() { return bookRepository.findByActivateTrueOrderByTitleAsc(); }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksNoActivated() { return bookRepository.findByActivateFalseOrderByTitleAsc(); }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksByAuthor(AuthorEntity authorEntity) {  return bookRepository.findByAuthorOrderByTitleAsc(authorEntity); }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksByEditorial(EditorialEntity editorialEntity) { return bookRepository.findByEditorialOrderByTitleAsc(editorialEntity); }

    @Override
    @Transactional(readOnly = true)
    public BookEntity findBookByIsbn(Long isbn) {
        if (bookRepository.findByIsbn(isbn) != null) {
            return bookRepository.findByIsbn(isbn);
        }
        return null;
    }

    public boolean bookExist(Long isbn) {
        if (this.findBookByIsbn(isbn) != null) {
            return true;
        }
        return false;
    }

}
