public class Gegner {
    private String name;
    private int hp;
    private int dmg;
    private double atk;
    private double def;

    public Gegner(String name, int hp, int dmg, double atk, double def) {
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.atk = atk;
        this.def = def;
    }

    public int berechneSchaden() {
        return (int)(atk * dmg);
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getDmg() { return dmg; }
    public double getAtk() { return atk; }
    public double getDef() { return def; }
    public void setHp(int hp) { this.hp = hp; }
}