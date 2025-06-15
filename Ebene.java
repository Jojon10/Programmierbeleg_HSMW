import java.util.*;

public class Ebene {
    private Map<String, Bereich> bereiche = new HashMap<>();
    private Bereich startBereich;

    public Ebene() {
        // Beispielhafte Levelstruktur anlegen
        Raum startRaum = new Raum(true);
        Gang gang1 = new Gang();
        Raum kampfraum = new Raum(false);
        kampfraum.setGegner(new Gegner("Goblin", 30, 6, 1.1, 0.8));
        Raum leiterRaum = new Raum(false);
        leiterRaum.setLeiter(true);

        startRaum.setNachbar("w", gang1);
        gang1.setNachbar("s", startRaum);
        gang1.setNachbar("w", kampfraum);
        kampfraum.setNachbar("s", gang1);
        kampfraum.setNachbar("w", leiterRaum);
        leiterRaum.setNachbar("s", kampfraum);

        startBereich = startRaum;

        bereiche.put("Start", startRaum);
        bereiche.put("Gang1", gang1);
        bereiche.put("KampfRaum", kampfraum);
        bereiche.put("Leiter", leiterRaum);
    }

    public Bereich getStartBereich() {
        return startBereich;
    }
}
