import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;

public class Game {

    private Deck<EventCard> eventDeck;
    private Deck<AdventureCard> adventureDeck;

    public Game() {
        eventDeck = new Deck<>();
        adventureDeck = new Deck<>();

        int[] expectedFoeCounts = {8, 7, 8, 7, 7, 4, 4, 2, 2, 1};
        CardType[] foeCards = {CardType.F5, CardType.F10, CardType.F15, CardType.F20, CardType.F25, CardType.F30, CardType.F35, CardType.F40, CardType.F50, CardType.F70};

        int[] expectedWeaponCounts = {6, 12, 16, 8, 6, 2};
        CardType[] weaponCards = {CardType.DAGGER, CardType.HORSE, CardType.SWORD, CardType.BATTLE_AXE, CardType.LANCE, CardType.EXCALIBUR};

        for(int i = 0; i < foeCards.length; i++) {
            for(int j = 0; j < expectedFoeCounts[i]; j++) {
                AdventureCard card = new AdventureCard(foeCards[i]);
                adventureDeck.addCard(card);
            }
        }
        for(int i = 0; i < weaponCards.length; i++) {
            for(int j = 0; j < expectedWeaponCounts[i]; j++) {
                AdventureCard card = new AdventureCard(weaponCards[i]);
                adventureDeck.addCard(card);
            }
        }

        int[] expectedQuestCounts = {3, 4, 3, 2};
        CardType[] questCards = {CardType.Q2, CardType.Q3, CardType.Q4, CardType.Q5};

        int[] expectedEventCounts = {1, 2, 2};
        CardType[] eventCards = {CardType.PLAGUE, CardType.QUEENS_FAVOR, CardType.PROSPERITY};

        for(int i = 0; i < questCards.length; i++) {
            for(int j = 0; j < expectedQuestCounts[i]; j++) {
                EventCard card = new EventCard(questCards[i]);
                eventDeck.addCard(card);
            }
        }
        for(int i = 0; i < eventCards.length; i++) {
            for(int j = 0; j < expectedEventCounts[i]; j++) {
                EventCard card = new EventCard(eventCards[i]);
                eventDeck.addCard(card);
            }
        }
    }


    public void initializeDecks() {

    }

    public AdventureCard drawAdventureCard() {
        return adventureDeck.drawCard();
    }
    public EventCard drawEventCard() {
        return eventDeck.drawCard();
    }

    public int getEventDeckSize() { return eventDeck.getSize(); }
    public int getAdventureDeckSize() { return adventureDeck.getSize(); }
}
