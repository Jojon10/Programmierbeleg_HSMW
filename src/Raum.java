public class Raum extends Bereich {
    private boolean truheGeoeffnet = false;
    private boolean haendlerBesucht = false;
    private boolean mitBett;
    private boolean hatLeiter = false;

    public Raum(boolean mitBett) {
        this.mitBett = mitBett;
    }

    public void setLeiter(boolean wert) {
        this.hatLeiter = wert;
    }

    @Override
    public boolean isErlaubteEingabe(String input) {
        // Richtungen + Truhe(1) + Händler(2)
        return nachbarn.containsKey(input) || input.equals("1") || input.equals("2");
    }

    @Override
    public String getErlaubteEingaben() {
        StringBuilder sb = new StringBuilder();
        for (String richtung : nachbarn.keySet()) {
            sb.append(richtung).append(", ");
        }
        if (!truheGeoeffnet) sb.append("1, ");
        if (!haendlerBesucht) sb.append("2, ");
        if (sb.length() > 2) sb.setLength(sb.length() - 2); // letztes Komma entfernen
        return sb.toString();
    }

    @Override
    public void handleInput(String input, Game model, GameView view) {
        if (gegner != null) {
            view.updateVerlauf("Ein Kampf beginnt mit: " + gegner.getName());
            return;
        }

        if (hatLeiter) {
            view.updateVerlauf("Du findest eine Leiter zur nächsten Ebene!");
            model.naechsteEbene();
            return;
        }

        switch (input) {
            case "1":
                if (!truheGeoeffnet) {
                    view.updateVerlauf("Du öffnest die Truhe. Du findest ein Item!");
                    truheGeoeffnet = true;
                } else {
                    view.updateVerlauf("Die Truhe ist bereits geöffnet.");
                }
                break;
            case "2":
                if (!haendlerBesucht) {
                    view.updateVerlauf("Du triffst einen Händler. Kaufe etwas!");
                    haendlerBesucht = true;
                } else {
                    view.updateVerlauf("Der Händler ist weitergezogen.");
                }
                break;
            default:
                Bereich ziel = nachbarn.get(input);
                if (ziel != null) {
                    model.setAktuellerBereich(ziel);
                    view.updateVerlauf("Du gehst " + input + ".");
                } else {
                    view.updateVerlauf("Ungültige Richtung.");
                }
                break;
        }
    }
}
