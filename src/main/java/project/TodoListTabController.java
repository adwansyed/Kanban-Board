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
import java.io.*;
import java.util.Optional;
import java.util.Scanner;

/**
 * Author(s): Adwan Syed, Andrew Selvarajah, Ahmed Naeem, Yi Guo
 */
public class TodoListTabController {

    private final DataFormat buttonFormat = new DataFormat(" ");
    @FXML private Button draggingButton;
    @FXML private FlowPane mPane, tPane, wPane, thPane, fPane, satPane, sunPane;
    public File todoListFile = new File("src/main/resources/data/todoListData.csv");
    public String csvFile = todoListFile.getAbsolutePath();

    // Fill daily tasks with previous saved session
    public void initialize(){

        // Initialize all panes to handle dragging
        enableDraggingUpdates(mPane);
        enableDraggingUpdates(tPane);
        enableDraggingUpdates(wPane);
        enableDraggingUpdates(thPane);
        enableDraggingUpdates(fPane);
        enableDraggingUpdates(satPane);
        enableDraggingUpdates(sunPane);

        //Pre-loading previous session tasks saved in CSV
        try {
            // read input from file
            Scanner inputStream = new Scanner(todoListFile);
            inputStream.next(); // Skip header line

            // hashNext() loops line-by-line
            String buffer = ""; // buffer to hold multiple word tasks
            while(inputStream.hasNext()) {
                //read single line, put in string
                String data = inputStream.next();
                String col[] = data.split("[ ,\n]");
                int wordCount = col.length - 4;
                if (wordCount < 0){
                    buffer += " " + col[0]; // update buffer based on word count
                    continue;               // handles "space" characters which act as newline when delimited
                }

                // parsing and adding strings for correct functionality
                // ex: rgb(xxx,xxx,xxx)
                String colour = col[wordCount].substring(1) + "," + col[wordCount + 1] + "," + col[wordCount + 2].substring(0,col[wordCount + 2].length() -1);
                String paneID = col[wordCount + 3];
                buffer += " " + col[0];
                String task = buffer;
                buffer = "";

                // select which pane to initialize buttons
                if (paneID.equals("Monday")){ mPane.getChildren().add(initButton(task,colour,col[wordCount + 3])); }
                if (paneID.equals("Tuesday")){ tPane.getChildren().add(initButton(task,colour,col[wordCount + 3]));}
                if (paneID.equals("Wednesday")){ wPane.getChildren().add(initButton(task,colour,col[wordCount + 3]));}
                if (paneID.equals("Thursday")){ thPane.getChildren().add(initButton(task,colour,col[wordCount + 3]));}
                if (paneID.equals("Friday")){ fPane.getChildren().add(initButton(task,colour,col[wordCount + 3]));}
                if (paneID.equals("Saturday")){ satPane.getChildren().add(initButton(task,colour,col[wordCount + 3]));}
                if (paneID.equals("Sunday")){ sunPane.getChildren().add(initButton(task,colour,col[wordCount + 3]));}
            }
        }catch (FileNotFoundException e){ e.printStackTrace();}

    }

    // create rgb string from Color input
    private String toRgbString(Color c) {
        return "rgb("
                + to255Int(c.getRed())
                + "," + to255Int(c.getGreen())
                + "," + to255Int(c.getBlue())
                + ")";
    }

    // convert to int
    private int to255Int(double d) {
        return (int) (d * 255);
    }

    // Calculates threshold index for darkness so we can choose font colour accordingly
    private int Brightness(String c){
        String rgbData[] = c.split(",");
        int r = Integer.parseInt(rgbData[0].substring(4, rgbData[0].length()));
        int g = Integer.parseInt(rgbData[1].substring(0, rgbData[1].length()));
        int b = Integer.parseInt(rgbData[2].substring(0, rgbData[2].length()-1));
        return (int)Math.sqrt( r * r * .241 + g * g * .691 + b * b * .068);
    }

