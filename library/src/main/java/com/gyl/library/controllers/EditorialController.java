package com.gyl.library.controllers;

import com.gyl.library.entities.EditorialEntity;
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

    @PostMapping("/save")
    public RedirectView saveEditorial(@RequestParam String name, @RequestParam Boolean activate) {
        editorialServiceImpl.createEditorial(name, activate);
        return new RedirectView("/editorials");
    }

    @PostMapping("/modify")
    public RedirectView modifyEditorial(@RequestParam Integer id_editorial, @RequestParam String name, @RequestParam Boolean activate) {
        editorialServiceImpl.updateEditorial(id_editorial, name, activate);
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

    @PostMapping("/activate/{id_editorial}")
    public RedirectView activateEditorial(@PathVariable Integer id_editorial) {
        EditorialEntity editorialEntity = editorialServiceImpl.findEditorialById(id_editorial);
        editorialEntity.setActivate(!editorialEntity.getActivate());
        editorialServiceImpl.updateEditorial(editorialEntity.getId_editorial(), editorialEntity.getName(), editorialEntity.getActivate());
        return new RedirectView("/editorials");
    }

}
