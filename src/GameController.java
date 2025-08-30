public class GameController {
    private final Game model;
    private final GameView view;
    private Battle battle;
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

            String bericht = battle.processInput(input);
            view.updateVerlauf(bericht);

            if (battle.istKampfVorbei()) {
                if (model.getPlayer().getHp() <= 0) {
                    // Tod zählen & Respawn am Bett
                    model.getPlayer().incrementDeaths();
                    view.updateVerlauf("Du bist ohnmächtig geworden und erwachst wieder am Bett...");
                    model.getPlayer().setHp(model.getPlayer().getMaxHp()); // Bett heilt auf Max HP
                    model.setAktuellerBereich(model.getAktuelleEbene().getStartBereich());
                } else {
                    int reward = battle.getCoinReward();
                    model.getPlayer().addCoins(reward);
                    view.updateVerlauf("Du hast " + battle.getGegnerName() + " besiegt und erhältst " + reward + " Münze(n).");
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

        // Prüfen, ob an/ im neuen Bereich ein Gegner wartet → Kampf starten
        Bereich aktueller = model.getAktuellerBereich();
        if (aktueller != null && aktueller.getGegner() != null) {
            battle = new Battle(model.getPlayer(), aktueller.getGegner());
            imKampf = true;
            view.updateVerlauf("Ein Kampf beginnt mit: " + aktueller.getGegner().getName());
        }

        updateAllowedInputs();
        updateView();
    }

    public void updateAllowedInputs() {
        if (imKampf) {
            view.updateAllowedInputs(battle.getAllowedActions());
        } else {
            Bereich b = model.getAktuellerBereich();
            view.updateAllowedInputs(b != null ? b.getErlaubteEingaben() : "—");
        }
    }

    private void updateView() {
        Player p = model.getPlayer();
        String status = "HP=" + p.getHp() + "/" + p.getMaxHp() +
                        " | ATK=" + String.format("%.2f", p.getAtk()) +
                        " | DEF=" + String.format("%.2f", p.getDef()) +
                        " | DMG=" + p.getDmg() +
                        " | Coins=" + p.getCoins() +
                        " | Tode=" + p.getDeaths();
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
            extras = (r.hatBett() ? " | Bett" : "") +
                     (r.hatLeiter() ? " | Leiter" : "") +
                     (r.hatTruhe() ? " | Truhe" : "") +
                     (r.hatHaendler() ? " | Händler" : "");
        }
        if (b.getGegner() != null) {
            extras += " | Gegner: " + b.getGegner().getName();
        }
        view.updateVisual(typ + extras);
    }
}
