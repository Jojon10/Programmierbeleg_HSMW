import java.util.*;

public class Ebene {
    private final Map<String, Bereich> bereiche = new HashMap<>();
    private Bereich startBereich;

    public Ebene(int aktuelleEbene) {
        switch (aktuelleEbene) {
            case 1:
                setupEbene1();
                break;
            case 2:
                setupEbene2();
                break;
            default:
                setupEbene1(); // Fallback: einfache Ebene
        }
    }

    private void setupEbene1() {
        // Start-Raum mit Bett
        Raum raumA = new Raum(true);
        // Gang mit Gegner
        Gang gang1 = new Gang();
        gang1.setGegner(new Gegner("Ratte", 30, 6, 1.0, 1.0, 3)); // 3 Münzen Belohnung
        // Ziel-Raum mit Truhe, Händler und Leiter zur nächsten Ebene
        Raum raumB = new Raum(false);
        raumB.setTruhe(true);
        raumB.setHaendler(true);
        raumB.setLeiter(true);

        // Verknüpfungen (nur rechts/links: d/a)
        raumA.setNachbar("d", gang1);
        gang1.setNachbar("a", raumA);

        gang1.setNachbar("d", raumB);
        raumB.setNachbar("a", gang1);

        startBereich = raumA;
        bereiche.put("RaumA", raumA);
        bereiche.put("Gang1", gang1);
        bereiche.put("RaumB", raumB);
    }

    private void setupEbene2() {
        // Start-Raum mit Bett
        Raum raumA = new Raum(true);
        // Gang ohne Gegner
        Gang gang1 = new Gang();
        // Ziel-Raum mit Truhe und Händler (keine Leiter mehr)
        Raum raumB = new Raum(false);
        raumB.setTruhe(true);
        raumB.setHaendler(true);

        // In der zweiten Ebene sitzt der Gegner im Ziel-Raum
        raumB.setGegner(new Gegner("Kobold", 50, 8, 1.1, 1.1, 5)); // 5 Münzen Belohnung

        // Verknüpfungen
        raumA.setNachbar("d", gang1);
        gang1.setNachbar("a", raumA);

        gang1.setNachbar("d", raumB);
        raumB.setNachbar("a", gang1);

        startBereich = raumA;
        bereiche.put("RaumA2", raumA);
        bereiche.put("Gang1_2", gang1);
        bereiche.put("RaumB2", raumB);
    }

    public Bereich getStartBereich() {
        return startBereich;
    }

    public Map<String, Bereich> getBereiche() {
        return bereiche;
    }
}
