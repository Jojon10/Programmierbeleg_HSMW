public class Gegner {
    private final String name;
    private int hp;
    private final double atk;
    private final double def;
    private final int dmg;
    private final int muenzenBelohnung;

    public Gegner(String name, int hp, int dmg, double atk, double def, int muenzenBelohnung) {
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
        this.atk = atk;
        this.def = def;
        this.muenzenBelohnung = Math.max(0, muenzenBelohnung);
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public double getAtk() {
        return atk;
    }

    public double getDef() {
        return def;
    }

    public int getDmg() {
        return dmg;
    }

    public int getMuenzenBelohnung() {
        return muenzenBelohnung;
    }
}
