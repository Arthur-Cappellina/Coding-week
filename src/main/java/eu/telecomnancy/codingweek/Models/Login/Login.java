package eu.telecomnancy.codingweek.Models.Login;

import eu.telecomnancy.codingweek.Models.BasicUser;
import eu.telecomnancy.codingweek.Models.DAO.*;
import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;

@Getter
public class Login {
    private String email;
    private String password;

    public Login() {}

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isValid() {
        return this.email != null && this.password != null;
    }

    public boolean checkEmail() {
        BasicUserDAO userDAO = BasicUserDAO.getInstance();
        return userDAO.getBasicUserWithEmail(this.email) != null;
    }

    public boolean checkPassword() {
        if (!this.checkEmail()) {
            return false;
        }
        String hash_pass = getHash();
        return BCrypt.checkpw(password, hash_pass);
    }

    public String getHash() {
        BasicUserDAO userDAO = BasicUserDAO.getInstance();
        BasicUser basicUser = userDAO.getBasicUserWithEmail(this.email);
        return basicUser.getPassword();
    }

    public String getErrorMessage() {
        if (!this.isValid()) {
            return "Veuillez remplir tous les champs.";
        } else if (!this.checkEmail()) {
            return "Email ou mot de passe incorrect.";
        } else if (!this.checkPassword()) {
            return "Email ou mot de passe incorrect.";
        }
        return "Erreur inconnue.";
    }

    public BasicUser login() {
        BasicUserDAO userDAO = BasicUserDAO.getInstance();
        if (this.checkEmail() && this.checkPassword()) {
            return userDAO.getBasicUserWithEmail(this.email);
        } else {
            throw new IllegalArgumentException(this.getErrorMessage());
        }
    }

}
