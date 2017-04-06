package project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by 100525709 on 3/14/2017.
 */
public class TodoListTabController {

    private final DataFormat buttonFormat = new DataFormat(" ");
    @FXML private Button draggingButton;
    @FXML private FlowPane mPane, tPane, wPane, thPane, fPane, satPane, sunPane;
    public File todoListFile = new File("src/main/resources/data/todoListData.csv");
    public String csvFile = todoListFile.getAbsolutePath();

    public void initialize(){

        // Initialize all panes to handle dragging
        addDropHandling(mPane);
        addDropHandling(tPane);
        addDropHandling(wPane);
        addDropHandling(thPane);
        addDropHandling(fPane);
        addDropHandling(satPane);
        addDropHandling(sunPane);

        //Pre-loading previous session tasks saved in CSV
        try {
            // read input from file
            Scanner inputStream = new Scanner(todoListFile);
            inputStream.next(); // Skip header line

            // hashNext() loops line-by-line
            while(inputStream.hasNext()) {
                //read single line, put in string
                String data = inputStream.next();
                String col[] = data.split(",");
                String colour = col[1].substring(1) + "," + col[2] + "," + col[3].substring(0,col[3].length()-1);
                String paneID = col[4];
                System.out.println(paneID);
                if (paneID.equals("Monday")){ mPane.getChildren().add(initButton(col[0],colour,col[4])); }
                if (paneID.equals("Tuesday")){ tPane.getChildren().add(initButton(col[0],colour,col[4]));}
                if (paneID.equals("Wednesday")){ wPane.getChildren().add(initButton(col[0],colour,col[4]));}
                if (paneID.equals("Thursday")){ thPane.getChildren().add(initButton(col[0],colour,col[4]));}
                if (paneID.equals("Friday")){ fPane.getChildren().add(initButton(col[0],colour,col[4]));}
                if (paneID.equals("Saturday")){ satPane.getChildren().add(initButton(col[0],colour,col[4]));}
                if (paneID.equals("Sunday")){ sunPane.getChildren().add(initButton(col[0],colour,col[4]));}
                //System.out.println(columns.length);
                //System.out.println(columns[1].substring(1) + "," + columns[2] + "," + columns[3].substring(0,columns[3].length()-1) );
                //System.out.println(data + "***");
            }

        }catch (FileNotFoundException e){ e.printStackTrace();}

    }

    private String toRgbString(Color c) {
        return "rgb("
                + to255Int(c.getRed())
                + "," + to255Int(c.getGreen())
                + "," + to255Int(c.getBlue())
                + ")";
    }

    private int to255Int(double d) {
        return (int) (d * 255);
    }

    private void addDialog(Pane pane, String day) {
        Dialog<Pair<String, String>> addDialog = new Dialog<>();
        addDialog.setTitle("Add Task");
        addDialog.setHeaderText("Enter your task");
        ButtonType logButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        addDialog.getDialogPane().getButtonTypes().addAll(logButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));

        TextField task = new TextField();
        task.setPromptText("task");
        ColorPicker cp = new ColorPicker();

        grid.add(new Label("Task:"), 0, 0);
        grid.add(task,1,0);
        grid.add(new Label("Colour:"), 0,1);
        grid.add(cp,1,1);
        addDialog.getDialogPane().setContent(grid);

        Platform.runLater(()->task.requestFocus());
        addDialog.setResultConverter(dialogButton -> {
            if (dialogButton == logButton){
                return new Pair<>(task.getText(), cp.getValue().toString());
            }
            return null;
        });

        Optional<Pair<String,String>> result = addDialog.showAndWait();
        result.ifPresent(taskName -> {
            String colour = toRgbString(cp.getValue());
            pane.getChildren().add(createButton(task.getText(),colour, day));
        });
    }

    @FXML
    void addToMonday(ActionEvent event) {
        String day = "Monday";
        addDialog(mPane, day);
    }
    @FXML
    void addToTuesday(ActionEvent event) {
        String day = "Tuesday";
        addDialog(tPane, day);
    }
    @FXML
    void addToWednesday(ActionEvent event) {
        String day = "Wednesday";
        addDialog(wPane, day);
    }
    @FXML
    void addToThursday(ActionEvent event) {
        String day = "Thursday";
        addDialog(thPane, day);
    }
    @FXML
    void addToFriday(ActionEvent event){
        String day = "Friday";
        addDialog(fPane, day);
    }
    @FXML
    void addToSaturday(ActionEvent event) {
        String day = "Saturday";
        addDialog(satPane, day);
    }
    @FXML
    void addToSunday(ActionEvent event) {
        String day = "Sunday";
        addDialog(sunPane, day);
    }

    private Button createButton(String text, String c, String day) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: "+ c +" ; -fx-text-fill: black;" + "-fx-font-weight: bold;");
        button.setMinWidth(120);
        button.setOnDragDetected(e -> {
            Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(button.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(buttonFormat, "button");
            db.setContent(cc);
            draggingButton = button ;
        });
        button.setOnDragDone(e -> draggingButton = null);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY){
                    String paneID = button.getParent().getId();
                    if (paneID == mPane.getId()){ mPane.getChildren().remove(button);}
                    if (paneID == tPane.getId()){ tPane.getChildren().remove(button);}
                    if (paneID == wPane.getId()){ wPane.getChildren().remove(button);}
                    if (paneID == thPane.getId()){ thPane.getChildren().remove(button);}
                    if (paneID == fPane.getId()){ fPane.getChildren().remove(button);}
                    if (paneID == satPane.getId()){ satPane.getChildren().remove(button);}
                    if (paneID == sunPane.getId()){ sunPane.getChildren().remove(button);}
                    //TODO: updateCSV
                }
            }
        });
        appendToCSV(text, c, day);
        return button ;
    }

    private Button initButton(String text, String c, String day) {
        System.out.println(text + c + day);
        Button button = new Button(text);
        button.setStyle("-fx-background-color: "+ c +" ; -fx-text-fill: black;" + "-fx-font-weight: bold;");
        button.setMinWidth(120);
        button.setOnDragDetected(e -> {
            Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(button.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(buttonFormat, "button");
            db.setContent(cc);
            draggingButton = button ;
        });
        button.setOnDragDone(e -> draggingButton = null);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY){
                    String paneID = button.getParent().getId();
                    if (paneID == mPane.getId()){ mPane.getChildren().remove(button);}
                    if (paneID == tPane.getId()){ tPane.getChildren().remove(button);}
                    if (paneID == wPane.getId()){ wPane.getChildren().remove(button);}
                    if (paneID == thPane.getId()){ thPane.getChildren().remove(button);}
                    if (paneID == fPane.getId()){ fPane.getChildren().remove(button);}
                    if (paneID == satPane.getId()){ satPane.getChildren().remove(button);}
                    if (paneID == sunPane.getId()){ sunPane.getChildren().remove(button);}
                    //TODO: updateCSV
                }
            }
        });
        return button ;
    }

    private void appendToCSV(String text, String c, String p) {
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        c = "\"" + c + "\"";
        try{
            FileWriter fileWriter = new FileWriter(csvFile, true);
            fileWriter.append(text + COMMA_DELIMITER + c + COMMA_DELIMITER + p);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.flush();
            fileWriter.close();
        }catch ( IOException e ){
            e.printStackTrace();
        }



    }

    private void addDropHandling(Pane pane) {
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat)
                    && draggingButton != null
                    && draggingButton.getParent() != pane) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat)) {
                ((Pane)draggingButton.getParent()).getChildren().remove(draggingButton);
                pane.getChildren().add(draggingButton);
                e.setDropCompleted(true);
            }
        });
    }
}