    // dialog to add tasks
    private void addDialog(Pane pane, String day) {
        // create new dialog with labels and buttons
        Dialog<Pair<String, String>> addDialog = new Dialog<>();
        addDialog.setTitle("Add Task");
        addDialog.setHeaderText("Enter your task");
        ButtonType logButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        addDialog.getDialogPane().getButtonTypes().addAll(logButton, ButtonType.CANCEL);

        // set layout structure for dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));

        TextField task = new TextField();
        task.setPromptText("Max: 12 characters");
        ColorPicker cp = new ColorPicker();

        grid.add(new Label("Task:"), 0, 0);
        grid.add(task,1,0);
        grid.add(new Label("Colour:"), 0,1);
        grid.add(cp,1,1);
        addDialog.getDialogPane().setContent(grid);

        // highlight color picker as default field to start on
        Platform.runLater(()->cp.requestFocus());
        addDialog.setResultConverter(dialogButton -> {
            if (dialogButton == logButton){
                return new Pair<>(task.getText(), cp.getValue().toString());
            }
            return null;
        });

        // submit create button request based on task, colour and day
        Optional<Pair<String,String>> result = addDialog.showAndWait();
        result.ifPresent(taskName -> {
            String colour = toRgbString(cp.getValue());
            pane.getChildren().add(createButton(task.getText(),colour, day));
        });
    }

