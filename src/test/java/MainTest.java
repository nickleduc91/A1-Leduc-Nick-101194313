import static org.junit.jupiter.api.Assertions.*;

import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

class MainTest {

    // RESP-01 Tests
    @Test
    @DisplayName("Ensure the deck contains the proper number of cards")
    void RESP_01_Test_01() {
        Game game = new Game();
        game.initializeDecks();
        int adventureDeckSize = game.getAdventureDeckSize();
        int eventDeckSize = game.getEventDeckSize();
        assertEquals(100, adventureDeckSize);
        assertEquals(17, eventDeckSize);
    }

    @Test
    @DisplayName("Ensure the Adventure deck contains the proper Weapon cards")
    void RESP_01_Test_02() {
        Game game = new Game();
        game.initializeDecks();

        int adventureDeckSize = game.getAdventureDeckSize();
        Dictionary<CardType, Integer> weaponValues= new Hashtable<>();

        int[] expectedWeaponCounts = {6, 12, 16, 8, 6, 2};
        CardType[] weaponCards = {CardType.DAGGER, CardType.HORSE, CardType.SWORD, CardType.BATTLE_AXE, CardType.LANCE, CardType.EXCALIBUR};

        for(int i = 0; i < adventureDeckSize; i++) {
            AdventureCard card = game.drawAdventureCard();
            CardType type = card.getType();

            if (card.getType().isWeapon()) {

                if (weaponValues.get(type) == null) {
                    weaponValues.put(type, 0);
                }
                weaponValues.put(type, weaponValues.get(type) + 1);
            }
        }

        for(int i = 0; i < weaponCards.length; i++) {
            CardType type = weaponCards[i];
            int expectedCount = expectedWeaponCounts[i];
            assertEquals(expectedCount, weaponValues.get(type));
        }
    }

    @Test
    @DisplayName("Ensure the Adventure deck contains the proper Foe cards")
    void RESP_01_Test_03() {
        Game game = new Game();
        game.initializeDecks();

        int adventureDeckSize = game.getAdventureDeckSize();
        Dictionary<CardType, Integer> foeValues= new Hashtable<>();

        int[] expectedFoeCounts = {8, 7, 8, 7, 7, 4, 4, 2, 2, 1};
        CardType[] foeCards = {CardType.F5, CardType.F10, CardType.F15, CardType.F20, CardType.F25, CardType.F30, CardType.F35, CardType.F40, CardType.F50, CardType.F70};

        for(int i = 0; i < adventureDeckSize; i++) {
            AdventureCard card = game.drawAdventureCard();
            CardType type = card.getType();

            if (card.getType().isFoe()) {

                if (foeValues.get(type) == null) {
                    foeValues.put(type, 0);
                }
                foeValues.put(type, foeValues.get(type) + 1);
            }
        }

        for(int i = 0; i < foeCards.length; i++) {
            CardType type = foeCards[i];
            int expectedCount = expectedFoeCounts[i];
            assertEquals(expectedCount, foeValues.get(type));
        }

    }

    @Test
    @DisplayName("Ensure the Event deck contains the proper Quest cards")
    void RESP_01_Test_04() {
        Game game = new Game();
        game.initializeDecks();

        int eventDeckSize = game.getEventDeckSize();
        Dictionary<CardType, Integer> questValues= new Hashtable<>();

        int[] expectedQuestCounts = {3, 4, 3, 2};
        CardType[] questCards = {CardType.Q2, CardType.Q3, CardType.Q4, CardType.Q5};

        for(int i = 0; i < eventDeckSize; i++) {
            EventCard card = game.drawEventCard();
            CardType type = card.getType();

            if (card.getType().isQuest()) {

                if (questValues.get(type) == null) {
                    questValues.put(type, 0);
                }
                questValues.put(type, questValues.get(type) + 1);
            }
        }

        for(int i = 0; i < questCards.length; i++) {
            CardType type = questCards[i];
            int expectedCount = expectedQuestCounts[i];
            assertEquals(expectedCount, questValues.get(type));
        }

    }

