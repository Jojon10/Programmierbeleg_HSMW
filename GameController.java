public class GameController {
    private Game model;
    private GameView view;
    private Battle battle;
    private boolean imKampf = false;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        updateAllowedInputs();
        updateView();
    }

    public void processInput(String input) {
        if (imKampf) {
            if (!input.equals("1") && !input.equals("2")) {
                view.updateAllowedInputs("Nur 1 (Angriff) oder 2 (Blocken) erlaubt!");
                return;
            }
            int aktion = Integer.parseInt(input);
            String ergebnis = battle.spielerZug(aktion);
            view.updateVerlauf(ergebnis);
            if (battle.istKampfVorbei()) {
                imKampf = false;
                if (model.getPlayer().getHp() <= 0) {
                    view.updateVerlauf("Game Over! Du wurdest besiegt.");
                } else {
                    view.updateVerlauf("Kampf beendet. Du hast gewonnen!");
                    model.getAktuellerBereich().setGegner(null);
                }
                updateAllowedInputs();
            } else {
                view.updateAllowedInputs(battle.getAllowedActions());
            }
            updateView();
        } else {
            Bereich aktuellerBereich = model.getAktuellerBereich();

            if (!aktuellerBereich.isErlaubteEingabe(input)) {
                view.updateVerlauf("Ungültige Eingabe. Erlaubt: " + aktuellerBereich.getErlaubteEingaben());
                return;
            }

            aktuellerBereich.handleInput(input, model, view);

            Gegner gegner = model.getAktuellerBereich().getGegner();
            if (gegner != null) {
                startKampf(gegner);
            }

            updateAllowedInputs();
            updateView();
        }
    }

    private void startKampf(Gegner gegner) {
        imKampf = true;
        battle = new Battle(model.getPlayer(), gegner);
        view.updateVerlauf("Kampf beginnt gegen " + gegner.getName() + "!");
        view.updateAllowedInputs(battle.getAllowedActions());
    }

    private void updateAllowedInputs() {
        if (imKampf) {
            view.updateAllowedInputs(battle.getAllowedActions());
        } else {
            view.updateAllowedInputs(model.getAktuellerBereich().getErlaubteEingaben());
        }
    }

    private void updateView() {
        String status = "HP: " + model.getPlayer().getHp();
        view.updateStatus(status);

        view.updateVisual("Du bist in einem Bereich.");
    }
}
