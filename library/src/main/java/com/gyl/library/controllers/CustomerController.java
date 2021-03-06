package com.gyl.library.controllers;

import com.gyl.library.entities.CustomerEntity;
import com.gyl.library.services.CustomerServiceImpl;
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
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping("/all")
    public ModelAndView viewAll(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("customers");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("customers", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("customers", null);
        } else {
            try {
                modelAndView.addObject("customers", customerServiceImpl.getAllCustomersOrderByName());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/customers");
            }
        }
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("customers");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("customers", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("customers", null);
        } else {
            try {
                modelAndView.addObject("customers", customerServiceImpl.getAllCustomersNoActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/customers");
            }
        }
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("customers");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("customers", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("customers", null);
        } else {
            try {
                modelAndView.addObject("customers", customerServiceImpl.getAllCustomersActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/customers");
            }
        }
        return modelAndView;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createCustomer(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("customerform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("customer", flashMap.get("customer"));
        } else {
            modelAndView.addObject("customer", new CustomerEntity());
        }
        modelAndView.addObject("title", "Creaci??n de Cliente");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveCustomer(@ModelAttribute CustomerEntity customer, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/customers");
        try {
            customerServiceImpl.validateFormAndCreate(customer.getDni(), customer.getFirstName(), customer.getLastName(), customer.getPhone());
            redirectAttributes.addFlashAttribute("success", "El cliente ha sido creado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectView.setUrl("/customers/create");
        }
        return redirectView;
    }

    @GetMapping("/edit/{id_customer}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editCustomer(@PathVariable Integer id_customer, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("customerform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("customer", flashMap.get("customer"));
        } else {
            modelAndView.addObject("customer", customerServiceImpl.findCustomerById(id_customer));
        }
        modelAndView.addObject("title", "Edicion de Cliente");
        modelAndView.addObject("action", "modify");
        return modelAndView;
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modifyCustomer(@ModelAttribute CustomerEntity customer, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/customers");
        try {
            customerServiceImpl.validateFormAndUpdate(customer.getId_customer(), customer.getDni(), customer.getFirstName(), customer.getLastName(), customer.getPhone(), customer.getActivate());
            redirectAttributes.addFlashAttribute("success", "El cliente ha sido modificado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectView.setUrl("/customers/edit/" + customer.getId_customer());
        }
        return redirectView;
    }

    @PostMapping("/delete/{id_customer}")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView deleteCustomer(@PathVariable Integer id_customer, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/customers");
        try {
            customerServiceImpl.deleteCustomer(id_customer);
            redirectAttributes.addFlashAttribute("success", "El cliente ha sido eliminado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return redirectView;
    }

    @PostMapping("/activate/{id_customer}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView activateCustomer(@PathVariable Integer id_customer, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/customers");
        try {
            String aux = "";
            CustomerEntity customerEntity = customerServiceImpl.findCustomerById(id_customer);
            customerEntity.setActivate(!customerEntity.getActivate());
            if (customerEntity.getActivate()) {
                aux = "habilitado";
            } else {
                aux = "deshabilitado";
            }
            customerServiceImpl.updateCustomer(customerEntity.getId_customer(), customerEntity.getDni(), customerEntity.getFirstName(), customerEntity.getLastName(), customerEntity.getPhone(), customerEntity.getActivate());
            redirectAttributes.addFlashAttribute("success", "El cliente ha sido " + aux + " exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return redirectView;
    }

}
