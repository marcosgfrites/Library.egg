package com.gyl.library.controllers;

import com.gyl.library.entities.EditorialEntity;
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
@RequestMapping("/editorials")
public class EditorialController {

    @Autowired
    private EditorialServiceImpl editorialServiceImpl;

    @GetMapping("/all")
    public ModelAndView viewAll(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("editorials");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("editorials", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("authors", null);
        } else {
            try {
                modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsOrderByName());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/editorials");
            }
        }
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("editorials");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("editorials", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("editorials", null);
        } else {
            try {
                modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsNoActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/editorials");
            }
        }
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated(HttpServletRequest httpServletRequest, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("editorials");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("editorials", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("editorials", null);
        } else {
            try {
                modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/editorials");
            }
        }
        return modelAndView;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createEditorial(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("editorialform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("editorial", flashMap.get("editorial"));
        } else {
            modelAndView.addObject("editorial", new EditorialEntity());
        }
        modelAndView.addObject("title", "Creación de Editorial");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveEditorial(@ModelAttribute EditorialEntity editorial, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/authors");
        try {
            editorialServiceImpl.validateFormAndCreate(editorial.getName());
            redirectAttributes.addFlashAttribute("success", "La editorial ha sido creada exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("author", editorial);
            redirectView.setUrl("/authors/create");
        }
        return redirectView;
    }

    @GetMapping("/edit/{id_editorial}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editEditorial(@PathVariable Integer id_editorial, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("editorialform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("editorial", flashMap.get("editorial"));
        } else {
            modelAndView.addObject("editorial", editorialServiceImpl.findEditorialById(id_editorial));
        }
        modelAndView.addObject("title", "Edicion de Editorial");
        modelAndView.addObject("action", "modify");
        return modelAndView;
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modifyEditorial(@ModelAttribute EditorialEntity editorial, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/editorials");
        try {
            editorialServiceImpl.validateFormAndUpdate(editorial.getId_editorial(), editorial.getName(), editorial.getActivate());
            redirectAttributes.addFlashAttribute("success", "La editorial ha sido modificada exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("editorial", editorial);
            redirectView.setUrl("/editorials/edit/" + editorial.getId_editorial());
        }
        return redirectView;
    }

    @PostMapping("/delete/{id_editorial}")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView deleteEditorial(@PathVariable Integer id_editorial, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/editorials");
        try {
            editorialServiceImpl.deleteEditorial(id_editorial);
            redirectAttributes.addFlashAttribute("success", "La editorial ha sido eliminada exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return redirectView;
    }

    @PostMapping("/activate/{id_editorial}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView activateEditorial(@PathVariable Integer id_editorial, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/editorials");
        try {
            String aux = "";
            EditorialEntity editorialEntity = editorialServiceImpl.findEditorialById(id_editorial);
            editorialEntity.setActivate(!editorialEntity.getActivate());
            if (editorialEntity.getActivate()) {
                aux = "habilitada";
            } else {
                aux = "deshabilitada";
            }
            editorialServiceImpl.updateEditorial(editorialEntity.getId_editorial(), editorialEntity.getName(), editorialEntity.getActivate());
            redirectAttributes.addFlashAttribute("success", "La editorial ha sido " + aux + " exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return redirectView;
    }

}
