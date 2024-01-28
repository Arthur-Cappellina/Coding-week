package eu.telecomnancy.codingweek.Models.Login;

import eu.telecomnancy.codingweek.Models.BasicUser;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import eu.telecomnancy.codingweek.Models.DAO.*;
import org.mindrot.jbcrypt.BCrypt;

@AllArgsConstructor
@Getter
@Setter
public class Registration {
    private String email;
    private String password;
    private String passwordConfirmation;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String postalAddress;
    private String city;
    private String hash;

    public Registration() {
        this.email = null;
        this.password = null;
        this.passwordConfirmation = null;
        this.firstName = null;
        this.lastName = null;
        this.zipCode = null;
        this.postalAddress = null;
        this.city = null;
        this.hash = null;
    }

    public boolean  isValid() {
        return this.email != null && this.password != null && this.passwordConfirmation != null && this.firstName != null && this.lastName != null && this.zipCode != null && this.postalAddress != null && this.city != null;
    }

    public boolean isPasswordValid() {
        return this.password.equals(this.passwordConfirmation);
    }

    public boolean isEmailValid() {
        return this.email.matches("^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public boolean isZipCodeValid() {
        return this.zipCode.matches("^[0-9]{5}$");
    }

    public boolean isPostalAddressValid() {
        return this.postalAddress.matches("^[0-9]{1,5} ([\\w-]+\\s?)+");
    }

    public boolean isCityValid() {
        return this.city.matches("^[\\w-]+");
    }

    public boolean isFirstNameValid() {
        return this.firstName.matches("^[\\w-]+");
    }

    public boolean isLastNameValid() {
        return this.lastName.matches("^[\\w-]+");
    }

    public boolean checkEmail() {
        BasicUserDAO userDAO = BasicUserDAO.getInstance();
        return userDAO.getBasicUserWithEmail(this.email) == null;
    }

    public boolean isRegistrationValid() {
        return this.isValid() && this.isPasswordValid() && this.isEmailValid() && this.isZipCodeValid() && this.isPostalAddressValid() && this.isCityValid() && this.isFirstNameValid() && this.isLastNameValid() && this.checkEmail();
    }

    public String getErrorMessage() {
        if (!this.isValid()) {
            return "Veuillez remplir tous les champs.";
        }
        if (!this.isPasswordValid()) {
            return "Les mots de passe ne correspondent pas.";
        }
        if (!this.isEmailValid()) {
            return "L'adresse email n'est pas valide.";
        }
        if (!this.isZipCodeValid()) {
            return "Le code postal n'est pas valide.";
        }
        if (!this.isPostalAddressValid()) {
            return "L'adresse postale n'est pas valide.";
        }
        if (!this.isCityValid()) {
            return "La ville n'est pas valide.";
        }
        if (!this.isFirstNameValid()) {
            return "Le prénom n'est pas valide.";
        }
        if (!this.isLastNameValid()) {
            return "Le nom n'est pas valide.";
        }
        if (!this.checkEmail()) {
            return "L'adresse email est déjà utilisée.";
        }
        return "Erreur inconnue.";
    }

    public void createHash() {
        this.hash =  BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public BasicUser signup() {
        BasicUserDAO userDAO = BasicUserDAO.getInstance();
        this.createHash();
        if (this.isRegistrationValid()) {
            BasicUser user = new BasicUser(this.firstName, this.lastName, this.zipCode, this.postalAddress, this.city, this.email, this.hash);
            userDAO.insertBasicUser(user);
            return user;
        } else {
            throw new IllegalArgumentException(this.getErrorMessage());
        }
    }

}
