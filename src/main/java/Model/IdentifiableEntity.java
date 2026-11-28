package Model;

/**
 * Abstract base class for GVMT domain entities that have a positive numeric identifier.
 * Provides shared ID encapsulation, validation, and polymorphic access.
 */
public abstract class IdentifiableEntity {

    private final int id;

    /**
     * Constructs an entity with a validated positive identifier.
     *
     * @param id        unique positive identifier
     * @param errorCode label used in the validation exception message
     * @throws IllegalArgumentException if {@code id} is less than or equal to zero
     */
    protected IdentifiableEntity(int id, String errorCode) {
        if (id <= 0) {
            throw new IllegalArgumentException(errorCode + " ID must be greater than zero.");
        }
        this.id = id;
    }

    /**
     * Returns the unique identifier of this entity.
     *
     * @return positive entity ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a human-readable summary of this entity for polymorphic display.
     *
     * @return summary description
     */
    public abstract String getSummary();
}