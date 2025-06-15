public class Player {
    private double atk = 1.0;
    private double def = 1.0;
    private int dmg = 5;
    private int hp = 100;

    public Player() {
        atk = 1.0;
        def = 1.0;
        dmg = 5;
        hp = 100;
    }

    public int berechneSchaden() {
        return (int)(atk * dmg);
    }

    public int getHp() { return hp; }
    public int getDmg() { return dmg; }
    public double getAtk() { return atk; }
    public double getDef() { return def; }

    public void setHp(int hp) { this.hp = hp; }
    public void setDmg(int dmg) { this.dmg = dmg; }
    public void setAtk(double atk) { this.atk = atk; }
    public void setDef(double def) { this.def = def; }
}