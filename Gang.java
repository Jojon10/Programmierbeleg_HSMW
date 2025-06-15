public class Gang extends Bereich {
    @Override
    public boolean isErlaubteEingabe(String input) {
        return input.equals("w") || input.equals("s");
    }

    @Override
    public String getErlaubteEingaben() {
        return "w - vorwärts, s - rückwärts";
    }

    @Override
    public void handleInput(String input, Game model, GameView view) {
        view.updateVerlauf("Du gehst " + (input.equals("w") ? "den Gang weiter" : "zurück") + ".");
    }
}