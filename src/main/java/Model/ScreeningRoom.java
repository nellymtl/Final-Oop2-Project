package Model;

/**
 * Represents a screening room in the Grand View Movie Theatre (GVMT) system,
 * corresponding to the {@code SCREENING_ROOM} entity in the conceptual EER model.
 */
public class ScreeningRoom {

    private final int roomId;
    private String roomName;
    private int capacity;

    /**
     * Constructs a validated {@code ScreeningRoom} instance.
     *
     * @param roomId   unique screening room identifier (must be positive)
     * @param roomName room name or label (must not be blank)
     * @param capacity maximum seating capacity (must be greater than zero)
     * @throws IllegalArgumentException if any attribute fails validation
     */
    public ScreeningRoom(int roomId, String roomName, int capacity) {
        if (roomId <= 0) {
            throw new IllegalArgumentException("Room ID must be greater than zero.");
        }
        this.roomId = roomId;
        setRoomName(roomName);
        setCapacity(capacity);
    }

    /**
     * Returns the unique identifier of this screening room.
     *
     * @return room ID
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Returns the name of this screening room.
     *
     * @return room name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Updates the room name after validating it is non-blank.
     *
     * @param roomName new screening room name
     * @throws IllegalArgumentException if {@code roomName} is null or blank
     */
    public void setRoomName(String roomName) {
        if (roomName == null || roomName.isBlank()) {
            throw new IllegalArgumentException("Room name cannot be empty.");
        }
        this.roomName = roomName.trim();
    }

    /**
     * Returns the seating capacity of this screening room.
     *
     * @return room capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Updates the seating capacity after validating it is positive.
     *
     * @param capacity new seating capacity
     * @throws IllegalArgumentException if {@code capacity} is less than or equal to zero
     */
    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Room capacity must be greater than 0.");
        }
        this.capacity = capacity;
    }
}