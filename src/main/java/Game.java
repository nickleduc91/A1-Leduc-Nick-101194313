import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Deck<EventCard> eventDeck;
    private Deck<AdventureCard> adventureDeck;
    private final List<Player> players;
    private int currentPlayerIndex;

    public Game() {
        eventDeck = new Deck<>();
        adventureDeck = new Deck<>();
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }

    public void initializeDecks() {
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
        shuffleAdventureDeck();
        shuffleEventDeck();
    }

    public void initializePlayers() {
        for(int i = 0; i < 4; i++) {
            Player player = new Player(i);
            players.add(player);

            // Deal 12 adventure cards
            for (int j = 0; j < 12; j++) {
                player.addCardToHand(drawAdventureCard());
            }
        }
    }

    public boolean playTurn() {
        if (hasWinner()) {
            return true;
        }

        updateNextPlayer();
        return false;
    }

    public void updateNextPlayer() {
        if(currentPlayerIndex == 3) {
            currentPlayerIndex = 0;
        } else {
            currentPlayerIndex += 1;
        }
    }

    public boolean hasWinner() {
        for (Player player : players) {
            if (player.getShields() >= 7) {
                return true;
            }
        }
        return false;
    }

    public List<Player> getWinners() {
        List<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.getShields() >= 7) {
                winners.add(player);
            }
        }
        return winners;
    }

    public Player getCurrentPlayer() { return players.get(currentPlayerIndex); }
    public Player getPlayer(int id) { return players.get(id); }

    public AdventureCard drawAdventureCard() {
        return adventureDeck.drawCard();
    }
    public EventCard drawEventCard() {
        return eventDeck.drawCard();
    }

    public Deck<EventCard> getEventDeck() { return eventDeck; }
    public Deck<AdventureCard> getAdventureDeck() { return adventureDeck; }

    public void shuffleAdventureDeck() { adventureDeck.shuffleCards(); }
    public void shuffleEventDeck() { eventDeck.shuffleCards(); }

    public int getEventDeckSize() { return eventDeck.getSize(); }
    public int getAdventureDeckSize() { return adventureDeck.getSize(); }

    public View getView() { return new View(); }
}
