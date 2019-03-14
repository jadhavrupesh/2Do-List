package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DatabaseHandler;
import sample.animations.Shaker;
import sample.model.User;

public class SignupController {

    String gender=null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private JFXTextField singUpLastName;

    @FXML
    private JFXTextField signUpFirstName;

    @FXML
    private JFXTextField signUpUsername;

    @FXML
    private AnchorPane bgReg;

    @FXML
    private JFXPasswordField signUpPassword;

    @FXML
    private JFXTextField signUpLocation;

    @FXML
    private JFXCheckBox singUpCheckBoxMale;

    @FXML
    private JFXCheckBox singUpCheckBoxFemale;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private JFXButton loginSignupButton;



    @FXML
    void selectedFemale(MouseEvent event) {
        gender="Female";
    }

    @FXML
    void selectedMale(MouseEvent event) {
        gender="Male";
    }

    @FXML
    void alreadyhaveAccount(ActionEvent event) {
        loginSignupButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }



    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> {

            String name = signUpFirstName.getText();
            String lastName = singUpLastName.getText();
            String userName = signUpUsername.getText();
            String password = signUpPassword.getText();
            String location = signUpLocation.getText();
            String chkgender = gender;


            if (!name.trim().equals("") && !lastName.trim().equals("") &&
                    !userName.trim().equals("")&& !password.trim().equals("") && !location.trim().equals("") && !chkgender.equals("")) {
                createUser();
                signUpButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/login.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            }
            else {
                Shaker firstNameShaker = new Shaker(signUpFirstName);
                Shaker lastNameShaker = new Shaker(singUpLastName);
                Shaker userNameShaker = new Shaker(signUpUsername);
                Shaker passwordShaker = new Shaker(signUpPassword);
                Shaker locationShaker = new Shaker(signUpLocation);
                locationShaker.shake();
                firstNameShaker.shake();
                lastNameShaker.shake();
                passwordShaker.shake();
                userNameShaker.shake();
                signUpPassword.clear();

                System.out.println("Fill form");
            }

        });
    }

    private void createUser() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        String name = signUpFirstName.getText();
        String lastName = singUpLastName.getText();
        String userName = signUpUsername.getText();
        String password = signUpPassword.getText();
        String location = signUpLocation.getText();
        String chkgender=gender;

        if (!name.trim().equals("") && !lastName.trim().equals("") &&
                !userName.trim().equals("") && !location.trim().equals("")) {
            System.out.println("not null");
            User user = new User(name, lastName, userName, password, location, chkgender);
            databaseHandler.signUpUser(user);
        }  else {
            signUpFirstName.clear();
            singUpLastName.clear();
            signUpUsername.clear();
            signUpPassword.clear();
            singUpCheckBoxFemale.setSelected(false);
            singUpCheckBoxMale.setSelected(false);
            signUpLocation.clear();
        }

    }
}
