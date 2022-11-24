package com.example.makkerzsombor_javafxrestclientdolgoza;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ListPeopleController extends Controller{

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn ageCol;
    @FXML
    private TableColumn heightCol;
    @FXML
    private TableColumn marriedCol;
    @FXML
    private TableView<Person> peopleTable;

    @FXML
    public void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        marriedCol.setCellValueFactory(new PropertyValueFactory<>("married"));
        Platform.runLater(() -> {
            try {
                loadPeopleFromServer();
                //System.out.println(content);
            } catch (IOException e) {
                error("Couldn't get data from server!");
                Platform.exit();
            }
        });
    }

    private void loadPeopleFromServer() throws IOException{
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Person[] people = converter.fromJson(content, Person[].class);
        peopleTable.getItems().clear();
        for (Person person : people) {
            peopleTable.getItems().add(person);
        }
    }

    @FXML
    public void insertClick(ActionEvent actionEvent) {

    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {

    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {

    }
}