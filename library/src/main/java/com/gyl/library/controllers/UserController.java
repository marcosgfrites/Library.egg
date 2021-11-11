package com.gyl.library.controllers;

import com.gyl.library.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/signup")
    public ModelAndView signup(HttpServletRequest request, Principal principal) {
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
        modelAndView.addObject("action", "register");
        return modelAndView;
    }

    @PostMapping("/register")
    public RedirectView register(@RequestParam String username, @RequestParam String password, @RequestParam String repassword, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/login");
        try {
            userServiceImpl.validateFormAndCreate(username, password, repassword);
            redirectAttributes.addFlashAttribute("success", "El usuario ha sido creado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("password", password);
            redirectAttributes.addFlashAttribute("repassword", repassword);
            redirectView.setUrl("/users/signup");
        }
        return redirectView;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
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
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveUser(@RequestParam String username, @RequestParam String password, @RequestParam String repassword, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/");
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
    }

}
