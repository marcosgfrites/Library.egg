package com.gyl.library.controllers;

import com.gyl.library.entities.AuthorEntity;
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

    @PostMapping("/save")
    public RedirectView saveAuthor(@RequestParam String name, @RequestParam Boolean activate) {
        authorServiceImpl.createAuthor(name, activate);
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

    @PostMapping("/modify")
    public RedirectView modifyAuthor(@RequestParam Integer id_author, @RequestParam String name, @RequestParam Boolean activate) {
        authorServiceImpl.updateAuthor(id_author, name, activate);
        return new RedirectView("/authors");
    }

    @PostMapping("/delete/{id_author}")
    public RedirectView deleteAuthor(@PathVariable Integer id_author) {
        authorServiceImpl.deleteAuthor(id_author);
        return new RedirectView("/authors");
    }

    @PostMapping("/activate/{id_author}")
    public RedirectView activateAuthor(@PathVariable Integer id_author) {
        AuthorEntity authorEntity = authorServiceImpl.findAuthorById(id_author);
        authorEntity.setActivate(!authorEntity.getActivate());
        authorServiceImpl.updateAuthor(authorEntity.getId_author(), authorEntity.getName(), authorEntity.getActivate());
        return new RedirectView("/authors");
    }

}
