import java.util.ArrayList;
import java.util.List;

public class Game {
    private Spieler spieler;
    private List<Ebene> ebenen;
    private int aktuelleEbeneIndex;
    private Bereich aktuellerBereich;

    public Game() {
        this.spieler = new Spieler();
        this.ebenen = new ArrayList<>();
        this.aktuelleEbeneIndex = 0;

        // Beispiel: 2 Ebenen anlegen
        ebenen.add(new Ebene(1));
        ebenen.add(new Ebene(2));

        // Startbereich der ersten Ebene setzen
        this.aktuellerBereich = ebenen.get(aktuelleEbeneIndex).getStartBereich();
    }

    public Spieler getPlayer() {
        return spieler;
    }

    public Bereich getAktuellerBereich() {
        return aktuellerBereich;
    }

    public void setAktuellerBereich(Bereich bereich) {
        this.aktuellerBereich = bereich;
    }

    public Ebene getAktuelleEbene() {
        return ebenen.get(aktuelleEbeneIndex);
    }

    public void naechsteEbene() {
        aktuelleEbeneIndex++;
        if (aktuelleEbeneIndex < ebenen.size()) {
            aktuellerBereich = ebenen.get(aktuelleEbeneIndex).getStartBereich();
        } else {
            Ebene neue = new Ebene(aktuelleEbeneIndex + 1);
            ebenen.add(neue);
            aktuellerBereich = neue.getStartBereich();
        }
    }
}
