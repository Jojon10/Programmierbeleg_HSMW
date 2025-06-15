public class Game {
    private Player player;
    private Dungeon dungeon;
    private Bereich aktuellerBereich;

    public Game() {
        this.player = new Player();
        this.dungeon = new Dungeon();
        this.aktuellerBereich = dungeon.getStartGang();
    }

    public Player getPlayer() { return player; }
    public Dungeon getDungeon() { return dungeon; }
    public Bereich getAktuellerBereich() { return aktuellerBereich; }
    public void setAktuellerBereich(Bereich bereich) { this.aktuellerBereich = bereich; }
}