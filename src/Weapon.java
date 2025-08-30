public class Weapon extends Item {
    private final int dmg;       // Basis-Schaden des Spielers
    private final double atk;    // Angriffs-Multiplikator des Spielers

    public Weapon(String name, int dmg, double atk) {
        super(name);
        this.dmg = dmg;
        this.atk = atk;
    }

    public int getDmg() {
        return dmg;
    }

    public double getAtk() {
        return atk;
    }

    @Override
    public String applyTo(Player player) {
        player.setDmg(dmg);
        player.setAtk(atk);
        return "Waffe angelegt: " + name + " (DMG=" + dmg + ", ATK=" + String.format("%.2f", atk) + ")";
    }
}
