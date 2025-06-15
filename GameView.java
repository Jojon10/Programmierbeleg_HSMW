import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GameView {
    private VBox root;
    private Label verlaufLabel;
    private Label visualLabel;
    private Label optionenLabel;
    private TextField inputField;
    private GameController controller;

    public GameView() {
        verlaufLabel = new Label("Willkommen im Dungeon!");
        visualLabel = new Label("Visualisierung folgt.");
        optionenLabel = new Label("");
        inputField = new TextField();

        HBox labels = new HBox(10, verlaufLabel, visualLabel);
        root = new VBox(10, labels, optionenLabel, inputField);

        inputField.setOnAction(e -> {
            if (controller != null) {
                controller.handleInput(inputField.getText());
                inputField.clear();
            }
        });
    }

    public VBox getRoot() { return root; }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void updateVerlauf(String text) {
        verlaufLabel.setText(text);
    }

    public void updateVisual(String text) {
        visualLabel.setText(text);
    }

    public void updateOptionen(String text) {
        optionenLabel.setText("Mögliche Eingaben: " + text);
    }
}