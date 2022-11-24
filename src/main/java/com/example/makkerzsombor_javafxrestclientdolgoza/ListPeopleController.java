package com.example.makkerzsombor_javafxrestclientdolgoza;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("create-people-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 480);
            Stage stage = new Stage();
            stage.setTitle("Create People");
            stage.setScene(scene);
            stage.showAndWait();
            stage.setOnCloseRequest(event -> {
                try {
                    loadPeopleFromServer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        int selectedIndex = peopleTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            warning("Please select a person from the list first!");
            return;
        }
        Person selected = peopleTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-people-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 480);
            Stage stage = new Stage();
            stage.setTitle("Update People");
            stage.setScene(scene);
            UpdatePeopleController controller = fxmlLoader.getController();
            controller.setPerson(selected);
            stage.show();
            insertButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            stage.setOnHidden(event -> {
                insertButton.setDisable(false);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
                try {
                    loadPeopleFromServer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        int selectedIndex = peopleTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            warning("Please select a person from the list first!");
            return;
        }
        Person selected = peopleTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(String.format("Are you sure you want to delete %s?", selected.getName()));
        Optional<ButtonType> optionalButtonType = confirmation.showAndWait();
        if (optionalButtonType.isEmpty()){
            System.err.println("Unknown error has occurred!");
            return;
        }
        ButtonType clickedButton = optionalButtonType.get();
        if (clickedButton.equals(ButtonType.OK)){
            String url = App.BASE_URL + "/" + selected.getId();
            try {
                RequestHandler.delete(url);
                loadPeopleFromServer();
            } catch (IOException e) {
                error("An error occurred while communicating with the server");
            }
        }
    }
}