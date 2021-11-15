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
    public ModelAndView viewAll(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("books");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("books", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("books", null);
        } else {
            try {
                modelAndView.addObject("books", bookServiceImpl.getAllBooksOrderByTitle());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/books");
            }
        }
        return modelAndView;
    }

    @GetMapping("/allbyauthor/{id_author}")
    public ModelAndView viewAllByAuthor(HttpServletRequest request, @RequestParam(required = false) String error, @PathVariable Integer id_author) throws Exception {
        ModelAndView modelAndView = new ModelAndView("books");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("books", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("books", null);
        } else {
            try {
                modelAndView.addObject("books", bookServiceImpl.getAllBooksByAuthor(authorServiceImpl.findAuthorById(id_author)));
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/books");
            }
        }
        return modelAndView;
    }

    @GetMapping("/allbyeditorial/{id_editorial}")
    public ModelAndView viewAllByEditorial(HttpServletRequest request, @RequestParam(required = false) String error, @PathVariable Integer id_editorial) throws Exception {
        ModelAndView modelAndView = new ModelAndView("books");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("books", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("books", null);
        } else {
            try {
                modelAndView.addObject("books", bookServiceImpl.getAllBooksByEditorial(editorialServiceImpl.findEditorialById(id_editorial)));
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/books");
            }
        }
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("books");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("books", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("books", null);
        } else {
            try {
                modelAndView.addObject("books", bookServiceImpl.getAllBooksNoActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/books");
            }
        }
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("books");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("books", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("books", null);
        } else {
            try {
                modelAndView.addObject("books", bookServiceImpl.getAllBooksActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/books");
            }
        }
        return modelAndView;
    }


    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createBook(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("bookform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("book", flashMap.get("book"));
        } else {
            modelAndView.addObject("book", new BookEntity());
        }
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsActivated());
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsActivated());
        modelAndView.addObject("title", "Creación de Libro");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveBook(@ModelAttribute BookEntity book, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/books");
        try {
            Integer remainingCopies = book.getCopies(); // igualo la cantidad de ejemplares restantes del libro con la de la ejemplares ingresados del mismo
            Integer loanedCopies = 0; // al ser un libro nuevo, establezco que la cantidad de ejemplares prestados es igual a 0
            bookServiceImpl.validateFormAndCreate(book.getIsbn(), book.getTitle(), book.getYear(), book.getCopies(), loanedCopies, remainingCopies, book.getAuthor(), book.getEditorial());
            redirectAttributes.addFlashAttribute("success", "El libro ha sido creado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("book", book);
            redirectView.setUrl("/books/create");
        }
        return redirectView;
    }

    @GetMapping("/edit/{id_book}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editBook(@PathVariable Integer id_book, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("bookform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("book", flashMap.get("book"));
        } else {
            modelAndView.addObject("book", bookServiceImpl.findBookById(id_book));
        }
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsActivated());
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsActivated());
        modelAndView.addObject("title", "Edicion de Libro");
        modelAndView.addObject("action", "modify");
        return modelAndView;
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modifyBook(@ModelAttribute BookEntity book, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/books");
        try {
            bookServiceImpl.validateFormAndUpdate(book.getId_book(), book.getIsbn(), book.getTitle(), book.getYear(), book.getYear(), book.getLoanedCopies(), book.getRemainingCopies(), book.getAuthor(), book.getEditorial(), book.getActivate());
            redirectAttributes.addFlashAttribute("success", "El libro ha sido modificado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("book", book);
            redirectView.setUrl("/books/edit/" + book.getId_book());
        }
        return redirectView;
    }

    @PostMapping("/delete/{id_book}")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView deleteBook(@PathVariable Integer id_book, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/books");
        try {
            bookServiceImpl.deleteBook(id_book);
            redirectAttributes.addFlashAttribute("success", "El libro ha sido eliminado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return redirectView;
    }

    @PostMapping("/activate/{id_book}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView activateBook(@PathVariable Integer id_book, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/books");
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
        return redirectView;
    }

}
