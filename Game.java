public class Game {
    private Player player;
    private Dungeon dungeon;
    private Bereich aktuellerBereich;
    private boolean imKampf = false;
    private boolean spielerBlockt = false;
    private boolean gegnerBlockt = false;

    public Game() {
        this.player = new Player();
        this.dungeon = new Dungeon();
        this.aktuellerBereich = dungeon.getStartGang();
    }

    public Player getPlayer() { return player; }
    public Dungeon getDungeon() { return dungeon; }
    public Bereich getAktuellerBereich() { return aktuellerBereich; }
    public void setAktuellerBereich(Bereich bereich) { this.aktuellerBereich = bereich; }

    public boolean istImKampf() { return imKampf; }
    public void setImKampf(boolean status) { imKampf = status; }

    public boolean isSpielerBlockt() { return spielerBlockt; }
    public void setSpielerBlockt(boolean b) { spielerBlockt = b; }

    public boolean isGegnerBlockt() { return gegnerBlockt; }
    public void setGegnerBlockt(boolean b) { gegnerBlockt = b; }
}