    @Test
    @DisplayName("Ensure the Event deck contains the proper E cards")
    void RESP_01_Test_05() {
        Game game = new Game();
        game.initializeDecks();

        int eventDeckSize = game.getEventDeckSize();
        Dictionary<CardType, Integer> eventValues= new Hashtable<>();

        int[] expectedEventCounts = {1, 2, 2};
        CardType[] eventCards = {CardType.PLAGUE, CardType.QUEENS_FAVOR, CardType.PROSPERITY};

        for(int i = 0; i < eventDeckSize; i++) {
            EventCard card = game.drawEventCard();
            CardType type = card.getType();

            if (card.getType().isEvent()) {

                if (eventValues.get(type) == null) {
                    eventValues.put(type, 0);
                }
                eventValues.put(type, eventValues.get(type) + 1);
            }
        }

        for(int i = 0; i < eventCards.length; i++) {
            CardType type = eventCards[i];
            int expectedCount = expectedEventCounts[i];
            assertEquals(expectedCount, eventValues.get(type));
        }

    }

    // RESP-02 Tests
    @Test
    @DisplayName("Ensure the Adventure Deck is shuffled properly")
    void RESP_02_Test_01() {
        Game game = new Game();
        game.initializeDecks();

        Deck<AdventureCard> original = new Deck<>();
        original.setDeck(game.getAdventureDeck().getCards());
        game.shuffleAdventureDeck();
        Deck<AdventureCard> shuffled = game.getAdventureDeck();

        assertNotEquals(original, shuffled);
        assertTrue(shuffled.getCards().containsAll(original.getCards()));
        assertEquals(original.getSize(), shuffled.getSize());
    }

    @Test
    @DisplayName("Ensure the Event Deck is shuffled properly")
    void RESP_02_Test_02() {
        Game game = new Game();
        game.initializeDecks();

        Deck<EventCard> original = new Deck<>();
        original.setDeck(game.getEventDeck().getCards());
        game.shuffleEventDeck();
        Deck<EventCard> shuffled = game.getEventDeck();

        assertNotEquals(original, shuffled);
        assertTrue(shuffled.getCards().containsAll(original.getCards()));
        assertEquals(original.getSize(), shuffled.getSize());
    }

    // RESP-03 TESTS
    @Test
    @DisplayName("Ensure each player gets 12 adventure cards and the Adventure Deck size is now equal to 52 ( 100 - (12*4) )")
    void RESP_03_TEST_01() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        for (int i = 0;  i < 4; i++) {
            Player player = game.getPlayer(i);
            int handSize = player.getHandSize();
            assertEquals(12, handSize);
        }

        assertEquals(52, game.getAdventureDeckSize());
    }

    // RESP-04 Tests
    @Test
    @DisplayName("Ensure the game identifies a winner when a player has exactly 7 shields")
    void RESP_04_Test_01() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        p1.addShields(7);
        p2.addShields(0);
        p3.addShields(6);
        p4.addShields(5);
        boolean hasWinner = game.playTurn();

        assertTrue(hasWinner);
        List<Player> winners = game.getWinners();
        assertEquals(1, winners.size());
        assertTrue(winners.contains(p1));
    }

    @Test
    @DisplayName("Ensure the game identifies multiple winners when more than one player has 7 shields")
    void RESP_04_Test_02() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        p1.addShields(4);
        p2.addShields(8);
        p3.addShields(7);
        p4.addShields(0);
        boolean hasWinner = game.playTurn();

        assertTrue(hasWinner);
        List<Player> winners = game.getWinners();
        assertEquals(2, winners.size());
        assertTrue(winners.contains(p2));
        assertTrue(winners.contains(p3));
    }

    @Test
    @DisplayName("Ensure the game identifies no winners after a turn if all players have <= 7 shields")
    void RESP_04_Test_03() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        p1.addShields(2);
        p2.addShields(0);
        p3.addShields(6);
        p4.addShields(5);
        boolean hasWinner = game.playTurn();

        assertFalse(hasWinner);
        List<Player> winners = game.getWinners();
        assertEquals(0, winners.size());
    }

}