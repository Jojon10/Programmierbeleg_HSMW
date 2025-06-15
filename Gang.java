public class Gang extends Bereich {
    @Override
    public boolean isErlaubteEingabe(String input) {
        return gegner != null ? input.equals("1") || input.equals("2") : input.equals("w") || input.equals("s");
    }

    @Override
    public String getErlaubteEingaben() {
        return gegner != null ? "1 - Angriff, 2 - Blocken" : "w - vorwärts, s - rückwärts";
    }

    @Override
    public void handleInput(String input, Game model, GameView view) {
        if (model.istImKampf()) {
            kampfInput(input, model, view);
            return;
        }
        if (gegner != null) {
            starteKampf(model, view);
            return;
        }
        view.updateVerlauf("Du gehst " + (input.equals("w") ? "den Gang weiter" : "zurück") + ".");
    }
}