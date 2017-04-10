package project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;


public class CalendarTabController {

    /*
    Need to figure out how to get Cell String Values
    Figure out how to save them in a specific Format
    need to get Data from .csv file and add to calendar
*/
    @FXML
    private TableColumn<Observe2, String> tuesColumn;

    @FXML
    private GridPane editAreaTop;

    @FXML
    private Label dateLabel;

    @FXML
    private TextArea helpLbl;

    @FXML
    private TableColumn<Observe2, String> monColumn;

    @FXML
    private TableColumn<Observe2, String> wedColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Observe2, String> sunColumn;

    @FXML
    private TableColumn<Observe2, String> satColumn;

    @FXML
    private TableColumn<Observe2, String> thursColumn;

    @FXML
    private TableView<Observe2> table;



    @FXML
    private TableColumn<Observe2, String> friColumn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button saveBtn;

    private DateFormat dateFormat;
    private Date date;
    private LocalDate date2;
    private YearMonth yearMonth;
    private static ObservableList<Observe2> node;
    private TextAreaTableCell monArea;
    private TextAreaTableCell tueArea;
    private TextAreaTableCell wedArea;
    private TextAreaTableCell thurArea;
    private TextAreaTableCell friArea;
    private TextAreaTableCell satArea;
    private TextAreaTableCell sunArea;
    public static Observe ob;
    private String dateStr;

