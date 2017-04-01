package project;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.awt.*;
import javafx.scene.text.Font;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;


public class CalendarTabController {

    /*
    Need to figure out how to get Cell String Values
    Figure out how to save them in a specific Format
    need to get Data from .csv file and add to calendar
*/
    @FXML
    private TableColumn<Node, String> tuesColumn;

    @FXML
    private GridPane editAreaTop;

    @FXML
    private Label dateLabel;

    @FXML
    private TableColumn<Node, String> monColumn;

    @FXML
    private TableColumn<Node, String> wedColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Node, String> sunColumn;

    @FXML
    private TableColumn<Node, String> satColumn;

    @FXML
    private TableColumn<Node, String> thursColumn;

    @FXML
    private TableView<Node> table;

    @FXML
    private TableColumn<Node, String> friColumn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button saveBtn;


    private DateFormat dateFormat;
    private Date date;
    private LocalDate date2;
    private YearMonth yearMonth;
    private ObservableList<Node> node;



    //Sets the calendar to empty cells and for the current date
    public void initialize() {

        node = FXCollections.observableArrayList();

        TextAreaTableCell tatc = new TextAreaTableCell();


        date = new Date();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        datePicker.setPromptText(dateFormat.format(date));
        System.out.println(dateFormat.format(date).toString());

        dateLabel.setText("Date: " + dateFormat.format(date).toString());


        table.setFixedCellSize(100);
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);




        sunColumn.setCellValueFactory(new PropertyValueFactory<Node,String>("first"));
        //   sunColumn.setCellFactory(TextAreaTableCell.forTableColumn());
        sunColumn.setCellFactory(TextAreaTableCell.forTableColumn());
       /* sunColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Node, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Node, String> t) {
                        ((Node) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirst(t.getNewValue());
                    }
                }
        );*/




        monColumn.setCellValueFactory(new PropertyValueFactory< Node, String>("second"));
        monColumn.setCellFactory(TextAreaTableCell.forTableColumn());


        tuesColumn.setCellValueFactory(new PropertyValueFactory<Node,String>("third"));
        tuesColumn.setCellFactory(TextAreaTableCell.forTableColumn());

        wedColumn.setCellValueFactory(new PropertyValueFactory<Node,String>("fourth"));
        wedColumn.setCellFactory(TextAreaTableCell.forTableColumn());

        thursColumn.setCellValueFactory(new PropertyValueFactory<Node,String>("fifth"));
        thursColumn.setCellFactory(TextAreaTableCell.forTableColumn());

        friColumn.setCellValueFactory(new PropertyValueFactory<Node,String>("sixth"));
        friColumn.setCellFactory(TextAreaTableCell.forTableColumn());

        satColumn.setCellValueFactory(new PropertyValueFactory<Node,String>("seventh"));
        satColumn.setCellFactory(TextAreaTableCell.forTableColumn());

        table.setItems(node);


    }
    //Updates the numbers based on the selected date
    //Clears all date within the cells from the previous calendar state
    @FXML
    void updater(ActionEvent event) {


        int[] nums = new int[7];
        int prevDays;
        for ( int i = 0; i<table.getItems().size(); i++) {
            table.getItems().clear();
        }

        date2 = datePicker.getValue();
        int days = checkDays(date2.getMonthValue(), date2.getYear());
        if(date2.getMonthValue() == 1) {
            prevDays = checkDays(12, date2.getYear()-1);
        }
        else{
            prevDays = checkDays((date2.getMonthValue()-1), date2.getYear());
        }
        int month = date2.getMonthValue();
        month = month-1;
        int loopCheckpoint = -1;




        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.DATE,1);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.YEAR,date2.getYear());


        SimpleDateFormat sdf= new SimpleDateFormat("EEEE");//formats the day into a string for the specific day
        String start = sdf.format(cal.getTime());

