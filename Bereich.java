public abstract class Bereich {
    protected Gegner gegner;

    public Bereich() {}

    public void setGegner(Gegner gegner) {
        this.gegner = gegner;
    }

    public Gegner getGegner() {
        return gegner;
    }

    public abstract boolean isErlaubteEingabe(String input);
    public abstract String getErlaubteEingaben();
    public abstract void handleInput(String input, Game model, GameView view);

    protected void starteKampf(Game model, GameView view) {
        Gegner gegner = this.gegner;
        Player spieler = model.getPlayer();
        model.setImKampf(true);
        model.setSpielerBlockt(false);
        model.setGegnerBlockt(false);
        view.updateVerlauf("Ein Gegner erscheint: " + gegner.getName() + "! Der Kampf beginnt.");
        view.updateOptionen("1 - Angriff, 2 - Blocken");
    }

    protected void kampfInput(String input, Game model, GameView view) {
        Player spieler = model.getPlayer();
        Gegner gegner = this.gegner;

        if (!(input.equals("1") || input.equals("2"))) {
            view.updateVerlauf("Nur 1 (Angriff) oder 2 (Blocken) erlaubt.");
            return;
        }

        if (input.equals("1")) {
            int schaden = (int)((spieler.getAtk() * spieler.getDmg()) / gegner.getDef());
            gegner.setHp(gegner.getHp() - schaden);
            view.updateVerlauf("Du greifst an und verursachst " + schaden + " Schaden!");
        } else {
            model.setSpielerBlockt(true);
            view.updateVerlauf("Du gehst in Blockhaltung.");
        }

        if (gegner.getHp() <= 0) {
            view.updateVerlauf("Du hast den Gegner besiegt!");
            model.setImKampf(false);
            this.gegner = null;
            model.setSpielerBlockt(false);
            model.setGegnerBlockt(false);
            return;
        }

        boolean gegnerBlockt = Math.random() < 0.5;
        model.setGegnerBlockt(gegnerBlockt);
        if (gegnerBlockt) {
            view.updateVerlauf("Der Gegner blockt.");
        } else {
            int gegnerSchaden = (int)((gegner.getAtk() * gegner.getDmg()) / spieler.getDef());
            if (model.isSpielerBlockt()) gegnerSchaden /= 2;
            spieler.setHp(spieler.getHp() - gegnerSchaden);
            view.updateVerlauf("Der Gegner greift an und verursacht " + gegnerSchaden + " Schaden!");
        }

        model.setSpielerBlockt(false);

        if (spieler.getHp() <= 0) {
            view.updateVerlauf("Du wurdest besiegt!");
            model.setImKampf(false);
        }
    }
}