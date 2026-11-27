package Model;

/**
 * Represents a movie in the Grand View Movie Theatre (GVMT) system,
 * corresponding to the {@code MOVIE} entity in the conceptual EER model.
 */
public class Movie {

    private final int movieId;
    private String title;
    private String genre;
    private int durationMinutes;
    private String rating;

    /**
     * Constructs a validated {@code Movie} instance.
     *
     * @param movieId         unique movie identifier (must be positive)
     * @param title           movie title (must not be blank)
     * @param genre           movie genre (must not be blank)
     * @param durationMinutes runtime in minutes (must be greater than zero)
     * @param rating          age or content rating (must not be blank)
     * @throws IllegalArgumentException if any field fails validation
     */
    public Movie(int movieId, String title, String genre, int durationMinutes, String rating) {
        if (movieId <= 0) {
            throw new IllegalArgumentException("Movie ID must be greater than zero.");
        }
        this.movieId = movieId;
        setTitle(title);
        setGenre(genre);
        setDurationMinutes(durationMinutes);
        setRating(rating);
    }

    /**
     * Returns the unique identifier of this movie.
     *
     * @return movie ID
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Returns the title of this movie.
     *
     * @return movie title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the movie title after validating it is non-blank.
     *
     * @param title new movie title
     * @throws IllegalArgumentException if {@code title} is null or blank
     */
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Movie title cannot be empty.");
        }
        this.title = title.trim();
    }

    /**
     * Returns the genre of this movie.
     *
     * @return movie genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Updates the movie genre after validating it is non-blank.
     *
     * @param genre new movie genre
     * @throws IllegalArgumentException if {@code genre} is null or blank
     */
    public void setGenre(String genre) {
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("Movie genre cannot be empty.");
        }
        this.genre = genre.trim();
    }

    /**
     * Returns the movie duration in minutes.
     *
     * @return duration in minutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Updates the movie duration after validating it is positive.
     *
     * @param durationMinutes runtime in minutes
     * @throws IllegalArgumentException if {@code durationMinutes} is less than or equal to zero
     */
    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0 minutes.");
        }
        this.durationMinutes = durationMinutes;
    }

    /**
     * Returns the rating of this movie.
     *
     * @return movie rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Updates the movie rating after validating it is non-blank.
     *
     * @param rating new movie rating
     * @throws IllegalArgumentException if {@code rating} is null or blank
     */
    public void setRating(String rating) {
        if (rating == null || rating.isBlank()) {
            throw new IllegalArgumentException("Movie rating cannot be empty.");
        }
        this.rating = rating.trim();
    }
}