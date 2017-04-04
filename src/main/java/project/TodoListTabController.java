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

import java.util.Optional;

/**
 * Created by 100525709 on 3/14/2017.
 */
public class TodoListTabController {

    private final DataFormat buttonFormat = new DataFormat(" ");
    @FXML private Button draggingButton;
    @FXML private FlowPane mPane, tPane, wPane, thPane, fPane, satPane, sunPane;

    public void initialize(){

        //TODO: pre-load CSV

        // Initialize all panes to handle dragging
        addDropHandling(mPane);
        addDropHandling(tPane);
        addDropHandling(wPane);
        addDropHandling(thPane);
        addDropHandling(fPane);
        addDropHandling(satPane);
        addDropHandling(sunPane);
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

    private void addDialog(Pane pane) {
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
            pane.getChildren().add(createButton(task.getText(),colour));
        });
    }

    @FXML
    void addToMonday(ActionEvent event) {
        addDialog(mPane);
    }
    @FXML
    void addToTuesday(ActionEvent event) {
        addDialog(tPane);
    }
    @FXML
    void addToWednesday(ActionEvent event) {
        addDialog(wPane);
    }
    @FXML
    void addToThursday(ActionEvent event) {
        addDialog(thPane);
    }
    @FXML
    void addToFriday(ActionEvent event){
        addDialog(fPane);
    }
    @FXML
    void addToSaturday(ActionEvent event) {
        addDialog(satPane);
    }
    @FXML
    void addToSunday(ActionEvent event) {
        addDialog(sunPane);
    }

    private Button createButton(String text, String c) {
        Button button = new Button(text);
        //TODO: AppendtoCSV
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
        updateCSV();
        button.setOnDragDone(e -> draggingButton = null);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY){
                    String buttonName = button.getText();
                    String paneID = button.getParent().getId();
                    /*System.out.println(paneID);
                    System.out.println(buttonName);
                    System.out.println(button.getLayoutBounds());
                    System.out.println(button.getBoundsInLocal());*/
                    if (paneID == mPane.getId()){ mPane.getChildren().remove(button);}
                    if (paneID == tPane.getId()){ tPane.getChildren().remove(button);}
                    if (paneID == wPane.getId()){ wPane.getChildren().remove(button);}
                    if (paneID == thPane.getId()){ thPane.getChildren().remove(button);}
                    if (paneID == fPane.getId()){ fPane.getChildren().remove(button);}
                    if (paneID == satPane.getId()){ satPane.getChildren().remove(button);}
                    if (paneID == sunPane.getId()){ sunPane.getChildren().remove(button);}
                    //TODO: updateCSV
                    updateCSV();
                }
            }
        });
        return button ;
    }

    private void updateCSV() {
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String FILE_HEADER = "TASK,COLOR,DAY";

      /* for (javafx.scene.Node component : mPane.getChildren()){
            System.out.println(component.toString());
        }*/

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
