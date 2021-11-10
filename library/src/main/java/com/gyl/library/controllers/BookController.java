package com.gyl.library.controllers;

import com.gyl.library.entities.BookEntity;
import com.gyl.library.services.AuthorServiceImpl;
import com.gyl.library.services.BookServiceImpl;
import com.gyl.library.services.EditorialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

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
    public ModelAndView viewAll(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksOrderByTitle());
        modelAndView.addObject("onlineuser", principal.getName());
        return modelAndView;
    }

    @GetMapping("/allbyauthor/{id_author}")
    public ModelAndView viewAllByAuthor(@PathVariable Integer id_author, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksByAuthor(authorServiceImpl.findAuthorById(id_author)));
        modelAndView.addObject("onlineuser", principal.getName());
        return modelAndView;
    }

    @GetMapping("/allbyeditorial/{id_editorial}")
    public ModelAndView viewAllByEditorial(@PathVariable Integer id_editorial, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksByEditorial(editorialServiceImpl.findEditorialById(id_editorial)));
        modelAndView.addObject("onlineuser", principal.getName());
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books", bookServiceImpl.getAllBooksNoActivated());
        modelAndView.addObject("onlineuser", principal.getName());
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated(HttpServletRequest httpServletRequest, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("books");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
        }
        modelAndView.addObject("books", bookServiceImpl.getAllBooksActivated());
        modelAndView.addObject("onlineuser", principal.getName());
        return modelAndView;
    }


    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createBook(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("bookform");
        modelAndView.addObject("book", new BookEntity());
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsActivated());
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsActivated());
        modelAndView.addObject("title", "Creación de Libro");
        modelAndView.addObject("action", "save");
        modelAndView.addObject("onlineuser", principal.getName());
        return modelAndView;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveBook(@RequestParam Long isbn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer copies, @RequestParam("author") Integer id_author, @RequestParam("editorial") Integer id_editorial, @RequestParam Boolean activate, RedirectAttributes redirectAttributes) {
        try {
            if (!bookServiceImpl.bookExist(isbn)) {
                Integer remainingCopies = copies; // igualo la cantidad de ejemplares restantes del libro con la de la ejemplares ingresados del mismo
                Integer loanedCopies = 0; // al ser un libro nuevo, establezco que la cantidad de ejemplares prestados es igual a 0
                bookServiceImpl.createBook(isbn, title, year, copies, loanedCopies, remainingCopies, authorServiceImpl.findAuthorById(id_author), editorialServiceImpl.findEditorialById(id_editorial), activate);
                redirectAttributes.addFlashAttribute("success", "El libro ha sido creado exitosamente!");
            } else {
                redirectAttributes.addFlashAttribute("warning", "Ya existe un libro registrado con el mismo ISBN.");
            }
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return new RedirectView("/books");
    }

    @GetMapping("/edit/{id_book}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editBook(@PathVariable Integer id_book, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("bookform");
        modelAndView.addObject("book", bookServiceImpl.findBookById(id_book));
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsActivated());
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsActivated());
        modelAndView.addObject("title", "Edicion de Libro");
        modelAndView.addObject("action", "modify");
        modelAndView.addObject("onlineuser", principal.getName());
        return modelAndView;
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modifyBook(@RequestParam Integer id_book, @RequestParam Long isbn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer copies, @RequestParam Integer loanedCopies, @RequestParam Integer remainingCopies, @RequestParam("author") Integer id_author, @RequestParam("editorial") Integer id_editorial, @RequestParam Boolean activate, RedirectAttributes redirectAttributes) {
        try {
            bookServiceImpl.updateBook(id_book, isbn, title, year, copies, loanedCopies, remainingCopies, authorServiceImpl.findAuthorById(id_author), editorialServiceImpl.findEditorialById(id_editorial), activate);
            redirectAttributes.addFlashAttribute("success", "El libro ha sido modificado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return new RedirectView("/books");
    }

    @PostMapping("/delete/{id_book}")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView deleteBook(@PathVariable Integer id_book, RedirectAttributes redirectAttributes) {
        try {
            bookServiceImpl.deleteBook(id_book);
            redirectAttributes.addFlashAttribute("success", "El libro ha sido eliminado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return new RedirectView("/books");
    }

    @PostMapping("/activate/{id_book}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView activateBook(@PathVariable Integer id_book, RedirectAttributes redirectAttributes) {
        try {
            String aux = "";
            BookEntity bookEntity = bookServiceImpl.findBookById(id_book);
            bookEntity.setActivate(!bookEntity.getActivate());
            if (bookEntity.getActivate()) {
                aux = "habilitado";
            } else {
                aux = "deshabilitado";
            }
            bookServiceImpl.updateBook(bookEntity.getId_book(), bookEntity.getIsbn(), bookEntity.getTitle(), bookEntity.getYear(), bookEntity.getCopies(), bookEntity.getLoanedCopies(), bookEntity.getRemainingCopies(), bookEntity.getAuthor(), bookEntity.getEditorial(), bookEntity.getActivate());
            redirectAttributes.addFlashAttribute("success", "El libro ha sido " + aux + " exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return new RedirectView("/books");
    }

}
