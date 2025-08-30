import java.util.HashMap;
import java.util.Map;

public abstract class Bereich {
    protected Gegner gegner;
    protected Map<String, Bereich> nachbarn = new HashMap<>();

    public void setNachbar(String richtung, Bereich bereich) {
        nachbarn.put(richtung, bereich);
    }

    public Bereich getNachbar(String richtung) {
        return nachbarn.get(richtung);
    }

    public void setGegner(Gegner gegner) {
        this.gegner = gegner;
    }

    public Gegner getGegner() {
        return gegner;
    }

    public abstract boolean isErlaubteEingabe(String input);

    public abstract String getErlaubteEingaben();

    public abstract void handleInput(String input, Game model, GameView view);
}
