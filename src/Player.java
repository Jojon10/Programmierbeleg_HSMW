public class Player {
    private int maxHp = 100;
    private int hp = 100;
    private double atk = 1.0;
    private double def = 1.0;
    private int dmg = 5;

    private int coins = 0;
    private int deaths = 0;

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) {
        this.maxHp = Math.max(1, maxHp);
        if (hp > this.maxHp) hp = this.maxHp; // einklemmen
    }

    public int getHp() { return hp; }
    public void setHp(int hp) {
        if (hp < 0) hp = 0;
        if (hp > maxHp) hp = maxHp;
        this.hp = hp;
    }

    public double getAtk() { return atk; }
    public void setAtk(double atk) { this.atk = atk; }

    public double getDef() { return def; }
    public void setDef(double def) { this.def = def; }

    public int getDmg() { return dmg; }
    public void setDmg(int dmg) { this.dmg = Math.max(0, dmg); }

    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = Math.max(0, coins); }
    public void addCoins(int delta) {
        this.coins += delta;
        if (this.coins < 0) this.coins = 0;
    }

    public int getDeaths() { return deaths; }
    public void incrementDeaths() { this.deaths++; }
}
