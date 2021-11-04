package com.gyl.library.controllers;

import com.gyl.library.entities.UserEntity;
import com.gyl.library.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/create")
    public ModelAndView createUser() {
        ModelAndView modelAndView = new ModelAndView("userform");
        modelAndView.addObject("user", new UserEntity());
        modelAndView.addObject("title", "Creación de Usuario");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView saveUser(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            if (!userServiceImpl.userExist(username)) {
                userServiceImpl.createUser(username, password);
                redirectAttributes.addFlashAttribute("success", "El usuario ha sido creado exitosamente!");
                return new RedirectView("/login");
            } else {
                redirectAttributes.addFlashAttribute("warning", "Ya existe un usuario registrado con el mismo username.");
                return new RedirectView("/users/create");
            }
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            System.out.println(exception.toString());
            return new RedirectView("/users/create");
        }
    }

}
