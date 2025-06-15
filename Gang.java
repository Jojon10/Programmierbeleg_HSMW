public class Gang extends Bereich {
    @Override
    public boolean isErlaubteEingabe(String input) {
        return nachbarn.containsKey(input);
    }

    @Override
    public String getErlaubteEingaben() {
        return String.join(", ", nachbarn.keySet());
    }

    @Override
    public void handleInput(String input, Game model, GameView view) {
        if (gegner != null) {
            view.updateVerlauf("Ein Kampf beginnt mit: " + gegner.getName());
            return;
        }

        Bereich ziel = nachbarn.get(input);
        if (ziel != null) {
            model.setAktuellerBereich(ziel);
            view.updateVerlauf("Du gehst " + input + ".");
        } else {
            view.updateVerlauf("Ungültige Richtung.");
        }
    }
}
