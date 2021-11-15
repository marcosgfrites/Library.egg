package com.gyl.library.services;

import com.gyl.library.entities.AuthorEntity;
import com.gyl.library.entities.BookEntity;
import com.gyl.library.entities.EditorialEntity;
import com.gyl.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksOrderByTitle() throws Exception {
        try {
            List<BookEntity> list = bookRepository.findByOrderByTitleAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Libros, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void createBook(Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity author, EditorialEntity editorial) throws Exception {
        try {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setIsbn(isbn);
            bookEntity.setTitle(title.toUpperCase());
            bookEntity.setYear(year);
            bookEntity.setCopies(copies);
            bookEntity.setLoanedCopies(loanedCopies);
            bookEntity.setRemainingCopies(remainingCopies);
            bookEntity.setAuthor(author);
            bookEntity.setEditorial(editorial);
            bookEntity.setActivate(true);
            bookRepository.save(bookEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la creación de Libro en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateBook(Integer id_book, Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity author, EditorialEntity editorial, Boolean activate) throws Exception {
        try {
            BookEntity bookEntity = bookRepository.findById(id_book).get();
            bookEntity.setId_book(id_book);
            bookEntity.setIsbn(isbn);
            bookEntity.setTitle(title.toUpperCase());
            bookEntity.setYear(year);
            bookEntity.setCopies(copies);
            bookEntity.setLoanedCopies(loanedCopies);
            bookEntity.setRemainingCopies(remainingCopies);
            bookEntity.setAuthor(author);
            bookEntity.setEditorial(editorial);
            bookEntity.setActivate(activate);
            bookRepository.save(bookEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la actualización de Libro en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteBook(Integer id_book) throws Exception {
        try {
            BookEntity bookEntity = bookRepository.findById(id_book).get();
            bookRepository.delete(bookEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la eliminación de Libro en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BookEntity findBookById(Integer id_book) throws Exception {
        try {
            BookEntity book = bookRepository.findById(id_book).get();
            if (book == null) {
                throw new Exception("No se ha encontrado un Libro con el número de ID indicado");
            } else {
                return book;
            }
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Libro, por ID, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksActivated() throws Exception {
        try {
            List<BookEntity> list = bookRepository.findByActivateTrueOrderByTitleAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Libros habilitados, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksNoActivated() throws Exception {
        try {
            List<BookEntity> list = bookRepository.findByActivateFalseOrderByTitleAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Libros deshabilitados, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksByAuthor(AuthorEntity authorEntity) throws Exception {
        try {
            List<BookEntity> list = bookRepository.findByAuthorOrderByTitleAsc(authorEntity);
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Libros habilitados, ordenados alfabéticamente y según el Autor indicado, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookEntity> getAllBooksByEditorial(EditorialEntity editorialEntity) throws Exception {
        try {
            List<BookEntity> list = bookRepository.findByEditorialOrderByTitleAsc(editorialEntity);
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Libros habilitados, ordenados alfabéticamente y según la Editorial indicada, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BookEntity findBookByIsbn(Long isbn) throws Exception {
        try {
            BookEntity book = bookRepository.findByIsbn(isbn);
            return book;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Libro, por ISBN, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public boolean bookExist(Long isbn) throws Exception {
        try {
            if (this.findBookByIsbn(isbn) != null) {
                return true;
            }
            return false;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido verificar la existencia de Libro en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public void validateFormAndCreate(Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity author, EditorialEntity editorial) throws Exception {
        try {
            if (this.bookExist(isbn)) {
                throw new Exception("Ya existe un Libro registrado con el mismo ISBN.");
            }
            if (year < LocalDate.now().getYear()) {
                throw new Exception("El año ingresado no puede ser mayor al año actual.");
            }
            if (copies != remainingCopies) {
                throw new Exception("La cantidad de ejemplares iniciales y restantes, son diferentes.");
            }
            if (loanedCopies != 0) {
                throw new Exception("La cantidad de ejemplares prestados inicialmente, no puede ser diferente de 0.");
            }
            this.createBook(isbn, title, year, copies, loanedCopies, remainingCopies, author, editorial);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    public void validateFormAndUpdate(Integer id_book, Long isbn, String title, Integer year, Integer copies, Integer loanedCopies, Integer remainingCopies, AuthorEntity author, EditorialEntity editorial, Boolean activate) throws Exception {
        try {
            if (year < LocalDate.now().getYear()) {
                throw new Exception("El año ingresado no puede ser mayor al año actual.");
            }
            if (copies != (loanedCopies + remainingCopies)) {
                throw new Exception("La suma de las cantidades de ejemplares prestados y restantes debe ser igual a la cantidad de ejemplares iniciales.");
            }
            this.updateBook(id_book, isbn, title, year, copies, loanedCopies, remainingCopies, author, editorial, activate);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

}
