package com.gyl.library.controllers;

import com.gyl.library.entities.AuthorEntity;
import com.gyl.library.services.AuthorServiceImpl;
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
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorServiceImpl authorServiceImpl;

    @GetMapping("/all")
    public ModelAndView viewAll(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("authors");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("authors", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("authors", null);
        } else {
            try {
                modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsOrderByName());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/authors");
            }
        }
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("authors");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("authors", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("authors", null);
        } else {
            try {
                modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsNoActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/authors");
            }
        }
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated(HttpServletRequest httpServletRequest, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("authors");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("authors", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("authors", null);
        } else {
            try {
                modelAndView.addObject("authors", authorServiceImpl.getAllAuthorsActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/authors");
            }
        }
        return modelAndView;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createAuthor(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("authorform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("author", flashMap.get("author"));
        } else {
            modelAndView.addObject("author", new AuthorEntity());
        }
        modelAndView.addObject("title", "Creación de Autor");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveAuthor(@ModelAttribute AuthorEntity author, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/authors");
        try {
            authorServiceImpl.validateFormAndCreate(author.getName());
            redirectAttributes.addFlashAttribute("success", "El autor ha sido creado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("author", author);
            redirectView.setUrl("/authors/create");
        }
        return redirectView;
    }

    @GetMapping("/edit/{id_author}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editAuthor(@PathVariable Integer id_author, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("authorform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("author", flashMap.get("author"));
        } else {
            modelAndView.addObject("author", authorServiceImpl.findAuthorById(id_author));
        }
        modelAndView.addObject("title", "Edicion de Autor");
        modelAndView.addObject("action", "modify");
        return modelAndView;
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modifyAuthor(@ModelAttribute AuthorEntity author, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/authors");
        try {
            authorServiceImpl.validateFormAndUpdate(author.getId_author(), author.getName(), author.getActivate());
            redirectAttributes.addFlashAttribute("success", "El autor ha sido modificado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("author", author);
            redirectView.setUrl("/authors/edit/" + author.getId_author());
        }
        return redirectView;
    }

    @PostMapping("/delete/{id_author}")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView deleteAuthor(@PathVariable Integer id_author, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/authors");
        try {
            authorServiceImpl.deleteAuthor(id_author);
            redirectAttributes.addFlashAttribute("success", "El autor ha sido eliminado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return redirectView;
    }

    @PostMapping("/activate/{id_author}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView activateAuthor(@PathVariable Integer id_author, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/authors");
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
        return redirectView;
    }

}
