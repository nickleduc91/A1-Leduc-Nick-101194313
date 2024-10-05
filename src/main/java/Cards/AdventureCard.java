package Cards;
import Enums.CardType;

public class AdventureCard {

    private final CardType cardType;
    private final int cardValue;

    public AdventureCard(CardType cardType) {
        this.cardType = cardType;
        this.cardValue = cardType.getValue();
    }

    public CardType getType() { return cardType; }

}
