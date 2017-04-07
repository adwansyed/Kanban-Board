package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public void initialize() throws IOException {
        //Loads all the data the user has inputted in previous session.
        ObservableList<CourseItem> marks = FXCollections.observableArrayList();
        ObservableList<String> courseNames = FXCollections.observableArrayList();
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try {
            //System.out.println(file);
            //System.out.println(file.getAbsoluteFile());
            //String curDir = System.getProperty("data/Database.csv");
            //File GradeList = new File("data/Database.csv");
            //System.out.println("Current sys dir: " + curDir);
            //System.out.println("Current abs dir: " + GradeList.getAbsolutePath());
            FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);

            String line;

            while ((line = in.readLine()) != null) {
                String[] dataFields = line.split(",");
                if(courseNames.contains(dataFields[0])) {
                    for(Course c: courses) {
                        if (c.getName().equals(dataFields[0])) {
                            c.getMarks().add(new CourseItem(dataFields[0],dataFields[1],Float.parseFloat(dataFields[2]),Float.parseFloat(dataFields[3])));
                        }
                    }
                }
                else {
                    courseNames.add(dataFields[0]);
                    Course newCourse = new Course(dataFields[0]);
                    newCourse.getMarks().add(new CourseItem(dataFields[0],dataFields[1],Float.parseFloat(dataFields[2]),Float.parseFloat(dataFields[3])));
                    courses.add(newCourse);
                }
            }
            for(Course c: courses) {
                Tab tab = new Tab(c.getName());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gradeCalculator.fxml"));
                //loader.load();
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

        } catch (IOException e){ e.printStackTrace(); }

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
            tabPane.getTabs().add(tab);
            tab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/fxml/gradeCalculator.fxml")));
        }
    }
}