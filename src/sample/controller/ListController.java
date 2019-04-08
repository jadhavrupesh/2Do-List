package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class ListController {

    @FXML
    private ImageView listRefreshButton;

    @FXML
    private JFXListView<Task> listTask;
    @FXML
    public JFXTextField listTaskField;
    @FXML
    public JFXTextField listDescriptionField;
    @FXML
    public JFXButton listSaveTaskButton;

    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;

    private DatabaseHandler databaseHandler;

    @FXML
    private JFXButton listLogout;

    @FXML
    private JFXTextField search;

    @FXML
    private JFXButton searchBtn;

    @FXML
    void dosearch(ActionEvent event) throws SQLException {

        databaseHandler = new DatabaseHandler();
        String tast = search.getText().toString();
//        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);
        ResultSet resultSet = databaseHandler.search(tast);
        System.out.println("initialize called");
        tasks = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            tasks.addAll(task);
        }
        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());
    }

    @FXML
    void initialize() throws SQLException {

        listLogout.setOnAction(event -> {

            listLogout.getScene().getWindow().hide();
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

        });

        System.out.println("initialize called");

        tasks = FXCollections.observableArrayList();


        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            tasks.addAll(task);
        }


        listTask.setItems(tasks);

        listTask.setCellFactory(CellController -> new CellController());


        listRefreshButton.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        listSaveTaskButton.setOnAction(event -> {
            addNewTask();

        });


    }

    public void refreshList() throws SQLException {


        System.out.println("refreshList in ListCont called");

        refreshedTasks = FXCollections.observableArrayList();


        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userId);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskId(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));
            refreshedTasks.addAll(task);

        }
        listTask.setItems(refreshedTasks);
        listTask.setCellFactory(CellController -> new CellController());
    }

    public void addNewTask() {

        if (!listTaskField.getText().equals("")
                || !listDescriptionField.getText().equals("")) {
            Task myNewTask = new Task();
            Calendar calendar = Calendar.getInstance();

            Timestamp timestamp =
                    new Timestamp(calendar.getTimeInMillis());

            myNewTask.setUserId(AddItemController.userId);
            myNewTask.setTask(listTaskField.getText().trim());
            myNewTask.setDescription(listDescriptionField.getText().trim());
            myNewTask.setDatecreated(timestamp);
            databaseHandler.insertTask(myNewTask);
            listTaskField.setText("");
            listDescriptionField.setText("");
            try {
                initialize();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


