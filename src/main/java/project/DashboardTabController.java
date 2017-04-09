package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DashboardTabController {

    @FXML
    private PieChart taskPieChart;
    private HashMap<String, Integer> taskCount = new HashMap<>();

    @FXML
    private Button taskPieButton;

    @FXML
    private Button buttonClear;

    @FXML
    void handleButtonClearAction(ActionEvent event) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        taskPieChart.setTitle("");
        taskPieChart.setData(pieChartData);

    }

    @FXML
    void handleButtonTaskAction(ActionEvent event) {
        initialize();
    }

    public void initialize(){
        //read thru the todoList csv file.
        readTask();
        //display the pie chart
        initializePieChart();

    }

    private void initializePieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(Map.Entry<String, Integer> entry: taskCount.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(),entry.getValue()));
        }
        taskPieChart.setData(pieChartData);

    }


    public void readTask(){

        File todoTaskFile = new File("src/main/resources/data/todoListData.csv");
        try {
            FileReader reader = new FileReader(todoTaskFile);
            BufferedReader in = new BufferedReader(reader);

            int columnIndex = 0;
            String line;
            int count = 0;
            while ((line = in.readLine()) != null) {
                if (count != 0) {//count == 0 means the first line, we skip the first line by skipping the if statement next
                    if (line.trim().length() != 0) {
                        String[] dataFields = line.split(",");
                        if (!taskCount.containsKey(dataFields[columnIndex])) {
                            taskCount.put(dataFields[columnIndex], 1);
                        } else {
                            int oldCount = taskCount.get(dataFields[columnIndex]);
                            taskCount.put(dataFields[columnIndex], oldCount + 1);
                        }
                    }

                }
                count++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String toRgbString(Color c) {
        return "rgb(" + to255Int(c.getRed()) + "," + to255Int(c.getGreen()) + "," + to255Int(c.getBlue()) + ")";

    }

    private int to255Int(double d){
        return (int) (d*255);
    }
}



