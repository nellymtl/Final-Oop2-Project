package Model;
import com.example.grandviewmovietheatre.ScreeningRoom;

import java.time.LocalDateTime;

/**
 * Represents a scheduled screening of a {@link Movie} inside a {@link ScreeningRoom},
 * corresponding to the {@code SHOWTIME} entity in the conceptual EER model.
 */
public class Showtime {

    private final int showtimeId;
    private Movie movie;
    private ScreeningRoom screeningRoom;
    private LocalDateTime showDateTime;

    /**
     * Constructs a validated {@code Showtime} instance.
     *
     * @param showtimeId    unique showtime identifier (must be positive)
     * @param movie         scheduled movie (must not be null)
     * @param screeningRoom hosting screening room (must not be null)
     * @param showDateTime  scheduled date and time (must not be null)
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Showtime(int showtimeId, Movie movie, ScreeningRoom screeningRoom,
                    LocalDateTime showDateTime) {
        if (showtimeId <= 0) {
            throw new IllegalArgumentException("Showtime ID must be greater than zero.");
        }
        this.showtimeId = showtimeId;
        setMovie(movie);
        setScreeningRoom(screeningRoom);
        setShowDateTime(showDateTime);
    }

    /**
     * Returns the unique identifier of this showtime.
     *
     * @return showtime ID
     */
    public int getShowtimeId() {
        return showtimeId;
    }

    /**
     * Returns the movie scheduled for this showtime.
     *
     * @return scheduled {@link Movie}
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Updates the movie scheduled for this showtime.
     *
     * @param movie new {@link Movie} instance
     * @throws IllegalArgumentException if {@code movie} is null
     */
    public void setMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null.");
        }
        this.movie = movie;
    }

    /**
     * Returns the screening room hosting this showtime.
     *
     * @return hosting {@link ScreeningRoom}
     */
    public ScreeningRoom getScreeningRoom() {
        return screeningRoom;
    }

    /**
     * Updates the screening room hosting this showtime.
     *
     * @param screeningRoom new {@link ScreeningRoom} instance
     * @throws IllegalArgumentException if {@code screeningRoom} is null
     */
    public void setScreeningRoom(ScreeningRoom screeningRoom) {
        if (screeningRoom == null) {
            throw new IllegalArgumentException("Screening room cannot be null.");
        }
        this.screeningRoom = screeningRoom;
    }

    /**
     * Returns the scheduled date and time of this showtime.
     *
     * @return show date and time
     */
    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }

    /**
     * Updates the scheduled date and time of this showtime.
     *
     * @param showDateTime new show date and time
     * @throws IllegalArgumentException if {@code showDateTime} is null
     */
    public void setShowDateTime(LocalDateTime showDateTime) {
        if (showDateTime == null) {
            throw new IllegalArgumentException("Show date and time cannot be null.");
        }
        this.showDateTime = showDateTime;
    }

    /**
     * Returns the movie identifier from the scheduled {@link Movie}.
     *
     * @return movie ID
     */
    public int getMovieId() {
        return movie.getMovieId();
    }

    /**
     * Returns the screening room identifier from the hosting {@link ScreeningRoom}.
     *
     * @return room ID
     */
    public int getRoomId() {
        return screeningRoom.getRoomId();
    }
}