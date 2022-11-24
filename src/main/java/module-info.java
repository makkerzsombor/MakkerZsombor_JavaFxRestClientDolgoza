module com.example.makkerzsombor_javafxrestclientdolgoza {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.makkerzsombor_javafxrestclientdolgoza to javafx.fxml;
    exports com.example.makkerzsombor_javafxrestclientdolgoza;
}