//formats calender numbers based on starting day
        if(start.equals("Sunday")){
            int counter2 = 1;
            int counter = 1;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        nums[i] = counter;
                        counter++;
                    }
                    else{
                        loopCheckpoint = i;
                        break;

                    }
                }
                if(loopCheckpoint != -1) {

                    for (int i = loopCheckpoint; i < 7;i++){
                        nums[i] = counter2;
                        counter2++;

                    }

                }
                node.add(new Node(Integer.toString(nums[0]), Integer.toString(nums[1]),
                        Integer.toString(nums[2]), Integer.toString(nums[3]), Integer.toString(nums[4]),
                        Integer.toString(nums[5]), Integer.toString(nums[6])));
            }
            table.setItems(node);

        }
        else if(start.equals("Monday")){
            int counter = 1;
            int counter2 = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 1) {
                            nums[i] = backCounter;//chenge this code according to the starting day
                            dayChecks++;
                        }
                        else{
                            nums[i] = counter;
                            counter++;
                        }

                    }
                    else{
                        loopCheckpoint = i;
                        break;

                    }
                }
                if(loopCheckpoint != -1) {

                    for (int i = loopCheckpoint; i < 7;i++){
                        nums[i] = counter2;
                        counter2++;

                    }

                }
                node.add(new Node(Integer.toString(nums[0]), Integer.toString(nums[1]),
                        Integer.toString(nums[2]), Integer.toString(nums[3]), Integer.toString(nums[4]),
                        Integer.toString(nums[5]), Integer.toString(nums[6])));
            }
            table.setItems(node);


        }
        else if(start.equals("Tuesday")){
            int counter = 1;
            int counter2 = 1;
            int dayChecks = 0;
            int backCounter = prevDays-1;//change this according to starting day
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 2) {//change this code according to the starting day
                            nums[i] = backCounter;
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = counter;
                            counter++;
                        }

                    }
                    else{
                        loopCheckpoint = i;
                        break;

                    }
                }
                if(loopCheckpoint != -1) {

                    for (int i = loopCheckpoint; i < 7;i++){
                        nums[i] = counter2;
                        counter2++;

                    }

                }
                node.add(new Node(Integer.toString(nums[0]), Integer.toString(nums[1]),
                        Integer.toString(nums[2]), Integer.toString(nums[3]), Integer.toString(nums[4]),
                        Integer.toString(nums[5]), Integer.toString(nums[6])));
            }
            table.setItems(node);


        }
        else if(start.equals("Wednesday")){
            int counter = 1;
            int counter2 = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays-2;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 3) {//change this code according to the starting day
                            nums[i] = backCounter;
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = counter;
                            counter++;
                        }

                    }
                    else{
                        loopCheckpoint = i;
                        break;

                    }
                }
                if(loopCheckpoint != -1) {
                    for (int i = loopCheckpoint; i < 7;i++){
                        nums[i] = counter2;
                        counter2++;

                    }

                }
                node.add(new Node(Integer.toString(nums[0]), Integer.toString(nums[1]),
                        Integer.toString(nums[2]), Integer.toString(nums[3]), Integer.toString(nums[4]),
                        Integer.toString(nums[5]), Integer.toString(nums[6])));
            }
            table.setItems(node);


        }
        else if(start.equals("Thursday")){
            int counter = 1;
            int counter2 = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays-3;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 4) {//change this code according to the starting day
                            nums[i] = backCounter;
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = counter;
                            counter++;
                        }

                    }
                    else{
                        loopCheckpoint = i;
                        break;

                    }
                }
                if(loopCheckpoint != -1) {
                    for (int i = loopCheckpoint; i < 7;i++){
                        nums[i] = counter2;
                        counter2++;

                    }

                }
                node.add(new Node(Integer.toString(nums[0]), Integer.toString(nums[1]),
                        Integer.toString(nums[2]), Integer.toString(nums[3]), Integer.toString(nums[4]),
                        Integer.toString(nums[5]), Integer.toString(nums[6])));
            }
            table.setItems(node);

        }
        else if(start.equals("Friday")){
            int counter = 1;
            int counter2 = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays-4;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 5) {//change this code according to the starting day
                            nums[i] = backCounter;
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = counter;
                            counter++;
                        }

                    }
                    else{
                        loopCheckpoint = i;
                        break;

                    }
                }
                if(loopCheckpoint != -1) {
                    for (int i = loopCheckpoint; i < 7;i++){
                        nums[i] = counter2;
                        counter2++;

                    }

                }
                node.add(new Node(Integer.toString(nums[0]), Integer.toString(nums[1]),
                        Integer.toString(nums[2]), Integer.toString(nums[3]), Integer.toString(nums[4]),
                        Integer.toString(nums[5]), Integer.toString(nums[6])));
            }
            table.setItems(node);

        }
        else if(start.equals("Saturday")){
            int counter = 1;
            int counter2 = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays-5;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+1) {
                        if(dayChecks < 6) {//change this code according to the starting day
                            nums[i] = backCounter;
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = counter;
                            counter++;
                        }

                    }
                    else{
                        loopCheckpoint = i;
                        break;

                    }
                }
                if(loopCheckpoint != -1) {
                    for (int i = loopCheckpoint; i < 7;i++){
                        nums[i] = counter2;
                        counter2++;

                    }

                }
                node.add(new Node(Integer.toString(nums[0]), Integer.toString(nums[1]),
                        Integer.toString(nums[2]), Integer.toString(nums[3]), Integer.toString(nums[4]),
                        Integer.toString(nums[5]), Integer.toString(nums[6])));
            }
            table.setItems(node);

        }





    }

    //Saves the date from the calendar to a file
    @FXML
    void saveMonth(ActionEvent event) {
        int counter = 0;
        TableColumn<Node, String> finalSunColumn = sunColumn;
        TableColumn<Node, String> finalMonColumn = monColumn;
        TableColumn<Node, String> finalTuesColumn = tuesColumn;
        TableColumn<Node, String> finalWedColumn = wedColumn;
        TableColumn<Node, String> finalThursColumn = thursColumn;
        TableColumn<Node, String> finalFriColumn = friColumn;
        TableColumn<Node, String> finalSatColumn = satColumn;
        ;

        String[] sunTemp = new String[table.getItems().size()];
        String[] monTemp = new String[table.getItems().size()];
        String[] tuesTemp = new String[table.getItems().size()];
        String[] wedTemp =new String[table.getItems().size()];
        String[] thursTemp =new String[table.getItems().size()];
        String[] friTemp =new String[table.getItems().size()];
        String[] satTemp =new String[table.getItems().size()];
        for(Node item : table.getItems()){
            sunTemp[counter] = finalSunColumn.getCellObservableValue(item).getValue();
            monTemp[counter] = finalMonColumn.getCellObservableValue(item).getValue();
            tuesTemp[counter] = finalTuesColumn.getCellObservableValue(item).getValue();
            wedTemp[counter] = finalWedColumn.getCellObservableValue(item).getValue();
            thursTemp[counter] = finalThursColumn.getCellObservableValue(item).getValue();
            friTemp[counter] = finalFriColumn.getCellObservableValue(item).getValue();
            satTemp[counter] = finalSatColumn.getCellObservableValue(item).getValue();
            System.out.println(sunTemp[counter]+" "+monTemp[counter]+" "+tuesTemp[counter]+" "+
                    wedTemp[counter]+" "+thursTemp[counter]+" "+friTemp[counter]+" "+satTemp[counter]);
            counter++;
        }

    }
    @FXML
    public void clickItem(MouseEvent event)
    {
        if (event.getClickCount() == 2) //Checking double click
        {
            System.out.println(table.getSelectionModel().getSelectedItem().getFirst());
            System.out.println(table.getSelectionModel().getSelectedItem().getSecond());
            System.out.println(table.getSelectionModel().getSelectedItem().getThird());
            System.out.println(table.getSelectionModel().getSelectedItem().getFourth());
            System.out.println(table.getSelectionModel().getSelectedItem().getFifth());
            System.out.println(table.getSelectionModel().getSelectedItem().getSixth());
            System.out.println(table.getSelectionModel().getSelectedItem().getSeventh());
        }
    }
    //calculates the number of days in the specified month and year
    public int checkDays(int month, int year){


        int daysInMonth;
        int isLeapYear;

        if (year%4 == 0 && year%100 == 0 && year%400 == 0){
            isLeapYear = 1;
        }
        else{
            isLeapYear = 0;

        }

        if (month == 2) {
            daysInMonth = 28 + isLeapYear;
        } else {
            daysInMonth = 31 - (month - 1) % 7 % 2;
        }
        return daysInMonth;
    }



}
