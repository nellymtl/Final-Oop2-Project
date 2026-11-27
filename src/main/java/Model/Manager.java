package Model;

/**
 * Represents the theatre manager.
 *
 * @author Benjamin
 */
public class Manager extends User {

    public Manager(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getRole() {
        return "Manager";
    }
}