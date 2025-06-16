public class Dungeon {
    private Gang startGang;

    public Dungeon() {
        startGang = new Gang();
        startGang.setGegner(new Gegner("Ratte", 20, 4, 1.0, 0.5));
    }

    public Gang getStartGang() {
        return startGang;
    }
}
