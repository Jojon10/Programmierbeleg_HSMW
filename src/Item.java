public abstract class Item {
    protected String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Wendet die Item-Wirkung auf den Spieler an und liefert einen kurzen Beschreibungstext
     * (z. B. für den Verlauf im UI).
     */
    public abstract String applyTo(Player player);
}
