public class Armor extends Item {
    private int hp;
    private double def;

    public Armor(String name, int hp, double def) {
        super(name);
        this.hp = hp;
        this.def = def;
    }
    public int getHp() { return hp; }
    public double getDef() { return def; }
}
