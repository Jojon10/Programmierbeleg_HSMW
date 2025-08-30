import java.util.Random;

public class Raum extends Bereich {
    private boolean truheGeoeffnet = false;
    private boolean haendlerBesucht = false;
    private final boolean mitBett;
    private boolean hatLeiter = false;

    private boolean hatTruhe = false;
    private boolean hatHaendler = false;

    public Raum(boolean mitBett) {
        this.mitBett = mitBett;
    }

    public void setLeiter(boolean wert) {
        this.hatLeiter = wert;
    }

    public void setTruhe(boolean wert) { this.hatTruhe = wert; }

    public void setHaendler(boolean wert) { this.hatHaendler = wert; }

    public boolean hatBett() { return mitBett; }
    public boolean hatLeiter() { return hatLeiter; }
    public boolean hatTruhe() { return hatTruhe && !truheGeoeffnet; }
    public boolean hatHaendler() { return hatHaendler; }

    @Override
    public boolean isErlaubteEingabe(String input) {
        if (input == null) return false;
        input = input.trim().toLowerCase();
        if (nachbarn.containsKey(input)) return true;
        switch (input) {
            case "1": return hatTruhe && !truheGeoeffnet;
            case "2": return hatHaendler; // mehrfach kaufen erlaubt
            case "e": return mitBett;
            case "x": return hatLeiter;
            default: return false;
        }
    }

    @Override
    public String getErlaubteEingaben() {
        StringBuilder sb = new StringBuilder();
        if (!nachbarn.isEmpty()) {
            sb.append(String.join(", ", nachbarn.keySet()));
        }
        if (hatTruhe && !truheGeoeffnet) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("1 (Truhe)");
        }
        if (hatHaendler) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("2 (Händler: Trank 1 Münze)");
        }
        if (mitBett) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("e (Bett)");
        }
        if (hatLeiter) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("x (Leiter)");
        }
        return sb.toString();
    }

    @Override
    public void handleInput(String input, Game model, GameView view) {
        input = input.trim().toLowerCase();

        // Kampf blockiert Bewegung/Interaktion – Controller startet danach den Kampf
        if (gegner != null) {
            view.updateVerlauf("Ein Kampf beginnt mit: " + gegner.getName());
            return;
        }

        switch (input) {
            case "1":
                if (hatTruhe && !truheGeoeffnet) {
                    truheGeoeffnet = true;
                    Random r = new Random();
                    if (r.nextBoolean()) {
                        // Waffe
                        Weapon w = new Weapon("Kurzschwert", 8, 1.20);
                        model.getPlayer().setDmg((int)Math.round(w.getDmg()));
                        model.getPlayer().setAtk(w.getAtk());
                        view.updateVerlauf("Du öffnest die Truhe und findest eine Waffe: " + w.getName() +
                                " (DMG=" + (int)w.getDmg() + ", ATK×" + String.format("%.2f", w.getAtk()) + ").");
                    } else {
                        // Rüstung → setzt Max HP & DEF
                        Armor a = new Armor("Leder-Rüstung", 120, 1.20);
                        model.getPlayer().setMaxHp(a.getHp());
                        // aktuelle HP einklemmen (geschieht in setMaxHp)
                        model.getPlayer().setDef(a.getDef());
                        view.updateVerlauf("Du öffnest die Truhe und findest eine Rüstung: " + a.getName() +
                                " (maxHP=" + a.getHp() + ", DEF×" + String.format("%.2f", a.getDef()) + ").");
                    }
                } else {
                    view.updateVerlauf("Hier gibt es keine nutzbare Truhe.");
                }
                break;
            case "2":
                if (hatHaendler) {
                    Player p = model.getPlayer();
                    if (p.getCoins() >= 1) {
                        p.addCoins(-1);
                        p.setHp(p.getHp() + 30); // wird in setHp auf maxHp begrenzt
                        haendlerBesucht = true;
                        view.updateVerlauf("Du kaufst einen Trank für 1 Münze (+30 HP). Verbleibende Münzen: " + p.getCoins());
                    } else {
                        view.updateVerlauf("Nicht genug Münzen. Ein Trank kostet 1 Münze.");
                    }
                } else {
                    view.updateVerlauf("Hier ist kein Händler.");
                }
                break;
            case "e":
                if (mitBett) {
                    model.getPlayer().setHp(model.getPlayer().getMaxHp()); // Bett heilt auf Max HP
                    view.updateVerlauf("Du ruhst dich am Bett aus. HP vollständig aufgefüllt.");
                } else {
                    view.updateVerlauf("Hier gibt es kein Bett.");
                }
                break;
            case "x":
                if (hatLeiter) {
                    view.updateVerlauf("Du steigst die Leiter hinauf...");
                    model.naechsteEbene();
                } else {
                    view.updateVerlauf("Hier gibt es keine Leiter.");
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
