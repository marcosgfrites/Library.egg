package com.gyl.library.services;

import com.gyl.library.model.entities.AuthorEntity;
import com.gyl.library.model.entities.BookEntity;
import com.gyl.library.model.entities.EditorialEntity;

import java.util.List;

public interface BookService {

    public List<BookEntity> getAllBooksOrderByTitle();
    public void createBook(Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity authorEntity, EditorialEntity editorialEntity, Boolean activate);
    public void updateBook(Integer id_book, Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity authorEntity, EditorialEntity editorialEntity, Boolean activate);
    public void deleteBook(Integer id_book);
    public BookEntity findBookById(Integer id_book);
    public List<BookEntity> getAllBooksActivated();
    public List<BookEntity> getAllBooksNoActivated();
    public BookEntity findBookByIsbn(Long isbn);
    public List<BookEntity> getAllBooksByAuthor(AuthorEntity authorEntity);
    public List<BookEntity> getAllBooksByEditorial(EditorialEntity editorialEntity);

}
