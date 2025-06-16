import java.util.*;

public class Game {
    private Player player;
    private List<Ebene> ebenen;
    private int aktuelleEbeneIndex;
    private Bereich aktuellerBereich;

    public Game() {
        this.player = new Player();
        this.ebenen = new ArrayList<>();
        this.aktuelleEbeneIndex = 0;

        // Beispiel: 2 Ebenen anlegen
        ebenen.add(new Ebene());
        ebenen.add(new Ebene());

        this.aktuellerBereich = ebenen.get(0).getStartBereich();
    }

    public Player getPlayer() { return player; }
    public Bereich getAktuellerBereich() { return aktuellerBereich; }
    public void setAktuellerBereich(Bereich bereich) { this.aktuellerBereich = bereich; }

    public void naechsteEbene() {
        aktuelleEbeneIndex++;
        if (aktuelleEbeneIndex < ebenen.size()) {
            aktuellerBereich = ebenen.get(aktuelleEbeneIndex).getStartBereich();
        } else {
            // Ende oder neue Ebene generieren
        }
    }
}
