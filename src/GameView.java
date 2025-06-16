import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class GameView {
    private VBox root;
    private TextArea verlaufArea;
    private Label allowedInputsLabel;
    private Label statusLabel;
    private Label visualLabel;
    private TextField inputField;
    private GameController controller;

    public GameView() {
        root = new VBox(10);
        root.setPadding(new Insets(10));

        verlaufArea = new TextArea();
        verlaufArea.setEditable(false);
        verlaufArea.setPrefHeight(300);
        verlaufArea.setFont(Font.font("Monospaced", 14));

        allowedInputsLabel = new Label("Erlaubte Eingaben:");
        statusLabel = new Label("Status:");
        visualLabel = new Label("Visualisierung:");

        inputField = new TextField();
        inputField.setPromptText("Gib Befehl ein und Enter drücken");
        inputField.setOnAction(e -> {
            if (controller != null) {
                String input = inputField.getText().trim();
                if (!input.isEmpty()) {
                    controller.processInput(input);
                    inputField.clear();
                }
            }
        });

        root.getChildren().addAll(verlaufArea, allowedInputsLabel, statusLabel, visualLabel, inputField);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public VBox getRoot() {
        return root;
    }

    public void updateVerlauf(String text) {
        verlaufArea.appendText(text + "\n");
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
}
