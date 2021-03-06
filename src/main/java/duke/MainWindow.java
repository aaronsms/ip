package duke;

import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);

        DialogBox user = DialogBox.getUserDialog(input, userImage);
        DialogBox duke = DialogBox.getDukeDialog(response, dukeImage);
        HBox.setHgrow(user, Priority.ALWAYS);
        HBox.setHgrow(duke, Priority.ALWAYS);

        dialogContainer.getChildren().addAll(user, duke);

        userInput.clear();
        if (input.equalsIgnoreCase("bye")) {
            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            cf.thenRun(Platform::exit).thenRun(() -> System.exit(0));
        }
    }
}
