module com.example.fuelcentergui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fuelcentergui to javafx.fxml;
    exports com.example.fuelcentergui;
}