package com.gyl.library.controllers;

import com.gyl.library.model.entities.EditorialEntity;
import com.gyl.library.services.EditorialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/editorials")
public class EditorialController {

    @Autowired
    private EditorialServiceImpl editorialServiceImpl;

    @GetMapping("/all")
    public ModelAndView viewAll() {
        ModelAndView modelAndView = new ModelAndView("editorials");
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsOrderByName());
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated() {
        ModelAndView modelAndView = new ModelAndView("editorials");
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsNoActivated());
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated() {
        ModelAndView modelAndView = new ModelAndView("editorials");
        modelAndView.addObject("editorials", editorialServiceImpl.getAllEditorialsActivated());
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView createEditorial() {
        ModelAndView modelAndView = new ModelAndView("editorialform");
        modelAndView.addObject("editorial", new EditorialEntity());
        modelAndView.addObject("title", "Creación de Editorial");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    /******* INICIO DE PRUEBA DE VALIDACIONES *******/

    @PostMapping("/save")
    public RedirectView saveEditorial(@RequestParam String name, @RequestParam Boolean activate) {
        String title_error = "Error de manipulación de editorial";
        String errors = "";
        if (!editorialServiceImpl.editorialExist(name)) {
            if (editorialServiceImpl.nameLengthOK(name)) {
                editorialServiceImpl.createEditorial(name, activate);
                return new RedirectView("/editorials");
            } else {
                errors += "El nombre de la editorial no cumple con los requisitos mínimos.\n";
            }
        } else {
            errors += "Ya existe otra editorial con el mismo nombre registrado.\n";
        }
        RedirectView redirectView = new RedirectView("/editorials/error");
        redirectView.addStaticAttribute("title", title_error);
        redirectView.addStaticAttribute("errors", errors);
        return redirectView;
    }

    @PostMapping("/modify")
    public RedirectView modifyEditorial(@RequestParam Integer id_editorial, @RequestParam String name, @RequestParam Boolean activate) {
        String title_error = "Error de manipulación de editorial";
        String errors = "";
        if (editorialServiceImpl.nameLengthOK(name)) {
            editorialServiceImpl.updateEditorial(id_editorial, name, activate);
            return new RedirectView("/editorials");
        } else {
            errors += "El nombre de la editorial no cumple con los requisitos mínimos.\n";
        }
        RedirectView redirectView = new RedirectView("/editorials/error");
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

    @PostMapping("/activate/{id_editorial}")
    public RedirectView activateEditorial(@PathVariable Integer id_editorial) {
        EditorialEntity editorialEntity = editorialServiceImpl.findEditorialById(id_editorial);
        editorialEntity.setActivate(!editorialEntity.getActivate());
        editorialServiceImpl.updateEditorial(editorialEntity.getId_editorial(), editorialEntity.getName(), editorialEntity.getActivate());
        return new RedirectView("/editorials");
    }

    @GetMapping("/edit/{id_editorial}")
    public ModelAndView editEditorial(@PathVariable Integer id_editorial) {
        ModelAndView modelAndView = new ModelAndView("editorialform");
        modelAndView.addObject("editorial", editorialServiceImpl.findEditorialById(id_editorial));
        modelAndView.addObject("title", "Edicion de Editorial");
        modelAndView.addObject("action", "modify");
        return modelAndView;
    }

    @PostMapping("/delete/{id_editorial}")
    public RedirectView deleteEditorial(@PathVariable Integer id_editorial) {
        editorialServiceImpl.deleteEditorial(id_editorial);
        return new RedirectView("/editorials");
    }

}
