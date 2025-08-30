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
                setupEbene1(); // Fallback
        }
    }

    private void setupEbene1() {
        // Start-Raum mit Bett
        Raum raumA = new Raum(true);

        // Gang mit Gegner (Belohnung: 3 Münzen)
        Gang gang1 = new Gang();
        gang1.setGegner(new Gegner("Ratte", 30, 6, 1.0, 1.0, 3));

        // Ziel-Raum mit Händler und fest definierter Truhe
        Raum raumB = new Raum(false);
        raumB.setHaendler(true);
        // ← HIER bestimmst du den individuellen Truheninhalt dieses Raums:
        raumB.setTruheLoot(
            new Weapon("Kurzschwert", 8, 1.20),
            new Armor("Leder-Rüstung", 120, 1.15)
        );
        raumB.setLeiter(true); // Leiter zur nächsten Ebene

        // Verknüpfungen (rechts/links: d/a)
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

        // Ziel-Raum mit anderer Truhe (ANDERE Items!) und Händler
        Raum raumB = new Raum(false);
        raumB.setHaendler(true);
        raumB.setTruheLoot(
            new Armor("Kettenhemd", 140, 1.30) // andere Rüstung als in Ebene 1
        );
        // Gegner in diesem Ziel-Raum (Belohnung: 5 Münzen)
        raumB.setGegner(new Gegner("Kobold", 50, 8, 1.1, 1.1, 5));

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
