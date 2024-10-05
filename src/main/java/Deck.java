import java.util.ArrayList;
import java.util.List;

public class Deck <T> {

    private List<T> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public void addCard(T card) {
        cards.add(card);
    }

    public T drawCard() {
        if (!cards.isEmpty()) {
            return cards.removeFirst();
        }
        return null;
    }

    public int getSize() { return cards.size(); }

    public List<T> getCards() { return null; }
}
