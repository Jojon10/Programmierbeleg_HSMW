public class Player {
    private int hp = 100;
    private double atk = 1.0;
    private double def = 1.0;
    private int dmg = 5;

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, hp); }
    public double getAtk() { return atk; }
    public void setAtk(double atk) { this.atk = atk; }
    public double getDef() { return def; }
    public void setDef(double def) { this.def = def; }
    public int getDmg() { return dmg; }
    public void setDmg(int dmg) { this.dmg = dmg; }
}