    // called from context menu
    void deleteDialog(Button button){
        // confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("You are about to delete this task.");
        alert.setContentText("Are you sure?");

        // delete button from correct pane once confirmed you want to delete
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String paneID = button.getParent().getId();
            deleteFromCSV(button);
            if (paneID == mPane.getId()){ mPane.getChildren().remove(button);}
            if (paneID == tPane.getId()){ tPane.getChildren().remove(button);}
            if (paneID == wPane.getId()){ wPane.getChildren().remove(button);}
            if (paneID == thPane.getId()){ thPane.getChildren().remove(button);}
            if (paneID == fPane.getId()){ fPane.getChildren().remove(button);}
            if (paneID == satPane.getId()){ satPane.getChildren().remove(button);}
            if (paneID == sunPane.getId()){ sunPane.getChildren().remove(button);}
        }
    }

    // makes CSV updates when you delete file using temporary CSV
    private void deleteFromCSV(Button button) {
        File copy = new File("src/main/resources/data/previous.csv");
        String paneID = button.getParent().getId();

        // use paneID to determine which rows to delete
        if (paneID == mPane.getId()){ paneID = "Monday";}
        if (paneID == tPane.getId()){ paneID = "Tuesday";}
        if (paneID == wPane.getId()){ paneID = "Wednesday";}
        if (paneID == thPane.getId()){ paneID = "Thursday";}
        if (paneID == fPane.getId()){ paneID = "Friday";}
        if (paneID == satPane.getId()){ paneID = "Saturday";}
        if (paneID == sunPane.getId()){ paneID = "Sunday";}
        try{
            String NEW_LINE_SEPARATOR = "\n";
            copyFile(todoListFile,copy);
            FileWriter fileWriter = new FileWriter(csvFile); // clear old CSV file
            fileWriter.append("TASK,COLOR,DAY");
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.flush();
            fileWriter.close();

            FileWriter fileWriter2 = new FileWriter(csvFile, true);

            Scanner inputStream = new Scanner(copy);
            inputStream.next(); // Skip header line

            int duplicateTaskCount = 0;
            while (inputStream.hasNext()){
                String data = inputStream.next();
                String col[] = data.split(",");

                if (button.getText().contains(col[0]) && col[col.length-1].equals(paneID)){
                    // do not want to delete other tasks with same name
                    duplicateTaskCount++;
                    if (duplicateTaskCount == 1) {
                        continue; // Skip hence delete row
                    }
                }
                fileWriter2.append(data);
                fileWriter2.append(NEW_LINE_SEPARATOR);
            }
            fileWriter2.flush();
            fileWriter2.close();
        }catch( IOException f){ f.printStackTrace(); }
    }

    // event handlers to create button tasks
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

    // create buttons for tasks
    private Button createButton(String text, String c, String day) {
        // set font colour based on brightness of button
        String fontColour = " ; -fx-text-fill: black;";
        if (Brightness(c) < 130){
            fontColour = " ; -fx-text-fill: white;";
        }
        Button button = new Button(text);

        // set style for button
        button.setStyle("-fx-background-color: "+ c + fontColour + "-fx-font-weight: bold;");
        button.setMinWidth(120);

        // event handlers for button
        button.setOnDragDetected(e -> {
            Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(button.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(buttonFormat, "button");
            db.setContent(cc);
            draggingButton = button ;
        });
        button.setOnDragDone(e -> draggingButton = null);

        // Right click context menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Delete");
        contextMenu.getItems().add(item1);
        button.setContextMenu(contextMenu);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY){
                    // Display context menu
                    contextMenu.show(button, event.getScreenX(), event.getScreenY());
                    item1.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            deleteDialog(button);
                        }
                    });

                }
            }
        });
        appendToCSV(text, c, day);
        return button ;
    }

    // initialize button
    private Button initButton(String text, String c, String day) {
        // set font colours based on button brightness
        String fontColour = " ; -fx-text-fill: black;";
        if (Brightness(c) < 130){
            fontColour = " ; -fx-text-fill: white;";
        }
        Button button = new Button(text);
        button.setStyle("-fx-background-color: "+ c + fontColour + "-fx-font-weight: bold;");
        button.setMinWidth(120);

        // event handlers for buttons

        button.setOnDragDetected(e -> {
            Dragboard db = button.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(button.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(buttonFormat, "button");
            db.setContent(cc);
            draggingButton = button ;
        });
        button.setOnDragDone(e -> draggingButton = null);

        // Right click context menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Delete");
        contextMenu.getItems().add(item1);
        button.setContextMenu(contextMenu);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY){
                    // Display context menu
                    contextMenu.show(button, event.getScreenX(), event.getScreenY());
                    item1.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            deleteDialog(button);
                        }
                    });
                }
            }
        });
        return button ;
    }

    // add new button task data for later use
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

    // coppy file data
    private static void copyFile(File src, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    // enable pane allows dragging
    private void enableDraggingUpdates(Pane pane) {
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat) && draggingButton != null && draggingButton.getParent() != pane) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        // event handler for when items dragged to pane
        pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat)) {
                ((Pane)draggingButton.getParent()).getChildren().remove(draggingButton);
                pane.getChildren().add(draggingButton);
                e.setDropCompleted(true);

                // update CSV
                File copy = new File("src/main/resources/data/previous.csv");
                try{
                    String NEW_LINE_SEPARATOR = "\n";
                    String DELIMITER = ",";
                    copyFile(todoListFile,copy);
                    FileWriter fileWriter = new FileWriter(csvFile); // clear old CSV file
                    fileWriter.append("TASK,COLOR,DAY");
                    fileWriter.append(NEW_LINE_SEPARATOR);
                    fileWriter.flush();
                    fileWriter.close();

                    FileWriter fileWriter2 = new FileWriter(csvFile, true);

                    Scanner inputStream = new Scanner(copy);
                    inputStream.next(); // Skip header line
                    while (inputStream.hasNext()){
                        String data = inputStream.next();
                        String col[] = data.split(",");

                        if (draggingButton.getText().contains(col[0])){
                            String newRow = "";
                            for (int i=0; i<col.length -1; i++){
                                newRow += col[i] + DELIMITER;
                            }
                            if (pane.getId() == mPane.getId()){ newRow+= "Monday";}
                            if (pane.getId()== tPane.getId()){ newRow+= "Tuesday";}
                            if (pane.getId()== wPane.getId()){ newRow+= "Wednesday";}
                            if (pane.getId() == thPane.getId()){ newRow+= "Thursday";}
                            if (pane.getId() == fPane.getId()){ newRow+= "Friday";}
                            if (pane.getId() == satPane.getId()){ newRow+= "Saturday";}
                            if (pane.getId()== sunPane.getId()){ newRow+= "Sunday";}
                            fileWriter2.append(newRow);
                            fileWriter2.append(NEW_LINE_SEPARATOR);
                            continue;
                        }
                        fileWriter2.append(data);
                        fileWriter2.append(NEW_LINE_SEPARATOR);
                    }
                    fileWriter2.flush();
                    fileWriter2.close();
                }catch( IOException f){
                    f.printStackTrace();
                }
            }
        });
    }
}
