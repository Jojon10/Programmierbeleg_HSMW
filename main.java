import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class main extends Application {
    private GameView view;
    private GameController controller;

    @Override
    public void start(Stage primaryStage) {
        view = new GameView();
        Game model = new Game();
        controller = new GameController(model, view);

        view.setController(controller);

        Scene scene = new Scene(view.getRoot(), 800, 600);
        primaryStage.setTitle("Dungeon RPG");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}