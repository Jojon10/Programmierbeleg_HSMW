public abstract class Bereich {
    protected Gegner gegner;

    public Bereich() {}

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