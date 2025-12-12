package io.jfxdevelop.lab6_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> resultsListView;

    private final DatabaseManager dbManager = new DatabaseManager();

    @FXML
    protected void onSearchButtonClick() {
        List<Student> students = dbManager.getStudentsEnrolledInMath();

        if (students.isEmpty()) {
            resultsListView.getItems().setAll("Нет студентов, записанных на курс 'Математика'.");
        } else {
            ObservableList<String> items = FXCollections.observableArrayList();
            for (Student student : students) {
                items.add(student.toString());
            }
            resultsListView.setItems(items);
        }
    }
}