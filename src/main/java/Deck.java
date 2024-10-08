import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck <T> {

    private List<T> cards;
    private List<T> discardPile;

    public Deck() {
        cards = new ArrayList<>();
        discardPile = new ArrayList<>();
    }

    public void addCard(T card) {
        cards.add(card);
    }

    public T drawCard() {
        if (!cards.isEmpty()) {
            return cards.removeFirst();
        }
        cards.addAll(discardPile);
        shuffleCards();
        discardPile.clear();
        return cards.removeFirst();
    }

    public void setDeck(List<T> newCards) { cards = newCards; }

    public int getSize() { return cards.size(); }

    public void shuffleCards() { Collections.shuffle(cards); };

    public List<T> getCards() { return cards; }
}
