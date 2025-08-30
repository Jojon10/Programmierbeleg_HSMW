public class Armor extends Item {
    private final int maxHp;     // neue maximale HP
    private final double def;    // Verteidigungs-Multiplikator

    public Armor(String name, int maxHp, double def) {
        super(name);
        this.maxHp = maxHp;
        this.def = def;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public double getDef() {
        return def;
    }

    @Override
    public String applyTo(Player player) {
        player.setMaxHp(maxHp);
        player.setDef(def);
        return "Rüstung angelegt: " + name + " (maxHP=" + maxHp + ", DEF×" + String.format("%.2f", def) + ")";
    }
}
