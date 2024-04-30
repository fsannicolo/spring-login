package it.marconi.login_registrazione.controllers;

import java.util.ArrayList;

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

import it.marconi.login_registrazione.domains.RegistrationForm;


@Controller
@RequestMapping("/")
public class UserController {

    ArrayList<RegistrationForm> users = new ArrayList<>();

    @GetMapping
    public ModelAndView viewHomePage() {

        users.clear();
        return new ModelAndView("homepage");
    }

    //localhost:8090/home/user?type=
    @GetMapping("/user")
    public ModelAndView handlerUserAction(@RequestParam("type") String type) {

        //gli passo 
        if(type.equals("new"))
            return new ModelAndView("user-registration").addObject("userForm", new RegistrationForm());
        
        else if(type.equals("login"))
            return new ModelAndView("user-login").addObject("userForm", new RegistrationForm());
        
        else 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagina non trovata!");

    }


    @PostMapping("/user/new")
    public  ModelAndView redirectHandler(@ModelAttribute("newUser") RegistrationForm newUser) {

        String username = newUser.getUsername();
        users.add(newUser);

        //return new ModelAndView("user-detail").addObject("newUser", newUser);
        return new ModelAndView("redirect:/user/"+username);
        
    }

    @GetMapping("/user/{username}")
    public  ModelAndView userDetail(@PathVariable("username") String username) {

        RegistrationForm user = new RegistrationForm();

        for (RegistrationForm u : users) {
            if(u.getUsername().equals(username)){
                user = u;
            }
        }

        return new ModelAndView("user-detail").addObject("user", user);
    }
   
}
