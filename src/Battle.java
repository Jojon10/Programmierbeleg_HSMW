import java.util.Random;

public class Battle {
    private final Player player;
    private final Gegner gegner;
    private boolean spielerBlockt = false;
    private boolean gegnerBlockt = false;
    private final Random random = new Random();

    public Battle(Player player, Gegner gegner) {
        this.player = player;
        this.gegner = gegner;
    }

    public String getAllowedActions() {
        return "1: Angriff, 2: Block";
    }

    public String getGegnerName() {
        return gegner.getName();
    }

    public String processInput(String input) {
        StringBuilder sb = new StringBuilder();
        // Spieleraktion
        if ("1".equals(input)) {
            spielerBlockt = false;
            int dmg = calcDamage(player.getDmg(), player.getAtk(), gegner.getDef(), gegnerBlockt);
            gegner.setHp(gegner.getHp() - dmg);
            sb.append("Du greifst an und verursachst ").append(dmg).append(" Schaden. ");
        } else if ("2".equals(input)) {
            spielerBlockt = true;
            sb.append("Du gehst in Deckung (Block). ");
        } else {
            sb.append("Unbekannte Aktion. ");
        }

        // Gegnerzug (falls er noch lebt)
        if (gegner.getHp() > 0) {
            boolean enemyBlocks = random.nextDouble() < 0.35;
            gegnerBlockt = enemyBlocks;
            if (enemyBlocks) {
                sb.append(gegner.getName()).append(" blockt. ");
            } else {
                int edmg = calcDamage(gegner.getDmg(), gegner.getAtk(), player.getDef(), spielerBlockt);
                player.setHp(player.getHp() - edmg);
                sb.append(gegner.getName()).append(" greift an und verursacht ").append(edmg).append(" Schaden. ");
            }
        }

        if (gegner.getHp() <= 0) {
            sb.append("Der Gegner ist besiegt!");
        }
        if (player.getHp() <= 0) {
            sb.append("Du wurdest besiegt!");
        }

        return sb.toString().trim();
    }

    public boolean istKampfVorbei() {
        return player.getHp() <= 0 || gegner.getHp() <= 0;
    }

    private int calcDamage(int baseDmg, double atk, double def, boolean defenderBlocks) {
        double mult = atk / Math.max(0.1, def);
        double val = baseDmg * mult;
        if (defenderBlocks) val *= 0.5;
        int dmg = (int)Math.round(val);
        return Math.max(1, dmg);
    }
}
