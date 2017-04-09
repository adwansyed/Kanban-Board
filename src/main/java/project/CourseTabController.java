package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;

//This class controls the tabs of the grade calculator tab.

public class CourseTabController {
    private File file = new File("src/main/resources/data/Database.csv");

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public Tab getCourseTab() {
        return courseTab;
    }

    public void setCourseTab(Tab courseTab) {
        this.courseTab = courseTab;
    }

    public TextField getCourseName() {
        return courseName;
    }

    public void setCourseName(TextField courseName) {
        this.courseName = courseName;
    }

    public Label getCourseLabel() {
        return courseLabel;
    }

    public void setCourseLabel(Label courseLabel) {
        this.courseLabel = courseLabel;
    }

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab courseTab;

    @FXML
    private TextField courseName;

    @FXML
    private Label courseLabel;

    private Client client;

    public void initialize() throws IOException {
        client = new Client("127.0.0.1", 2222);

        //Loads all the data the user has inputted in previous session.
        ObservableList<Course> courses = client.getData("Get");
        for(Course c: courses) {
            Tab tab = new Tab(c.getName());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gradeCalculator.fxml"));
            tab.setContent(loader.load());
            tab.setStyle("-fx-background-color: #B0C4DE; ");
            tabPane.getTabs().add(tab);

            GradesCalculatorTabController controller = loader.getController();

            controller.getCourseNameField().setText(c.getName());
            controller.getTableView().setItems(c.getMarks());
            controller.getCourseItem().setCellValueFactory(( new PropertyValueFactory<>("courseItem")));
            controller.getWorth().setCellValueFactory(( new PropertyValueFactory<>("worth")));
            controller.getYourMark().setCellValueFactory(( new PropertyValueFactory<>("yourMark")));

            //update Labels
            for(CourseItem item: c.getMarks()) {
                controller.updateLables(item);
            }
        }
    }

    @FXML
    public void addTab(ActionEvent actionEvent) throws IOException {
        if(courseName.getText().trim().isEmpty()) {
            Tab tab = new Tab("New Course");
            tab.setStyle("-fx-background-color: #B0C4DE;");
            tabPane.getTabs().add(tab);
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/fxml/gradeCalculator.fxml")));
        }
        else {
            //courseLabel.textProperty().bind(courseName.getText());
            Tab tab = new Tab(courseName.getText());
            tab.setStyle("-fx-background-color: #B0C4DE;");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gradeCalculator.fxml"));
            tab.setContent(loader.load());
            tabPane.getTabs().add(tab);

            GradesCalculatorTabController controller = loader.getController();
            controller.getCourseNameField().setText(courseName.getText());
        }
    }
}