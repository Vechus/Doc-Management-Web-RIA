package it.polimi.tiw.beans;

public class UserRegisterData {
    private String email;
    private String username;
    private String passwordHash;
    private String passwordRepeatHash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordRepeatHash() {
        return passwordRepeatHash;
    }

    public void setPasswordRepeatHash(String passwordRepeatHash) {
        this.passwordRepeatHash = passwordRepeatHash;
    }
}
