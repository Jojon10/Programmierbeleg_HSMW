public class Weapon extends Item {
    private double dmg;
    private double atk;

    public Weapon(String name, double dmg, double atk) {
        super(name);
        this.dmg = dmg;
        this.atk = atk;
    }
    public double getDmg() { return dmg; }
    public double getAtk() { return atk; }
}
