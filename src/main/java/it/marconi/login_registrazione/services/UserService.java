package it.marconi.login_registrazione.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.marconi.login_registrazione.domains.RegistrationForm;

@Service
public class UserService {
    
    // creo un finto "database" dove salvare gli utenti registrati
    private ArrayList<RegistrationForm> users = new ArrayList<>();

    public ArrayList<RegistrationForm> getUsers() {
        return users;
    }

    public void addUser(RegistrationForm newUser) {
        users.add(newUser);
    }

    public Optional<RegistrationForm> getUserByUsername(String username) {

        for(RegistrationForm u : users) {
            if(u.getUsername().equals(username)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
}
