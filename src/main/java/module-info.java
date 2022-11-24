module com.example.makkerzsombor_javafxrestclientdolgoza {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.makkerzsombor_javafxrestclientdolgoza to javafx.fxml, com.google.gson;
    exports com.example.makkerzsombor_javafxrestclientdolgoza;
}