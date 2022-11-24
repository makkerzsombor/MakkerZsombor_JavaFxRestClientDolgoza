package com.example.makkerzsombor_javafxrestclientdolgoza;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreatePeopleController extends Controller{

    @FXML
    private Button submitButton;
    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> heightField;
    @FXML
    private Spinner<Integer> ageField;
    @FXML
    private CheckBox marriedCheck = new CheckBox();

    @FXML
    public void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,30);
        ageField.setValueFactory(valueFactory);
        SpinnerValueFactory.IntegerSpinnerValueFactory heightFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,150);
        heightField.setValueFactory(heightFactory);
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) {
        String name = nameField.getText();
        int age = ageField.getValue();
        int height = heightField.getValue();
        boolean married;
        if(name.isEmpty()){
            warning("Name is required!");
        }
        if (marriedCheck.isSelected()){
            married = true;
        }else{
            married = false;
        }
        Person newPerson = new Person(0, name, age, married, height);
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newPerson);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode() == 201){
                warning("Person Added!");
                nameField.setText("");
                ageField.getValueFactory().setValue(30);
                heightField.getValueFactory().setValue(100);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
