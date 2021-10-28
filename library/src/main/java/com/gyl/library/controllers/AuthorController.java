package com.gyl.library.controllers;

import com.gyl.library.model.entities.AuthorEntity;
import com.gyl.library.services.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/authors")

public class AuthorController {

    @Autowired
    private AuthorServiceImpl authorServiceImpl;

    @GetMapping("/all")
    public ModelAndView viewAll() {
        ModelAndView modelAndView = new ModelAndView("authors");
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsOrderByName());
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated() {
        ModelAndView modelAndView = new ModelAndView("authors");
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsNoActivated());
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated() {
        ModelAndView modelAndView = new ModelAndView("authors");
        modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsActivated());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createAuthor() {
        ModelAndView modelAndView = new ModelAndView("authorform");
        modelAndView.addObject("author", new AuthorEntity());
        modelAndView.addObject("title", "Creación de Autor");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    /******* INICIO DE PRUEBA DE VALIDACIONES *******/

    @PostMapping("/save")
    public RedirectView saveAuthor(@RequestParam String name, @RequestParam Boolean activate) {
        String title_error = "Error de manipulación de autor";
        String errors = "";
        if (!authorServiceImpl.authorExist(name)) {
            if (authorServiceImpl.nameLengthOK(name)) {
                authorServiceImpl.createAuthor(name, activate);
                return new RedirectView("/authors");
            } else {
                errors += "El nombre del autor no cumple con los requisitos mínimos.\n";
            }
        } else {
            errors += "Ya existe otro autor con el mismo nombre registrado.\n";
        }
        RedirectView redirectView = new RedirectView("/authors/error");
        redirectView.addStaticAttribute("title", title_error);
        redirectView.addStaticAttribute("errors", errors);
        return redirectView;
    }

    @PostMapping("/modify")
    public RedirectView modifyAuthor(@RequestParam Integer id_author, @RequestParam String name, @RequestParam Boolean activate) {
        String title_error = "Error de manipulación de autor";
        String errors = "";
        if (authorServiceImpl.nameLengthOK(name)) {
            authorServiceImpl.updateAuthor(id_author, name, activate);
            return new RedirectView("/authors");
        } else {
            errors += "El nombre del autor no cumple con los requisitos mínimos.\n";
        }
        RedirectView redirectView = new RedirectView("/authors/error");
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

    /******* FIN DE PRUEBA DE VALIDACIONES *******/

    @PostMapping("/activate/{id_author}")
    public RedirectView activateAuthor(@PathVariable Integer id_author) {
        AuthorEntity authorEntity = authorServiceImpl.findAuthorById(id_author);
        authorEntity.setActivate(!authorEntity.getActivate());
        authorServiceImpl.updateAuthor(authorEntity.getId_author(), authorEntity.getName(), authorEntity.getActivate());
        return new RedirectView("/authors");
    }

    @GetMapping("/edit/{id_author}")
    public ModelAndView editAuthor(@PathVariable Integer id_author) {
        ModelAndView modelAndView = new ModelAndView("authorform");
        modelAndView.addObject("author", authorServiceImpl.findAuthorById(id_author));
        modelAndView.addObject("title", "Edicion de Autor");
        modelAndView.addObject("action", "modify");
        return modelAndView;
    }

    @PostMapping("/delete/{id_author}")
    public RedirectView deleteAuthor(@PathVariable Integer id_author) {
        authorServiceImpl.deleteAuthor(id_author);
        return new RedirectView("/authors");
    }

}
