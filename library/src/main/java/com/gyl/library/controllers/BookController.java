package com.gyl.library.controllers;

import com.gyl.library.model.entities.BookEntity;
import com.gyl.library.services.AuthorServiceImpl;
import com.gyl.library.services.BookServiceImpl;
import com.gyl.library.services.EditorialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private AuthorServiceImpl authorServiceImpl;

    @Autowired
    private EditorialServiceImpl editorialServiceImpl;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @GetMapping("/all")
    public ModelAndView viewAll() {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksOrderByTitle());
        return modelAndView;
    }

    @GetMapping("/allbyauthor/{id_author}")
    public ModelAndView viewAllByAuthor(@PathVariable Integer id_author) {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksByAuthor(authorServiceImpl.findAuthorById(id_author)));
        return modelAndView;
    }

    @GetMapping("/allbyeditorial/{id_editorial}")
    public ModelAndView viewAllByEditorial(@PathVariable Integer id_editorial) {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksByEditorial(editorialServiceImpl.findEditorialById(id_editorial)));
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated() {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksNoActivated());
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated() {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksActivated());
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView createBook() {
        ModelAndView modelAndView = new ModelAndView("bookform");
        modelAndView.addObject("book", new BookEntity());
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsOrderByName());
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsOrderByName());
        modelAndView.addObject("title", "Creación de Libro");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    /******* INICIO DE PRUEBA DE VALIDACIONES *******/

    @PostMapping("/save")
    public RedirectView saveBook(@RequestParam Long isbn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer copies, @RequestParam("author") Integer id_author, @RequestParam("editorial") Integer id_editorial, @RequestParam Boolean activate) {
        String title_error = "Error de manipulación de libro";
        String errors = "";
        if (!bookServiceImpl.bookExist(isbn)) {
            if (bookServiceImpl.titleLengthOK(title)) {
                if (bookServiceImpl.isbnLengthOK(isbn)) {
                    if (bookServiceImpl.yearOK(year)) {
                        Integer remainingCopies = copies; // igualo la cantidad de ejemplares restantes del libro con la de la ejemplares ingresados del mismo
                        Integer loanedCopies = 0; // al ser un libro nuevo, establezco que la cantidad de ejemplares prestados es igual a 0
                        bookServiceImpl.createBook(isbn, title, year, copies, loanedCopies, remainingCopies, authorServiceImpl.findAuthorById(id_author), editorialServiceImpl.findEditorialById(id_editorial), activate);
                        return new RedirectView("/books");
                    } else {
                        errors += "El año ingresado no es un valor válido.\n";
                    }
                } else {
                    errors += "El código ISBN del libro no es un código válido.\n";
                }
            } else {
                errors += "El título del libro no cumple con los requisitos mínimos.\n";
            }
        } else {
            errors += "Ya existe otro libro con el mismo ISBN registrado.\n";
        }
        RedirectView redirectView = new RedirectView("/books/error");
        redirectView.addStaticAttribute("title", title_error);
        redirectView.addStaticAttribute("errors", errors);
        return redirectView;
    }

    @GetMapping("/error")
    public ModelAndView hasError(@RequestParam String title, @RequestParam String errors) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("title", title);
        modelAndView.addObject("errors", errors);
        return modelAndView;
    }

    @PostMapping("/modify")
    public RedirectView modifyBook(@RequestParam Integer id_book, @RequestParam Long isbn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer copies, @RequestParam Integer loanedCopies, @RequestParam Integer remainingCopies, @RequestParam("author") Integer id_author, @RequestParam("editorial") Integer id_editorial, @RequestParam Boolean activate) {
        String title_error = "Error de manipulación de libro";
        String errors = "";
        if (bookServiceImpl.titleLengthOK(title)) {
            if (bookServiceImpl.isbnLengthOK(isbn)) {
                if (bookServiceImpl.yearOK(year)) {
                    bookServiceImpl.updateBook(id_book, isbn, title, year, copies, loanedCopies, remainingCopies, authorServiceImpl.findAuthorById(id_author), editorialServiceImpl.findEditorialById(id_editorial), activate);
                    return new RedirectView("/books");
                } else {
                    errors += "El año ingresado no es un valor válido.\n";
                }
            } else {
                errors += "El código ISBN del libro no es un código válido.\n";
            }
        } else {
            errors += "El título del libro no cumple con los requisitos mínimos.\n";
        }
        RedirectView redirectView = new RedirectView("/books/error");
        redirectView.addStaticAttribute("title", title_error);
        redirectView.addStaticAttribute("errors", errors);
        return redirectView;
    }

    /******* FIN DE PRUEBA DE VALIDACIONES *******/

    @PostMapping("/activate/{id_book}")
    public RedirectView activateBook(@PathVariable Integer id_book) {
        BookEntity bookEntity = bookServiceImpl.findBookById(id_book);
        bookEntity.setActivate(!bookEntity.getActivate());
        bookServiceImpl.updateBook(bookEntity.getId_book(), bookEntity.getIsbn(), bookEntity.getTitle(), bookEntity.getYear(), bookEntity.getCopies(), bookEntity.getLoanedCopies(), bookEntity.getRemainingCopies(), bookEntity.getAuthor(), bookEntity.getEditorial(), bookEntity.getActivate());
        return new RedirectView("/books");
    }

    @GetMapping("/edit/{id_book}")
    public ModelAndView editBook(@PathVariable Integer id_book) {
        ModelAndView modelAndView = new ModelAndView("bookform");
        modelAndView.addObject("book", bookServiceImpl.findBookById(id_book));
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsOrderByName());
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsOrderByName());
        modelAndView.addObject("title", "Edicion de Libro");
        modelAndView.addObject("action", "modify");
        return modelAndView;
    }

    @PostMapping("/delete/{id_book}")
    public RedirectView deleteBook(@PathVariable Integer id_book) {
        bookServiceImpl.deleteBook(id_book);
        return new RedirectView("/books");
    }

}
