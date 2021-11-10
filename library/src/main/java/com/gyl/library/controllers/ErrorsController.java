package com.gyl.library.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorsController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView errors(HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("error");
        String message = "";
        int codeResponse = response.getStatus();
        switch (codeResponse) {
            case 403:
                message = "¡No cuenta con los permisos necesarios para acceder al recurso solicitado!";
                break;
            case 404:
                message = "¡No se ha encontrado el recurso solicitado!";
                break;
            case 500:
                message = "¡Algo ha ocurrido en el servidor!";
                break;
            default:
                message = "¡Error inesperado! Contacte con su administrador.";
        }
        modelAndView.addObject("code", codeResponse);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

}
