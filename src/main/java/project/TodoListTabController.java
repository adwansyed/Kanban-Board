package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Created by 100525709 on 3/14/2017.
 */
public class TodoListTabController {
    @FXML
    private AnchorPane textField;

    @FXML
    private Button testButton;

    @FXML
    void testAction(ActionEvent event) {
        System.out.println("Test");
    }
}
