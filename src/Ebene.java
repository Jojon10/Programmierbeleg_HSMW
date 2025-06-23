import java.util.*;

public class Ebene {
    private Map<String, Bereich> bereiche = new HashMap<>();
    private Bereich startBereich;

     public Ebene(int aktuelleEbene) {
        switch (aktuelleEbene) {
            case 1:
                setupEbene1();
                break;
            case 2:
                setupEbene2();
                break;
            // weitere Ebenen...
            default:
                throw new IllegalArgumentException(
                    "Unbekannte Ebene: " + aktuelleEbene);
        }
    }
    private void setupEbene1() {
        Raum startRaum   = new Raum(true);
        Gang gang1       = new Gang();
        Raum kampfraum   = new Raum(false);
        kampfraum.setGegner(new Gegner("Goblin", 30, 6, 1.1, 0.8));
        Raum leiterRaum  = new Raum(false);
        leiterRaum.setLeiter(true);

        // Nachbarn verknüpfen
        startRaum.setNachbar("w", gang1);
        gang1.setNachbar("s", startRaum);
        gang1.setNachbar("w", kampfraum);
        kampfraum.setNachbar("s", gang1);
        kampfraum.setNachbar("w", leiterRaum);
        leiterRaum.setNachbar("s", kampfraum);

        // Startbereich und Map befüllen
        startBereich = startRaum;
        bereiche.put("Start", startRaum);
        bereiche.put("Gang1", gang1);
        bereiche.put("KampfRaum", kampfraum);
        bereiche.put("Leiter", leiterRaum);
    }

    private void setupEbene2() {
        // Beispiel für Ebene 2:
        Raum raumA = new Raum(true);
        Raum raumB = new Raum(false);
        raumB.setGegner(new Gegner("Skelett", 40, 5, 1.0, 0.9));

        raumA.setNachbar("n", raumB);
        raumB.setNachbar("s", raumA);

        startBereich = raumA;
        bereiche.put("RaumA", raumA);
        bereiche.put("RaumB", raumB);
    }

    // Getter für startBereich und bereiche, falls benötigt
    public Bereich getStartBereich() {
    return startBereich;
    }

    public Map<String, Bereich> getBereiche() {
        return bereiche;
    }
}
