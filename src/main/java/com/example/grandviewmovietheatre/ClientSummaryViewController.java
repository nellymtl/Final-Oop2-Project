package com.example.grandviewmovietheatre;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

/**
 * Controller for {@code client-summary-view.fxml} in the Grand View Movie Theatre
 * (GVMT) client booking flow. Displays movie and showtime details, calculates
 * subtotal, taxes, and total amount, validates ticket count with exceptions,
 * and passes the total to {@link PaymentViewController}.
 */
public class ClientSummaryViewController {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.14975");
    private static final NumberFormat CURRENCY_FORMAT =
            NumberFormat.getCurrencyInstance(Locale.CANADA);
    private static final DateTimeFormatter SHOWTIME_FORMATTER =
            DateTimeFormatter.ofPattern("EEE, MMM d, yyyy 'at' HH:mm");

    @FXML private Label movieTitleLabel;
    @FXML private Label roomLabel;
    @FXML private Label showtimeLabel;
    @FXML private Label unitPriceLabel;
    @FXML private Spinner<Integer> ticketQuantitySpinner;
    @FXML private Label availableSeatsLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalAmountLabel;
    @FXML private Label errorLabel;
    @FXML private Button backButton;
    @FXML private Button proceedButton;

    private String movieTitle = "Selected Movie";
    private String screeningRoom = "Room 1";
    private LocalDateTime showtime = LocalDateTime.now();
    private BigDecimal ticketPrice = new BigDecimal("15.00");
    private int availableSeats = 50;
    private int ticketQuantity = 1;

    /**
     * Initializes the ticket quantity spinner and displays default summary values.
     */
    @FXML
    private void initialize() {
        configureSpinner(1, Math.max(1, availableSeats), ticketQuantity);
        refreshSummaryDisplay();
        clearError();
    }

    /**
     * Populates the summary view with movie, screening room, showtime, pricing,
     * and seat availability details.
     *
     * @param movieTitle     title of the selected movie
     * @param screeningRoom  name or identifier of the screening room
     * @param showtime       scheduled date and time of the screening
     * @param ticketPrice    price per ticket (must be positive)
     * @param availableSeats remaining seats available for the showtime
     * @param initialQty     initial number of tickets selected
     */
    public void setSummaryDetails(String movieTitle, String screeningRoom, LocalDateTime showtime,
                                  BigDecimal ticketPrice, int availableSeats, int initialQty) {
        clearError();
        try {
            if (movieTitle == null || movieTitle.isBlank()) {
                throw new IllegalArgumentException("Movie title is required.");
            }
            if (screeningRoom == null || screeningRoom.isBlank()) {
                throw new IllegalArgumentException("Screening room is required.");
            }
            if (showtime == null) {
                throw new IllegalArgumentException("Showtime is required.");
            }
            if (ticketPrice == null || ticketPrice.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Ticket price must be greater than $0.00.");
            }
            if (availableSeats <= 0) {
                throw new IllegalArgumentException("No seats are available for this showtime.");
            }

            this.movieTitle = movieTitle.trim();
            this.screeningRoom = screeningRoom.trim();
            this.showtime = showtime;
            this.ticketPrice = ticketPrice.setScale(2, RoundingMode.HALF_UP);
            this.availableSeats = availableSeats;

            configureSpinner(1, availableSeats, initialQty);
            validateTicketQuantity(initialQty);
            this.ticketQuantity = initialQty;
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        }
        refreshSummaryDisplay();
    }

    private void configureSpinner(int min, int max, int current) {
        int safeCurrent = Math.min(Math.max(current, min), max);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, safeCurrent);
        ticketQuantitySpinner.setValueFactory(valueFactory);

        ticketQuantitySpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                handleQuantityInput(newVal);
            }
        });
    }

    private void handleQuantityInput(int candidateQty) {
        clearError();
        try {
            validateTicketQuantity(candidateQty);
            this.ticketQuantity = candidateQty;
            refreshSummaryDisplay();
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
            ticketQuantitySpinner.getValueFactory().setValue(this.ticketQuantity);
        }
    }

    /**
     * Validates the requested ticket count and throws {@link IllegalArgumentException}
     * if it is less than 1 or greater than available seats.
     *
     * @param quantity requested ticket count
     * @throws IllegalArgumentException when quantity is out of valid bounds
     */
    private void validateTicketQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Please select at least 1 ticket.");
        }
        if (quantity > availableSeats) {
            throw new IllegalArgumentException(
                    "Only " + availableSeats + " seat(s) available for this showtime."
            );
        }
    }

    /**
     * Calculates the order subtotal before tax.
     *
     * @return subtotal rounded to 2 decimal places
     */
    public BigDecimal calculateSubtotal() {
        return ticketPrice
                .multiply(BigDecimal.valueOf(ticketQuantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the tax amount for the current subtotal.
     *
     * @return tax amount rounded to 2 decimal places
     */
    public BigDecimal calculateTax() {
        return calculateSubtotal()
                .multiply(TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the final total amount including taxes.
     *
     * @return total amount rounded to 2 decimal places
     */
    public BigDecimal calculateTotal() {
        return calculateSubtotal()
                .add(calculateTax())
                .setScale(2, RoundingMode.HALF_UP);
    }

    @FXML
    private void handleProceedToPayment() {
        clearError();
        try {
            String rawText = ticketQuantitySpinner.getEditor().getText();
            int parsedQty = parseQuantityText(rawText);
            validateTicketQuantity(parsedQty);
            this.ticketQuantity = parsedQty;
            ticketQuantitySpinner.getValueFactory().setValue(parsedQty);
            refreshSummaryDisplay();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/grandviewmovietheatre/payment-view.fxml")
            );
            Parent root = loader.load();

            PaymentViewController paymentController = loader.getController();
            paymentController.setAmountDue(calculateTotal());

            Stage stage = (Stage) proceedButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GVMT — Payment");
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        } catch (IOException ex) {
            errorLabel.setText("Unable to load the payment view.");
        }
    }

    private int parseQuantityText(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket quantity cannot be empty.");
        }
        try {
            return Integer.parseInt(rawText.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Ticket quantity must be a whole number.");
        }
    }

    @FXML
    private void handleBack() {
        clearError();
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/grandviewmovietheatre/browser-view.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GVMT — Browse");
        } catch (IOException ex) {
            errorLabel.setText("Unable to navigate back.");
        }
    }

    private void refreshSummaryDisplay() {
        movieTitleLabel.setText(movieTitle);
        roomLabel.setText(screeningRoom);
        showtimeLabel.setText(showtime.format(SHOWTIME_FORMATTER));
        unitPriceLabel.setText(CURRENCY_FORMAT.format(ticketPrice));
        availableSeatsLabel.setText("(" + availableSeats + " available)");
        subtotalLabel.setText(CURRENCY_FORMAT.format(calculateSubtotal()));
        taxLabel.setText(CURRENCY_FORMAT.format(calculateTax()));
        totalAmountLabel.setText(CURRENCY_FORMAT.format(calculateTotal()));
    }

    private void clearError() {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }
}

