public class GameController {
    private final Game model;
    private final GameView view;
    private Kampflogik kampf;
    private boolean imKampf = false;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        updateAllowedInputs();
        updateView();
    }

    public void processInput(String input) {
        if (input == null) return;
        input = input.trim().toLowerCase();
        if (input.isEmpty()) return;

        if (imKampf) {
            // Nur Kampfaktionen zulassen
            if (!input.equals("1") && !input.equals("2")) {
                view.updateVerlauf("Im Kampf sind nur die Aktionen 1 (Angriff) und 2 (Block) erlaubt.");
                updateAllowedInputs();
                return;
            }

            String bericht = kampf.processInput(input);
            view.updateVerlauf(bericht);

            if (kampf.istKampfVorbei()) {
                if (model.getPlayer().getHp() <= 0) {
                    // Tod zählen & Respawn am Bett
                    model.getPlayer().erhoeheTode();
                    view.updateVerlauf("Du bist ohnmächtig geworden und erwachst wieder am Bett...");
                    model.getPlayer().setHp(model.getPlayer().getMaxHp()); // Bett heilt auf Max HP
                    model.setAktuellerBereich(model.getAktuelleEbene().getStartBereich());
                } else {
                    int belohnung = kampf.getMuenzenBelohnung();
                    model.getPlayer().addMuenzen(belohnung);
                    view.updateVerlauf("Du hast " + kampf.getGegnerName() + " besiegt und erhältst " + belohnung + " Münze(n).");
                    // Gegner aus dem Bereich entfernen
                    if (model.getAktuellerBereich() != null) {
                        model.getAktuellerBereich().setGegner(null);
                    }
                }
                imKampf = false;
            }

            updateAllowedInputs();
            updateView();
            return;
        }

        // Nicht im Kampf: Eingabe an aktuellen Bereich delegieren
        Bereich bereich = model.getAktuellerBereich();
        if (bereich == null) {
            view.updateVerlauf("Es gibt keinen aktuellen Bereich.");
            return;
        }

        if (!bereich.isErlaubteEingabe(input)) {
            view.updateVerlauf("Ungültige Eingabe.");
            updateAllowedInputs();
            return;
        }

        bereich.handleInput(input, model, view);

        // Prüfen, ob am/im neuen Bereich ein Gegner wartet → Kampf starten
        Bereich aktueller = model.getAktuellerBereich();
        if (aktueller != null && aktueller.getGegner() != null) {
            kampf = new Kampflogik(model.getPlayer(), aktueller.getGegner());
            imKampf = true;
            view.updateVerlauf("Ein Kampf beginnt mit: " + aktueller.getGegner().getName());
        }

        updateAllowedInputs();
        updateView();
    }

    public void updateAllowedInputs() {
        if (imKampf) {
            view.updateAllowedInputs(kampf.getErlaubteAktionen());
        } else {
            Bereich b = model.getAktuellerBereich();
            view.updateAllowedInputs(b != null ? b.getErlaubteEingaben() : "—");
        }
    }

    private void updateView() {
        Spieler s = model.getPlayer();
        String status = "HP=" + s.getHp() + "/" + s.getMaxHp()
                + " | ATK=" + String.format("%.2f", s.getAtk())
                + " | DEF=" + String.format("%.2f", s.getDef())
                + " | DMG=" + s.getDmg()
                + " | Münzen=" + s.getMuenzen()
                + " | Tode=" + s.getTode();
        view.updateStatus(status);

        Bereich b = model.getAktuellerBereich();
        if (b == null) {
            view.updateVisual("Kein Bereich.");
            return;
        }

        String typ = (b instanceof Raum) ? "Raum" : "Gang";
        String extras = "";
        if (b instanceof Raum) {
            Raum r = (Raum) b;
            extras = (r.hatBett() ? " | Bett" : "")
                    + (r.hatLeiter() ? " | Leiter" : "")
                    + (r.hatTruhe() ? " | Truhe" : "")
                    + (r.hatHaendler() ? " | Händler" : "");
        }

        if (b.getGegner() != null) {
            // Während des Kampfes die HP des Gegners anzeigen
            if (imKampf) {
                extras += " | Gegner: " + b.getGegner().getName() + " (HP=" + b.getGegner().getHp() + ")";
            } else {
                extras += " | Gegner: " + b.getGegner().getName();
            }
        }

        view.updateVisual(typ + extras);
    }
}
