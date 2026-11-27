package Model;

/**
 * Represents a user of the movie theatre system.
 * A user has a username, email, and password.
 *
 * This class is abstract because users can be
 * different types such as customers and managers.
 *
 * @author Benjamin
 */
public abstract class User {

    /** The user's username. */
    private String username;

    /** The user's email address. */
    private String email;

    /** The user's password. */
    private String password;

    /**
     * Creates a new user.
     *
     * @param username The username of the user
     * @param email The email address of the user
     * @param password The password of the user
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     *
     * @return The email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email The new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password.
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role of the user.
     *
     * @return The user role
     */
    public abstract String getRole();
}