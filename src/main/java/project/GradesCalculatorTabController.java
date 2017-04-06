package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.text.DecimalFormat;

public class GradesCalculatorTabController {
    public String roundMark(float mark) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(mark*100);
    }

    private float percentOfCourse = 0;
    private float currentCourseTotal = 0;
    private File file = new File("src/main/resources/data/Database.csv");

    @FXML
    private Label currentCourseTotalLabel;

    @FXML
    private Label currentCourseMarkLabel;

    @FXML
    private TableView<CourseItem> tableView = new TableView<>();

    @FXML
    private TableColumn<CourseItem, String> courseItem;

    @FXML
    private TableColumn<CourseItem, Float> worth;

    @FXML
    private TableColumn<CourseItem, Float> yourMark;

    @FXML
    private TextField courseItemField;

    @FXML
    private TextField worthField;

    @FXML
    private TextField yourMarkField;

    @FXML
    private TextField courseNameField;

    @FXML
    private Label finalExamWorth;

    @FXML
    private Label fifty;

    @FXML
    private Label sixty;

    @FXML
    private Label seventy;

    @FXML
    private Label eighty;

    @FXML
    private Label ninety;

    @FXML
    private Label hundred;

    public float getPercentOfCourse() {
        return percentOfCourse;
    }

    public void setPercentOfCourse(float percentOfCourse) {
        this.percentOfCourse = percentOfCourse;
    }

    public float getCurrentCourseTotal() {
        return currentCourseTotal;
    }

    public void setCurrentCourseTotal(float currentCourseTotal) {
        this.currentCourseTotal = currentCourseTotal;
    }

    public Label getCurrentCourseTotalLabel() {
        return currentCourseTotalLabel;
    }

    public void setCurrentCourseTotalLabel(Label currentCourseTotalLabel) {
        this.currentCourseTotalLabel = currentCourseTotalLabel;
    }

    public Label getCurrentCourseMarkLabel() {
        return currentCourseMarkLabel;
    }

    public void setCurrentCourseMarkLabel(Label currentCourseMarkLabel) {
        this.currentCourseMarkLabel = currentCourseMarkLabel;
    }

    public TableView<CourseItem> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<CourseItem> tableView) {
        this.tableView = tableView;
    }

    public TableColumn<CourseItem, String> getCourseItem() {
        return courseItem;
    }

    public void setCourseItem(TableColumn<CourseItem, String> courseItem) {
        this.courseItem = courseItem;
    }

    public TableColumn<CourseItem, Float> getWorth() {
        return worth;
    }

    public void setWorth(TableColumn<CourseItem, Float> worth) {
        this.worth = worth;
    }

    public TableColumn<CourseItem, Float> getYourMark() {
        return yourMark;
    }

    public void setYourMark(TableColumn<CourseItem, Float> yourMark) {
        this.yourMark = yourMark;
    }

    public TextField getCourseItemField() {
        return courseItemField;
    }

    public void setCourseItemField(TextField courseItemField) {
        this.courseItemField = courseItemField;
    }

    public TextField getWorthField() {
        return worthField;
    }

    public void setWorthField(TextField worthField) {
        this.worthField = worthField;
    }

    public TextField getYourMarkField() {
        return yourMarkField;
    }

    public void setYourMarkField(TextField yourMarkField) {
        this.yourMarkField = yourMarkField;
    }

    public TextField getCourseNameField() {
        return courseNameField;
    }

    public void setCourseNameField(TextField courseNameField) {
        this.courseNameField = courseNameField;
    }

    public Label getFinalExamWorth() {
        return finalExamWorth;
    }

    public void setFinalExamWorth(Label finalExamWorth) {
        this.finalExamWorth = finalExamWorth;
    }

    public Label getFifty() {
        return fifty;
    }

    public void setFifty(Label fifty) {
        this.fifty = fifty;
    }

    public Label getSixty() {
        return sixty;
    }

    public void setSixty(Label sixty) {
        this.sixty = sixty;
    }

    public Label getSeventy() {
        return seventy;
    }

    public void setSeventy(Label seventy) {
        this.seventy = seventy;
    }

    public Label getEighty() {
        return eighty;
    }

    public void setEighty(Label eighty) {
        this.eighty = eighty;
    }

    public Label getNinety() {
        return ninety;
    }

    public void setNinety(Label ninety) {
        this.ninety = ninety;
    }

    public Label getHundred() {
        return hundred;
    }

    public void setHundred(Label hundred) {
        this.hundred = hundred;
    }

    public void initialize() throws IOException {
    }

    public void updateLables(CourseItem item) {
        //Updating Labels
        currentCourseTotal += item.getWorth();
        currentCourseTotalLabel.setText(Float.toString(currentCourseTotal)+"%");
        percentOfCourse += item.getWorth()*item.getYourMark()*0.01;
        currentCourseMarkLabel.setText(roundMark(percentOfCourse/currentCourseTotal)+"%");
        finalExamWorth.setText(Float.toString((100-currentCourseTotal)));


        fifty.setText(roundMark((50-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        sixty.setText(roundMark((60-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        seventy.setText(roundMark((70-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        eighty.setText(roundMark((80-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        ninety.setText(roundMark((90-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        hundred.setText(roundMark((100-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
    }

    @FXML
    public void addItem(ActionEvent actionEvent) {
        if ((courseItemField.getText() != null) && (worthField.getText() != null) && (yourMarkField.getText() != null)) {
            String courseNameInput = courseNameField.getText().trim();
            String courseItemInput = courseItemField.getText();
            Float worthInput = Float.parseFloat(worthField.getText());
            Float yourMarkInput = Float.parseFloat(yourMarkField.getText());
            CourseItem newCourseItem = new CourseItem(courseNameInput, courseItemInput, worthInput, yourMarkInput);

            //update labels
            updateLables(newCourseItem);

            courseItem.setCellValueFactory(( new PropertyValueFactory<>("courseItem")));
            worth.setCellValueFactory(( new PropertyValueFactory<>("worth")));
            yourMark.setCellValueFactory(( new PropertyValueFactory<>("yourMark")));

            //Add item to Tableview and clear input fields
            tableView.getItems().add(newCourseItem);
            courseItemField.clear();
            worthField.clear();
            yourMarkField.clear();


            try {
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                out.println(courseNameInput + "," +courseItemInput + "," + worthInput + "," + yourMarkInput);
                out.close();
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteItem(ActionEvent actionEvent) throws IOException {
        CourseItem selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);

        currentCourseTotal -= selectedItem.getWorth();
        currentCourseTotalLabel.setText(Float.toString(currentCourseTotal)+"%");

        percentOfCourse -= selectedItem.getYourMark()*selectedItem.getWorth()*0.01;
        //To prevent a NaN sign on the application
        if(currentCourseTotal != 0) {
            currentCourseMarkLabel.setText(roundMark(percentOfCourse/currentCourseTotal)+"%");
        }
        else {
            currentCourseMarkLabel.setText("0.0%");
        }


        Float newFinalExamWorth = Float.parseFloat(finalExamWorth.getText()) + selectedItem.getWorth();
        finalExamWorth.setText(Float.toString(newFinalExamWorth));


        //update labels
        fifty.setText(roundMark((50-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        sixty.setText(roundMark((60-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        seventy.setText(roundMark((70-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        eighty.setText(roundMark((80-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        ninety.setText(roundMark((90-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));
        hundred.setText(roundMark((100-percentOfCourse)/Float.parseFloat(finalExamWorth.getText())));

        //Temp file is created. Every line except the file that we want to delete is added to tempfile.
        //TempFile is then converted to Database.csv
        //File inputFile = new File("data/Database.csv");
        File tempFile = new File("TempCsv.csv");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = selectedItem.getCourseName()+","+selectedItem.getCourseItem()+","+selectedItem.getWorth()+","+selectedItem.getYourMark();
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(file);
    }
}
