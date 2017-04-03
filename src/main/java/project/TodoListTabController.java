package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

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

    @FXML
    void addToMonday(ActionEvent event) {
        //TODO: Open Dialog
        mPane.getChildren().add(createButton("Monday"));
        //TODO: AppendtoCSV
        /*for (Node component : mPane.getChildren()){
            System.out.println(component.toString());
        }*/
    }


    @FXML
    void addToTuesday(ActionEvent event) {
        tPane.getChildren().add(createButton("Tuesday"));
    }
    @FXML
    void addToWednesday(ActionEvent event) {
        wPane.getChildren().add(createButton("Wednesday"));
    }
    @FXML
    void addToThursday(ActionEvent event) {
        satPane.getChildren().add(createButton("Thursday"));
    }
    @FXML
    void addToFriday(ActionEvent event) {
        fPane.getChildren().add(createButton("Friday"));
    }
    @FXML
    void addToSaturday(ActionEvent event) {
        satPane.getChildren().add(createButton("Saturday"));
    }
    @FXML
    void addToSunday(ActionEvent event) {
        sunPane.getChildren().add(createButton("Sunday"));
    }



    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: seagreen; -fx-text-fill: white");
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
        button.setOnAction(event -> mPane.getChildren().remove(0,1));
        return button ;
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