    //Sets the calendar to empty cells and for the current date
    public void initialize() {
        node = FXCollections.observableArrayList();

        monArea = new TextAreaTableCell();
        tueArea = new TextAreaTableCell();
        wedArea = new TextAreaTableCell();
        thurArea = new TextAreaTableCell();
        friArea = new TextAreaTableCell();
        satArea = new TextAreaTableCell();
        sunArea = new TextAreaTableCell();
        helpLbl.setText("To open a new month:" + "\tTo edit cell:\n" +
                "Step 1: pick a date " + "\t        Step 1: double click cell \n" +
                "Step 2: click new" + "\t        Step 2: edit cell \n" +
                "                     \t                Step 3: press SHIFT + ENTER to lock changes");
        helpLbl.setVisible(true);
        helpLbl.setEditable(false);


        date = new Date();
        LocalDate dates = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        datePicker.setValue(dates);

        dateLabel.setText("Date: " + dateFormat.format(date).toString());


        table.setFixedCellSize(100);
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getSelectionModel().getSelectedCells();






        sunColumn.setCellValueFactory(new PropertyValueFactory<Observe2,String>("first"));
        sunColumn.setCellFactory(sunArea.forTableColumn());



        monColumn.setCellValueFactory(new PropertyValueFactory< Observe2, String>("second"));
        monColumn.setCellFactory(monArea.forTableColumn());


        tuesColumn.setCellValueFactory(new PropertyValueFactory<Observe2,String>("third"));
        tuesColumn.setCellFactory(tueArea.forTableColumn());

        wedColumn.setCellValueFactory(new PropertyValueFactory<Observe2,String>("fourth"));
        wedColumn.setCellFactory(wedArea.forTableColumn());

        thursColumn.setCellValueFactory(new PropertyValueFactory<Observe2,String>("fifth"));
        thursColumn.setCellFactory(thurArea.forTableColumn());

        friColumn.setCellValueFactory(new PropertyValueFactory<Observe2,String>("sixth"));
        friColumn.setCellFactory(friArea.forTableColumn());

        satColumn.setCellValueFactory(new PropertyValueFactory<Observe2,String>("seventh"));
        satColumn.setCellFactory(satArea.forTableColumn());

        table.setItems(node);


    }
    //Updates the numbers based on the selected date
    //Clears all date within the cells from the previous calendar state
    @FXML
    void updater(ActionEvent event) {

        String[] nums = new String[7];
        int prevDays;
        for ( int i = 0; i<table.getItems().size(); i++) {
            table.getItems().clear();
        }
        ob = new Observe();
        LocalDate dateName = datePicker.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date tempDate = java.sql.Date.valueOf(dateName);
        dateStr = sdf.format(tempDate).toString();


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

        sdf= new SimpleDateFormat("EEEE");//formats the day into a string for the specific day
        String start = sdf.format(cal.getTime());

//formats calender numbers based on starting day
        if(start.equals("Sunday")){
            int counter2 = 1;
            int counter = 1;
            int week = 1;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        nums[i] = Integer.toString(counter);
                        counter++;
                    }
                    else{
                        loopCheckpoint = i;
                        break;

                    }
                }
                if(loopCheckpoint != -1) {

                    for (int i = loopCheckpoint; i < 7;i++){
                        nums[i] = "+"+Integer.toString(counter2);
                        counter2++;
                    }
                }
                node.add(new Observe2(nums[0] + " .", nums[1] + " .",
                        nums[2] + " .", nums[3] + " .", nums[4] + " .",
                        nums[5] + " .", nums[6] + " .", week));
                week++;
            }
            table.setItems(node);
        }
        else if(start.equals("Monday")){
            int counter = 1;
            int counter2 = 1;
            int week = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 1) {
                            nums[i] = "-"+Integer.toString(backCounter);//chenge this code according to the starting day
                            dayChecks++;
                        }
                        else{
                            nums[i] = Integer.toString(counter);
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
                        nums[i] = "+"+Integer.toString(counter2);
                        counter2++;

                    }

                }
                node.add(new Observe2(nums[0] + " .", nums[1] + " .",
                        nums[2] + " .", nums[3] + " .", nums[4] + " .",
                        nums[5] + " .", nums[6] + " .", week));
                week++;
            }
            table.setItems(node);
        }
        else if(start.equals("Tuesday")){
            int counter = 1;
            int counter2 = 1;
            int dayChecks = 0;
            int week = 1;
            int backCounter = prevDays-1;//change this according to starting day
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 2) {//change this code according to the starting day
                            nums[i] = "-"+Integer.toString(backCounter);
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = Integer.toString(counter);
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
                        nums[i] = "+"+Integer.toString(counter2);
                        counter2++;

                    }

                }
                node.add(new Observe2(nums[0] + " .", nums[1] + " .",
                        nums[2] + " .", nums[3]+ " .", nums[4] + " .",
                        nums[5] + " .", nums[6] + " .", week));
                week++;
            }
            table.setItems(node);
        }
        else if(start.equals("Wednesday")){
            int counter = 1;
            int counter2 = 1;
            int week = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays-2;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 3) {//change this code according to the starting day
                            nums[i] = "-"+Integer.toString(backCounter);
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = Integer.toString(counter);
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
                        nums[i] = "+"+Integer.toString(counter2);
                        counter2++;
                    }
                }
                node.add(new Observe2(nums[0] + " .", nums[1] + " .",
                        nums[2] + " .", nums[3] + " .", nums[4] + " .",
                        nums[5] + " .", nums[6] + " .", week));
                week++;
            }
            table.setItems(node);
        }
        else if(start.equals("Thursday")){
            int counter = 1;
            int counter2 = 1;
            int week = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays-3;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 4) {//change this code according to the starting day
                            nums[i] = "-"+Integer.toString(backCounter);
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = Integer.toString(counter);
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
                        nums[i] = "+"+Integer.toString(counter2);
                        counter2++;

                    }
                }
                node.add(new Observe2(nums[0] + " .", nums[1] + " .",
                        nums[2] + " .", nums[3] + " .", nums[4] + " .",
                        nums[5] + " .", nums[6] + " .", week));
                week++;
            }
            table.setItems(node);
        }
        else if(start.equals("Friday")){
            int counter = 1;
            int counter2 = 1;
            int week = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays-4;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+ 1) {
                        if(dayChecks < 5) {//change this code according to the starting day
                            nums[i] = "-"+Integer.toString(backCounter);
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = Integer.toString(counter);
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
                        nums[i] = "+"+Integer.toString(counter2);
                        counter2++;
                    }

                }
                node.add(new Observe2(nums[0] + " .", nums[1] + " .",
                        nums[2] + " .", nums[3] + " .", nums[4] + " .",
                        nums[5] + " .", nums[6] + " .", week));
                week++;
            }
            table.setItems(node);

        }
        else if(start.equals("Saturday")){
            int counter = 1;
            int counter2 = 1;
            int week = 1;
            int dayChecks = 0;//change this according to starting day
            int backCounter = prevDays-5;
            for (int x = 0; x < 6;x++){
                for (int i = 0; i < 7;i++){
                    if(counter < days+1) {
                        if(dayChecks < 6) {//change this code according to the starting day
                            nums[i] = "-"+Integer.toString(backCounter);
                            backCounter++;
                            dayChecks++;
                        }
                        else{
                            nums[i] = Integer.toString(counter);
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
                        nums[i] = "+"+Integer.toString(counter2);
                        counter2++;

                    }

                }
                node.add(new Observe2(nums[0] + " .", nums[1] + " .",
                        nums[2] + " .", nums[3] + " .", nums[4] + " .",
                        nums[5] + " .", nums[6] + " .", week));
                week++;
            }
            table.setItems(node);

        }
    }

    //Saves the date from the calendar to a file
    @FXML
    void saveMonth(ActionEvent event) {

        ObservableList<Node2> tempList = ob.getObserve();

        String NEW_LINE_SEPARATOR = "\n";
        int count = 0;
        File file = new File("src/main/resources/data/Calender/" + dateStr + ".csv");
        String fileName2 = file.getAbsolutePath();
        try{
            FileWriter fileWriter = new FileWriter(fileName2);
            for(Node2 f : tempList){
                if(count == 7){
                    fileWriter.append('/');
                    fileWriter.append(NEW_LINE_SEPARATOR);
                    count = 0;
                }
                fileWriter.append(","+f.getDate() + " ." + f.getText());
                fileWriter.append(NEW_LINE_SEPARATOR);
                count++;
            }
            fileWriter.append('/');
            fileWriter.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void openMonth(ActionEvent event) {
        ob = new Observe();
        for ( int i = 0; i<table.getItems().size(); i++) {
            table.getItems().clear();
        }
;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/data/Calender/"));
        File file = fileChooser.showOpenDialog(new Stage());
        dateStr = file.getName();
        String line;
        String[] tempCarry;
        int counter = -1;
        boolean strCheck;
        int week = 1;
        String[] col = new String[7];
        try {
            Scanner scanner = new Scanner(file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (scanner.hasNext()) {
                line = br.readLine();
                if(line.equals("/")){
                    node.add(new Observe2(col[0], col[1], col[2], col[3], col[4], col[5], col[6], week));
                    week++;
                    counter = -1;
                }
                else {
                    tempCarry = line.split("");
                    if(tempCarry[0].equals(",")){
                        strCheck = false;
                        counter++;
                        line =line.replaceFirst(",", line);
                        line = "";
                        for(int x = 1; x < tempCarry.length;x++){
                            line = line + tempCarry[x];
                        }
                    }
                    else{
                        strCheck = true;
                    }
                    if(strCheck== true){
                        col[counter] = col[counter] + "\n"+ line;
                    }
                    else{
                        col[counter] = line;
                    }
                }
                scanner.nextLine();

            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setItems(node);
    }
    //calculates the number of days in the specified month and year
    private int checkDays(int month, int year){
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

    public static void addList(String text, Cell cell){
        ObservableList<Node2> tempList = ob.getObserve();
        String[] strArray;
        String tempDate;
        String colName = " ";
        boolean check = true;
        strArray = text.split(" ");
        tempDate = strArray[0];

        if (strArray.length > 2){
            text = strArray[2];
            for (int counter = 3; counter < strArray.length;counter++){
                text = text + " " + strArray[counter];
            }
        }
        else{
            text = " ";
        }
        if(tempList.isEmpty()) {
            tempList.add(new Node2(tempDate, text));
            check = false;
        }
        else{
            outerloop:
            for (Node2 f : tempList) {
                if ((tempDate.equals(f.getDate()))) {

                    for(Observe2 t : node) {
                        if((tempDate+" .").equals(t.getFirst())) {
                            colName = "sunColumn";
                            if(colName.equals(cell.getId())){
                                f.setText(text);
                                check = false;
                                break outerloop;
                            }
                            else{
                                colName = " ";
                            }
                        }
                        else{
                            colName = " ";
                        }
                        if((tempDate+" .").equals(t.getSecond())) {
                            colName = "monColumn";
                            if(colName.equals(cell.getId())){
                                f.setText(text);
                                check = false;
                                break outerloop;
                            }
                            else{
                                colName = " ";
                            }
                        }
                        else{
                            colName = " ";
                        }
                        if((tempDate+" .").equals(t.getThird())) {
                            colName = "tuesColumn";
                            if(colName.equals(cell.getId())){
                                f.setText(text);
                                check = false;
                                break outerloop;
                            }
                            else{
                                colName = " ";
                            }
                        }
                        else{
                            colName = " ";
                        }
                        if((tempDate+" .").equals(t.getFourth())) {
                            colName = "wedColumn";
                            if(colName.equals(cell.getId())){
                                f.setText(text);
                                check = false;
                                break outerloop;
                            }
                            else{
                                colName = " ";
                            }
                        }
                        else{
                            colName = " ";
                        }
                        if((tempDate+" .").equals(t.getFifth())) {
                            colName = "thursColumn";
                            if(colName.equals(cell.getId())){
                                f.setText(text);
                                check = false;
                                break outerloop;
                            }
                            else{
                                colName = " ";
                            }
                        }
                        else{
                            colName = " ";
                        }
                        if((tempDate+" .").equals(t.getSixth())) {
                            colName = "friColumn";
                            if(colName.equals(cell.getId())){
                                f.setText(text);
                                check = false;
                                break outerloop;
                            }
                            else{
                                colName = " ";
                            }
                        }
                        else{
                            colName = " ";
                        }
                        if((tempDate+" .").equals(t.getSeventh())) {
                            colName = "satColumn";
                            if(colName.equals(cell.getId())){
                                f.setText(text);
                                check = false;
                                break outerloop;
                            }
                            else{
                                colName = " ";
                            }
                        }
                        else{
                            colName = " ";
                        }
                    }
                }
            }
        }
        if(check == true) {
            tempList.add(new Node2(tempDate, text));
        }
    }








}
