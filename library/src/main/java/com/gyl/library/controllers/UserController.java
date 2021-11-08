package com.gyl.library.controllers;

import com.gyl.library.entities.UserEntity;
import com.gyl.library.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/create")
    public ModelAndView createUser(HttpServletRequest request, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("userform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("username", flashMap.get("username"));
            modelAndView.addObject("password", flashMap.get("password"));
            modelAndView.addObject("repassword", flashMap.get("repassword"));
        }
        if (principal != null) {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView saveUser(@RequestParam String username, @RequestParam String password, @RequestParam String repassword, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/login");

        try {
            userServiceImpl.validateFormAndCreate(username, password, repassword);
            redirectAttributes.addFlashAttribute("success", "El usuario ha sido creado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("password", password);
            redirectAttributes.addFlashAttribute("repassword", repassword);
            redirectView.setUrl("/users/create");
        }
        return redirectView;

/*
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
*/

    }

}
