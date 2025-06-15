public class Raum extends Bereich {
    private boolean truheGeoeffnet = false;
    private boolean haendlerBesucht = false;

    @Override
    public boolean isErlaubteEingabe(String input) {
        return model.istImKampf() ? input.equals("1") || input.equals("2") :
               input.equals("w") || input.equals("s") || input.equals("1") || input.equals("2");
    }

    @Override
    public String getErlaubteEingaben() {
        return gegner != null ? "1 - Angriff, 2 - Blocken" : "w - vorwärts, s - zurück, 1 - Truhe öffnen, 2 - Händler";
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
            case "w":
            case "s":
                view.updateVerlauf("Du gehst " + (input.equals("w") ? "vorwärts" : "zurück") + ".");
                break;
        }
    }
}