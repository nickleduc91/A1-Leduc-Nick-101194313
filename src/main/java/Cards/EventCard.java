package Cards;

import Enums.CardType;

public class EventCard {

    private final CardType cardType;
    private final int cardValue;

    public EventCard(CardType cardType) {
        this.cardType = cardType;
        this.cardValue = cardType.getValue();
    }

    public CardType getType() { return cardType; }
}
