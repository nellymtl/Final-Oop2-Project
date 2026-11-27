package Model;

/**
 * Represents a customer of the movie theatre system.
 *
 * Customers can browse movies and make payments.
 *
 * @author Benjamin
 */
public class Customer extends User {

    /**
     * Creates a new customer.
     *
     * @param username The customer's username
     * @param email The customer's email address
     * @param password The customer's password
     */
    public Customer(String username, String email, String password) {
        super(username, email, password);
    }

    /**
     * Returns the role of this user.
     *
     * @return Customer
     */
    @Override
    public String getRole() {
        return "Customer";
    }
}