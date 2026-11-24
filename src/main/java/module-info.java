module com.example.grandviewmovietheatre {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grandviewmovietheatre to javafx.fxml;
    exports com.example.grandviewmovietheatre;
}