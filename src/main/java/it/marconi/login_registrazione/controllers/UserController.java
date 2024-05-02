package it.marconi.login_registrazione.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.marconi.login_registrazione.domains.RegistrationForm;
import it.marconi.login_registrazione.services.UserService;

@Controller
@RequestMapping("/")
public class UserController {

    // non era necessario vista la mancanza di db, ma è comunque buona pratica
    @Autowired
    UserService userService;

    @GetMapping
    public ModelAndView viewHomePage() {

        return new ModelAndView("homepage");
    }

    //localhost:8090/home/user?type=
    @GetMapping("/user")
    public ModelAndView handlerUserAction(@RequestParam("type") String type) {

        // in base al parametro, mostro la pagina relativa
        if(type.equals("new"))
            return new ModelAndView("user-registration").addObject("userForm", new RegistrationForm());
        
        else if(type.equals("login"))
            return new ModelAndView("user-login").addObject("userForm", new RegistrationForm());
        
        // se il parametro è errato, pagina non trovata
        else 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagina non trovata!");
    }

    @PostMapping("/user/login")
    public ModelAndView handlerLoggedUser(
        @ModelAttribute RegistrationForm userForm,
        RedirectAttributes attr
    ) {
        String username = userForm.getUsername();     
        Optional<RegistrationForm> user = userService.getUserByUsername(username);

        // se trovo l'utente, mostro pagina recap
        if (user.isPresent()) {
            return new ModelAndView("redirect:/user/" + username);
        }
        // se non lo trovo, ricarico la pagina di login con un messaggio di errore
        else {
            attr.addFlashAttribute("notfound", true);
            return new ModelAndView("redirect:/user?type=login");
        }
    }

    @PostMapping("/user/new")
    public ModelAndView handlerNewUser(@ModelAttribute RegistrationForm userForm) {

        // salvo l'utente nel "database"
        userService.addUser(userForm); 

        String username = userForm.getUsername();         
        return new ModelAndView("redirect:/user/" + username);
    }

    @GetMapping("/user/{username}")
    public  ModelAndView userDetail(@PathVariable("username") String username) {

        Optional<RegistrationForm> user = userService.getUserByUsername(username);

        // se l'utente esiste, mostro la pagina di recap
        if (user.isPresent())
            return new ModelAndView("user-detail").addObject("user", user.get());
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato!");
    }
}
