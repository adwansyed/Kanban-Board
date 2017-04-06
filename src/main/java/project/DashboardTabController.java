package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

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

    public void initialize(){
        readTask();
        initializePieChart();
    }

    private void initializePieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(Map.Entry<String, Integer> entry: taskCount.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(),entry.getValue()));
        }
        taskPieChart.setData(pieChartData);
    }

    private void readTask(){
//        URL url = getClass().getResource
//                ("src/main/resources/data/todoListData.csv");
        File todoTaskFile = new File("src/main/resources/data/todoListData.csv");
        try {
            FileReader reader = new FileReader(todoTaskFile);
            BufferedReader in = new BufferedReader(reader);

            int columnIndex = 0;
            String line;

            while ((line = in.readLine()) != null) {
                if (line.trim().length() != 0) {
                    String[] dataFields = line.split(",");
                    if (!taskCount.containsKey(dataFields[columnIndex])) {
                        taskCount.put(dataFields[columnIndex], 1);
                    } else {
                        int oldCount = taskCount.get(dataFields[columnIndex]);
                        taskCount.put(dataFields[columnIndex],oldCount + 1);
                    }
                }

            }

/*            for (Map.Entry<String,Integer> entry: taskCount.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }*/

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


