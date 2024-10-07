import Cards.AdventureCard;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int id;
    private List<AdventureCard> hand;

    public Player(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
    }

    public void addCardToHand(AdventureCard card) {
        hand.add(card);
    }

    public void addShields(int shields) {

    }

    public int getHandSize() { return hand.size(); }

}
