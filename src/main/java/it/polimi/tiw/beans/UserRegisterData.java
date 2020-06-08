package it.polimi.tiw.beans;

/**
 * The type User register data.
 */
public class UserRegisterData {
    private String email;
    private String username;
    private String passwordHash;
    private String passwordRepeatHash;

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password hash.
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets password hash.
     *
     * @param passwordHash the password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets password repeat hash.
     *
     * @return the password repeat hash
     */
    public String getPasswordRepeatHash() {
        return passwordRepeatHash;
    }

    /**
     * Sets password repeat hash.
     *
     * @param passwordRepeatHash the password repeat hash
     */
    public void setPasswordRepeatHash(String passwordRepeatHash) {
        this.passwordRepeatHash = passwordRepeatHash;
    }
}
