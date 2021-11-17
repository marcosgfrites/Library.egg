package com.gyl.library.controllers;

import com.gyl.library.entities.BookEntity;
import com.gyl.library.entities.LoanEntity;
import com.gyl.library.services.BookServiceImpl;
import com.gyl.library.services.CustomerServiceImpl;
import com.gyl.library.services.LoanServiceImpl;
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
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private LoanServiceImpl loanServiceImpl;

    @GetMapping("/all")
    public ModelAndView viewAll(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("loans");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("loans", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("loans", null);
        } else {
            try {
                modelAndView.addObject("loans", loanServiceImpl.getAllLoansOrderByLoanDateAndReturnDate());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/loans");
            }
        }
        return modelAndView;
    }

    @GetMapping("/allbybook/{id_book}")
    public ModelAndView viewAllByBook(HttpServletRequest request, @RequestParam(required = false) String error, @PathVariable Integer id_book) throws Exception {
        ModelAndView modelAndView = new ModelAndView("loans");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("loans", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("loans", null);
        } else {
            try {
                modelAndView.addObject("loans", loanServiceImpl.getAllLoansByBook(bookServiceImpl.findBookById(id_book)));
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/loans");
            }
        }
        return modelAndView;
    }

    @GetMapping("/allbycustomer/{id_customer}")
    public ModelAndView viewAllByCustomer(HttpServletRequest request, @RequestParam(required = false) String error, @PathVariable Integer id_customer) throws Exception {
        ModelAndView modelAndView = new ModelAndView("loans");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("loans", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("loans", null);
        } else {
            try {
                modelAndView.addObject("loans", loanServiceImpl.getAllLoansByCustomer(customerServiceImpl.findCustomerById(id_customer)));
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/loans");
            }
        }
        return modelAndView;
    }

    @GetMapping("/activatefalse")
    public ModelAndView viewAllNoActivated(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("loans");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("loans", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("loans", null);
        } else {
            try {
                modelAndView.addObject("loans", loanServiceImpl.getAllLoansNoActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/loans");
            }
        }
        return modelAndView;
    }

    @GetMapping
    public ModelAndView viewAllActivated(HttpServletRequest request, @RequestParam(required = false) String error) throws Exception {
        ModelAndView modelAndView = new ModelAndView("loans");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("loans", null);
        }
        if (error != null) {
            modelAndView.addObject("error", error);
            modelAndView.addObject("loans", null);
        } else {
            try {
                modelAndView.addObject("loans", loanServiceImpl.getAllLoansActivated());
            } catch (Exception exception) {
                modelAndView.addObject("error", exception.getMessage());
                modelAndView.setViewName("redirect:/loans");
            }
        }
        return modelAndView;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView createLoan(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("loanform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("loan", flashMap.get("loan"));
        } else {
            modelAndView.addObject("loan", new LoanEntity());
        }
        modelAndView.addObject("books", bookServiceImpl.getAllBooksActivated());
        modelAndView.addObject("customers", customerServiceImpl.getAllCustomersActivated());
        modelAndView.addObject("title", "Creación de Préstamo");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView saveLoan(@ModelAttribute LoanEntity loan, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/loans");
        try {
            loanServiceImpl.validateFormAndCreate(loan.getLoanDate(), loan.getReturnDate(), loan.getBook(), loan.getCustomer());
            redirectAttributes.addFlashAttribute("success", "El préstamo ha sido creado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("loan", loan);
            redirectView.setUrl("/loans/create");
        }
        return redirectView;
    }

    @GetMapping("/edit/{id_loan}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editLoan(@PathVariable Integer id_loan, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("loanform");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("loan", flashMap.get("loan"));
        } else {
            modelAndView.addObject("loan", loanServiceImpl.findLoanById(id_loan));
        }
        modelAndView.addObject("books", bookServiceImpl.getAllBooksActivated());
        modelAndView.addObject("customers", customerServiceImpl.getAllCustomersActivated());
        modelAndView.addObject("title", "Edicion de Préstamo");
        modelAndView.addObject("action", "modify");
        return modelAndView;
    }

    @PostMapping("/modify")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modifyLoan(@ModelAttribute LoanEntity loan, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/loans");
        try {
            loanServiceImpl.validateFormAndUpdate(loan.getId_loan(), loan.getLoanDate(), loan.getReturnDate(), loan.getBook(), loan.getCustomer(), loan.getActivate());
            redirectAttributes.addFlashAttribute("success", "El préstamo ha sido modificado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
            redirectAttributes.addFlashAttribute("loan", loan);
            redirectView.setUrl("/loans/edit/" + loan.getId_loan());
        }
        return redirectView;
    }

    @PostMapping("/delete/{id_loan}")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView deleteLoan(@PathVariable Integer id_loan, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/loans");
        try {
            loanServiceImpl.deleteLoan(id_loan);
            redirectAttributes.addFlashAttribute("success", "El préstamo ha sido eliminado exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return redirectView;
    }

    @PostMapping("/activate/{id_loan}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView activateLoan(@PathVariable Integer id_loan, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView("/loans");
        try {
            String aux = "";
            LoanEntity loanEntity = loanServiceImpl.findLoanById(id_loan);
            loanEntity.setActivate(!loanEntity.getActivate());
            if (loanEntity.getActivate()) {
                aux = "PENDIENTE";
                bookServiceImpl.updateBook(loanEntity.getBook().getId_book(), loanEntity.getBook().getIsbn(), loanEntity.getBook().getTitle(), loanEntity.getBook().getYear(), loanEntity.getBook().getCopies(), loanEntity.getBook().getLoanedCopies() + 1, loanEntity.getBook().getRemainingCopies() - 1, loanEntity.getBook().getAuthor(), loanEntity.getBook().getEditorial(), loanEntity.getBook().getActivate());
            } else {
                aux = "FINALIZADO";
                bookServiceImpl.updateBook(loanEntity.getBook().getId_book(), loanEntity.getBook().getIsbn(), loanEntity.getBook().getTitle(), loanEntity.getBook().getYear(), loanEntity.getBook().getCopies(), loanEntity.getBook().getLoanedCopies() - 1, loanEntity.getBook().getRemainingCopies() + 1, loanEntity.getBook().getAuthor(), loanEntity.getBook().getEditorial(), loanEntity.getBook().getActivate());
            }
            loanServiceImpl.updateLoan(loanEntity.getId_loan(), loanEntity.getLoanDate(), loanEntity.getReturnDate(), loanEntity.getBook(), loanEntity.getCustomer(), loanEntity.getActivate());
            redirectAttributes.addFlashAttribute("success", "El préstamo ha cambiado su estado a: " + aux + ", exitosamente!");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return redirectView;
    }

}
