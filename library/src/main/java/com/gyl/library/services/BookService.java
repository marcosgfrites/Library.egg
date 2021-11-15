package com.gyl.library.services;

import com.gyl.library.entities.AuthorEntity;
import com.gyl.library.entities.BookEntity;
import com.gyl.library.entities.EditorialEntity;

import java.util.List;

public interface BookService {

    public List<BookEntity> getAllBooksOrderByTitle() throws Exception;
    public void createBook(Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity author, EditorialEntity editorial) throws Exception;
    public void updateBook(Integer id_book, Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity author, EditorialEntity editorial, Boolean activate) throws Exception;
    public void deleteBook(Integer id_book) throws Exception;
    public BookEntity findBookById(Integer id_book) throws Exception;
    public List<BookEntity> getAllBooksActivated() throws Exception;
    public List<BookEntity> getAllBooksNoActivated() throws Exception;
    public BookEntity findBookByIsbn(Long isbn) throws Exception;
    public List<BookEntity> getAllBooksByAuthor(AuthorEntity authorEntity) throws Exception;
    public List<BookEntity> getAllBooksByEditorial(EditorialEntity editorialEntity) throws Exception;

}
