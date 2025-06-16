public class Gegner {
    private String name;
    private int hp;
    private double atk;
    private double def;
    private int dmg;

    public Gegner(String name, int hp, int dmg, double atk, double def) {
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.atk = atk;
        this.def = def;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, hp); }
    public double getAtk() { return atk; }
    public double getDef() { return def; }
    public int getDmg() { return dmg; }
}
