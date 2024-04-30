package it.marconi.login_registrazione.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;


}
