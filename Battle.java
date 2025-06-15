import java.util.Random;

public class Battle {
    private Player player;
    private Gegner gegner;
    private boolean spielerBlockt = false;
    private boolean gegnerBlockt = false;
    private boolean spielerAmZug = true;
    private Random random = new Random();

    public Battle(Player player, Gegner gegner) {
        this.player = player;
        this.gegner = gegner;
    }

    public String getAllowedActions() {
        return "1 (Angriff), 2 (Blocken)";
    }

    public String spielerZug(int aktion) {
        StringBuilder sb = new StringBuilder();
        if (!spielerAmZug) {
            return "Es ist nicht dein Zug!";
        }

        if (aktion == 1) { // Angriff
            if (gegnerBlockt) {
                int schaden = (int)((player.getDmg() * player.getAtk()) / (gegner.getDef() * 2));
                gegner.setHp(gegner.getHp() - schaden);
                sb.append("Du greifst an, aber der Gegner blockt! Schaden: ").append(schaden).append("\n");
            } else {
                int schaden = (int)(player.getDmg() * player.getAtk() / gegner.getDef());
                gegner.setHp(gegner.getHp() - schaden);
                sb.append("Du greifst an und verursachst ").append(schaden).append(" Schaden.\n");
            }
            spielerBlockt = false;
        } else if (aktion == 2) { // Blocken
            spielerBlockt = true;
            sb.append("Du bereitest dich auf den nächsten Angriff vor (Block).\n");
        } else {
            sb.append("Ungültige Aktion.\n");
            return sb.toString();
        }

        if (gegner.getHp() <= 0) {
            sb.append("Du hast den Gegner besiegt!");
            return sb.toString();
        }

        // Gegnerzug
        int gegnerAktion = random.nextInt(2) + 1; // 1=Angriff, 2=Blocken
        if (gegnerAktion == 1) {
            if (spielerBlockt) {
                int schaden = (int)((gegner.getDmg() * gegner.getAtk()) / (player.getDef() * 2));
                player.setHp(player.getHp() - schaden);
                sb.append("Der Gegner greift an, du blockst! Schaden: ").append(schaden).append("\n");
            } else {
                int schaden = (int)(gegner.getDmg() * gegner.getAtk() / player.getDef());
                player.setHp(player.getHp() - schaden);
                sb.append("Der Gegner greift an und verursacht ").append(schaden).append(" Schaden.\n");
            }
            gegnerBlockt = false;
        } else {
            gegnerBlockt = true;
            sb.append("Der Gegner bereitet sich auf den nächsten Angriff vor (Block).\n");
        }

        if (player.getHp() <= 0) {
            sb.append("Du wurdest besiegt!");
        }

        spielerAmZug = true; // Immer Spieler am Zug in diesem Modell

        return sb.toString();
    }

    public boolean istKampfVorbei() {
        return player.getHp() <= 0 || gegner.getHp() <= 0;
    }
}
