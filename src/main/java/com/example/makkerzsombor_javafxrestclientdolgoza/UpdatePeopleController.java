package com.example.makkerzsombor_javafxrestclientdolgoza;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdatePeopleController extends Controller{

    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> ageField;
    @FXML
    private Spinner<Integer> heightField;
    @FXML
    private CheckBox marriedCheck;
    @FXML
    private Button updateButton;
    private Person person;

    public void setPerson(Person person) {
        this.person = person;
        nameField.setText(person.getName());
        ageField.getValueFactory().setValue(this.person.getAge());
        heightField.getValueFactory().setValue(this.person.getHeight());
        if(this.person.isMarried()){
            marriedCheck.setSelected(true);
        }else{
            marriedCheck.setSelected(false);
        }
    }

    @FXML
    public void initialize() {
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,30);
        ageField.setValueFactory(valueFactory);
        SpinnerValueFactory.IntegerSpinnerValueFactory heightValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,200,150);
        heightField.setValueFactory(heightValueFactory);

    }
    @FXML
    public void updateClick(ActionEvent actionEvent) {
        String name = nameField.getText();
        int age = ageField.getValue();
        int height = heightField.getValue();
        boolean married;
        if(name.isEmpty()){
            warning("Name is required!");
            return;
        }
        if (marriedCheck.isSelected()){
            married = true;
        }else{
            married = false;
        }
        this.person.setName(name);
        this.person.setHeight(height);
        this.person.setAge(age);
        this.person.setMarried(married);
        Gson converter = new Gson();
        String json = converter.toJson(this.person);
        try {
            String url = App.BASE_URL + "/" + this.person.getId();
            Response response = RequestHandler.put(url, json);
            if (response.getResponseCode() == 200){
                Stage stage = (Stage) this.updateButton.getScene().getWindow();
                stage.close();
            } else{
                String content = response.getContent();
                error(content);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
