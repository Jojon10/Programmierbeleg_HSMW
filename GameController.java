public class GameController {
    private Game model;
    private GameView view;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        aktualisiereMoeglichkeiten();
    }

    public void handleInput(String input) {
        Bereich bereich = model.getAktuellerBereich();
        if (bereich.isErlaubteEingabe(input)) {
            view.updateVerlauf("Aktion: " + input);
            bereich.handleInput(input, model, view);
            aktualisiereMoeglichkeiten();
        } else {
            view.updateVerlauf("Ungültige Eingabe. Erlaubt: " + bereich.getErlaubteEingaben());
        }
    }

    private void aktualisiereMoeglichkeiten() {
        view.updateOptionen(model.getAktuellerBereich().getErlaubteEingaben());
    }
}
