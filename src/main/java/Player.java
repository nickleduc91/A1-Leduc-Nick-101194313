import Cards.AdventureCard;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int id;
    private List<AdventureCard> hand;
    private int shields;

    public Player(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
        this.shields = 0;
    }

    public int addCardToHand(AdventureCard card) {
        hand.add(card);
        return -1;
    }

    public String toString() {
        return "Player ID: P" + (id + 1);
    }

    public void addShields(int shields) { this.shields += shields; }
    public int getShields() { return shields; }
    public int getHandSize() { return hand.size(); }
    public List<AdventureCard> getHand() { return hand; }

}
