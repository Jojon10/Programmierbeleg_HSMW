import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Raum extends Bereich {
    private boolean truheGeoeffnet = false;
    private boolean haendlerBesucht = false;
    private final boolean mitBett;
    private boolean hatLeiter = false;
    private boolean hatHaendler = false;

    // Inhalt der Truhe in diesem Raum
    private final List<Item> truhenLoot = new ArrayList<>();

    public Raum(boolean mitBett) {
        this.mitBett = mitBett;
    }

    // ---- Truhen-API ----
    public void setTruheLoot(Item... items) {
        truhenLoot.clear();
        if (items != null) {
            truhenLoot.addAll(Arrays.asList(items));
        }
        truheGeoeffnet = false; // neu befüllt -> wieder verschlossen
    }

    public void addTruheItem(Item item) {
        if (item != null) {
            truhenLoot.add(item);
            truheGeoeffnet = false;
        }
    }

    public boolean hatTruhe() {
        return !truheGeoeffnet && !truhenLoot.isEmpty();
    }

    // ---- Sonstige Optionen ----
    public void setLeiter(boolean wert) {
        this.hatLeiter = wert;
    }

    public void setHaendler(boolean wert) {
        this.hatHaendler = wert;
    }

    public boolean hatBett() {
        return mitBett;
    }

    public boolean hatLeiter() {
        return hatLeiter;
    }

    public boolean hatHaendler() {
        return hatHaendler;
    }

    @Override
    public boolean isErlaubteEingabe(String input) {
        if (input == null) return false;
        input = input.trim().toLowerCase();
        if (nachbarn.containsKey(input)) return true;

        switch (input) {
            case "1": return hatTruhe();
            case "2": return hatHaendler; // mehrfach kaufen erlaubt (1 Münze pro Trank)
            case "x": return hatLeiter;
            default:  return false;
        }
    }

    @Override
    public String getErlaubteEingaben() {
        StringBuilder sb = new StringBuilder();
        if (!nachbarn.isEmpty()) {
            sb.append(String.join(", ", nachbarn.keySet()));
        }
        if (hatTruhe()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("1 (Truhe)");
        }
        if (hatHaendler) {
            if (sb.length() > 0) sb.append(", ");
            sb.append("2 (Händler: Trank 1 Münze)");
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
            case "1": // Truhe öffnen
                if (hatTruhe()) {
                    StringBuilder sb = new StringBuilder("Du öffnest die Truhe:\n");
                    for (Item it : truhenLoot) {
                        String info = it.applyTo(model.getSpieler());
                        sb.append("- ").append(info).append("\n");
                    }
                    truheGeoeffnet = true; // ab jetzt dauerhaft leer
                    view.updateVerlauf(sb.toString().trim());
                } else {
                    view.updateVerlauf("Hier gibt es keine nutzbare Truhe.");
                }
                break;

            case "2": // Händler (Trank 1 Münze, +30 HP bis maxHp)
                if (hatHaendler) {
                    Spieler s = model.getSpieler();
                    if (s.getMuenzen() >= 1) {
                        s.addMuenzen(-1);
                        s.setHp(s.getHp() + 30); // wird in setHp auf maxHp begrenzt
                        haendlerBesucht = true;
                        view.updateVerlauf("Du kaufst einen Trank für 1 Münze (+30 HP). Verbleibende Münzen: " + s.getMuenzen());
                    } else {
                        view.updateVerlauf("Nicht genug Münzen. Ein Trank kostet 1 Münze.");
                    }
                } else {
                    view.updateVerlauf("Hier ist kein Händler.");
                }
                break;

            // "e" (Bett) entfällt – Reset ist global über "r" im Controller
            case "x": // Leiter
                if (hatLeiter) {
                    view.updateVerlauf("Du steigst die Leiter hinauf...");
                    model.naechsteEbene();
                } else {
                    view.updateVerlauf("Hier gibt es keine Leiter.");
                }
                break;

            default: // Bewegung
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
