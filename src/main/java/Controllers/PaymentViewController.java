package com.example.grandviewmovietheatre;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for {@code payment-view.fxml} in the Grand View Movie Theatre (GVMT)
 * client booking workflow. Displays the amount due, validates payment fields using
 * exceptions, and handles confirmation or navigation back to the summary view.
 */

/**
 * @author :Aya
 */
public class PaymentViewController {

    private static final NumberFormat CURRENCY_FORMAT =
            NumberFormat.getCurrencyInstance(Locale.CANADA);

    @FXML private Label amountDueLabel;
    @FXML private ComboBox<String> paymentMethodCombo;
    @FXML private TextField cardholderField;
    @FXML private TextField cardNumberField;
    @FXML private TextField expiryField;
    @FXML private TextField cvvField;
    @FXML private Label errorLabel;
    @FXML private Button backButton;
    @FXML private Button payButton;

    private BigDecimal amountDue = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    /**
     * Initializes UI controls and sets the default currency display.
     */
    @FXML
    private void initialize() {
        paymentMethodCombo.setItems(FXCollections.observableArrayList(
                "Credit Card",
                "Debit Card"
        ));
        paymentMethodCombo.getSelectionModel().selectFirst();
        updateAmountDueLabel();
        clearError();
    }

    /**
     * Sets the total amount due for the current ticket order and refreshes the label.
     *
     * @param amountDue total order amount including taxes
     */
    public void setAmountDue(BigDecimal amountDue) {
        if (amountDue == null || amountDue.compareTo(BigDecimal.ZERO) < 0) {
            this.amountDue = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        } else {
            this.amountDue = amountDue.setScale(2, RoundingMode.HALF_UP);
        }
        updateAmountDueLabel();
    }

    /**
     * Handles the Confirm Payment button action, validating inputs and showing
     * confirmation or validation error feedback.
     */
    @FXML
    private void handleConfirmPayment() {
        clearError();
        try {
            validatePaymentDetails();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Confirmed");
            alert.setHeaderText("Thank you for your purchase!");
            alert.setContentText(
                    "Payment of " + CURRENCY_FORMAT.format(amountDue)
                            + " (" + paymentMethodCombo.getValue() + ") was processed."
            );
            alert.showAndWait();

            resetForm();
            navigateTo("/com/example/grandviewmovietheatre/browser-view.fxml", "GVMT");
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        } catch (Exception ex) {
            errorLabel.setText("Unable to complete payment due to an unexpected error.");
        }
    }

    /**
     * Validates all payment fields and throws {@link IllegalArgumentException}
     * with a user-friendly message if any input is invalid.
     *
     * @throws IllegalArgumentException when a required payment field is missing or malformed
     */
    private void validatePaymentDetails() {
        if (amountDue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount due must be greater than $0.00.");
        }

        String method = paymentMethodCombo.getValue();
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException("Please select a payment method.");
        }

        String cardholder = cardholderField.getText();
        if (cardholder == null || cardholder.trim().length() < 2) {
            throw new IllegalArgumentException("Please enter a valid cardholder name.");
        }

        String cardDigits = cardNumberField.getText() == null
                ? ""
                : cardNumberField.getText().replaceAll("\\s+", "");
        if (!cardDigits.matches("\\d{13,19}")) {
            throw new IllegalArgumentException("Card number must contain 13 to 19 digits.");
        }

        String expiry = expiryField.getText() == null ? "" : expiryField.getText().trim();
        if (!expiry.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            throw new IllegalArgumentException("Expiration date must follow MM/YY format.");
        }

        String cvv = cvvField.getText() == null ? "" : cvvField.getText().trim();
        if (!cvv.matches("\\d{3,4}")) {
            throw new IllegalArgumentException("CVV must be 3 or 4 digits.");
        }
    }

    /**
     * Navigates back to the order summary view (or fallback view if not yet added).
     */
    @FXML
    private void handleBackToSummary() {
        clearError();
        // Update this path once your summary FXML file is added to resources
        navigateTo("/com/example/grandviewmovietheatre/browser-view.fxml", "GVMT — Summary");
    }

    private void updateAmountDueLabel() {
        if (amountDueLabel != null) {
            amountDueLabel.setText(CURRENCY_FORMAT.format(amountDue));
        }
    }

    private void clearError() {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    private void resetForm() {
        cardholderField.clear();
        cardNumberField.clear();
        expiryField.clear();
        cvvField.clear();
        paymentMethodCombo.getSelectionModel().selectFirst();
    }

    private void navigateTo(String resourcePath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            Parent root = loader.load();
            Stage stage = (Stage) payButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (IOException ex) {
            errorLabel.setText("Could not load view: " + resourcePath);
        }
    }
}