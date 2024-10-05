import static org.junit.jupiter.api.Assertions.*;

import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Dictionary;
import java.util.Hashtable;

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

}