package com.gyl.library.controllers;

import com.gyl.library.entities.RolEntity;
import com.gyl.library.services.RolServiceImpl;
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
@RequestMapping("/roles")
@PreAuthorize("hasRole('SUPER')")
public class RolController {

    @Autowired
    private RolServiceImpl rolServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/create")
    @PreAuthorize("hasRole('SUPER')")
    public ModelAndView createRol(HttpServletRequest request, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("rolform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("rol", flashMap.get("rol"));
        } else {
            modelAndView.addObject("rol", new RolEntity());
        }
        modelAndView.addObject("users", userServiceImpl.getAllUsersActivated());
        return modelAndView;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView saveRol(@ModelAttribute RolEntity rol, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/login");
        try {
            rolServiceImpl.validateFormAndCreate(rol.getNamerol(), rol.getUser());
            redirectAttributes.addFlashAttribute("success", "El rol ha sido asignado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("rol", rol);
            redirectView.setUrl("/roles/create");
        }
        return redirectView;
    }

}
