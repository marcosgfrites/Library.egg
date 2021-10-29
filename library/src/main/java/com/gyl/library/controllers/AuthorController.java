package com.gyl.library.controllers;

import com.gyl.library.entities.AuthorEntity;
import com.gyl.library.services.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    public ModelAndView viewAllActivated(HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("authors");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
        }
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
    public RedirectView saveAuthor(@RequestParam String name, @RequestParam Boolean activate, RedirectAttributes redirectAttributes) {
        try {
            if (!authorServiceImpl.authorExist(name)) {
                authorServiceImpl.createAuthor(name, activate);
                redirectAttributes.addFlashAttribute("success", "El autor ha sido creado exitosamente!");
            } else {
                redirectAttributes.addFlashAttribute("warning", "Ya existe un autor registrado con el mismo nombre.");
            }
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
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
    public RedirectView modifyAuthor(@RequestParam Integer id_author, @RequestParam String name, @RequestParam Boolean activate, RedirectAttributes redirectAttributes) {
        try {
            authorServiceImpl.updateAuthor(id_author, name, activate);
            redirectAttributes.addFlashAttribute("success", "El autor ha sido modificado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return new RedirectView("/authors");
    }

    @PostMapping("/delete/{id_author}")
    public RedirectView deleteAuthor(@PathVariable Integer id_author, RedirectAttributes redirectAttributes) {
        try {
            authorServiceImpl.deleteAuthor(id_author);
            redirectAttributes.addFlashAttribute("success", "El autor ha sido eliminado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return new RedirectView("/authors");
    }

    @PostMapping("/activate/{id_author}")
    public RedirectView activateAuthor(@PathVariable Integer id_author, RedirectAttributes redirectAttributes) {
        try {
            String aux = "";
            AuthorEntity authorEntity = authorServiceImpl.findAuthorById(id_author);
            authorEntity.setActivate(!authorEntity.getActivate());
            if (authorEntity.getActivate()) {
                aux = "habilitado";
            } else {
                aux = "deshabilitado";
            }
            authorServiceImpl.updateAuthor(authorEntity.getId_author(), authorEntity.getName(), authorEntity.getActivate());
            redirectAttributes.addFlashAttribute("success", "El autor ha sido " + aux + " exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return new RedirectView("/authors");
    }

}
