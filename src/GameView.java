import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class GameView {
    private final VBox root;
    private final TextArea verlaufArea;
    private final Label allowedInputsLabel;
    private final Label statusLabel;
    private final Label visualLabel;
    private final TextField inputField;
    private GameController controller;

    public GameView() {
        root = new VBox(10);
        root.setPadding(new Insets(12));

        Label title = new Label("Dungeon RPG");
        title.setFont(new Font(20));

        statusLabel = new Label("Status: —");
        visualLabel = new Label("Visualisierung: —");
        allowedInputsLabel = new Label("Erlaubte Eingaben: —");

        verlaufArea = new TextArea();
        verlaufArea.setEditable(false);
        verlaufArea.setPrefRowCount(15);

        inputField = new TextField();
        inputField.setPromptText("Eingabe (w/a/s/d, 1=Truhe, 2=Händler [1 Münze], e=Bett, x=Leiter).");
        inputField.setOnAction(e -> {
            String text = inputField.getText();
            if (controller != null && text != null && !text.isBlank()) {
                controller.processInput(text);
            }
            inputField.clear();
        });

        root.getChildren().addAll(title, statusLabel, visualLabel, allowedInputsLabel, verlaufArea, inputField);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public Pane getRoot() {
        return root;
    }

    public void updateAllowedInputs(String inputs) {
        allowedInputsLabel.setText("Erlaubte Eingaben: " + inputs);
    }

    public void updateStatus(String status) {
        statusLabel.setText("Status: " + status);
    }

    public void updateVisual(String visual) {
        visualLabel.setText("Visualisierung: " + visual);
    }

    public void updateVerlauf(String text) {
        if (text == null) return;
        if (!verlaufArea.getText().isEmpty()) {
            verlaufArea.appendText("\n");
        }
        verlaufArea.appendText(text);
        verlaufArea.setScrollTop(Double.MAX_VALUE);
    }
}
