import static org.junit.jupiter.api.Assertions.*;

import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

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
        Controller controller = new Controller(game);

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        p1.addShields(7);
        p2.addShields(0);
        p3.addShields(6);
        p4.addShields(5);
        boolean hasWinner = controller.playTurn();

        assertTrue(hasWinner);
        List<Player> winners = game.getWinners();
        assertEquals(1, winners.size());
        assertTrue(winners.contains(p1));
    }

    @Test
    @DisplayName("Ensure the game identifies multiple winners when more than one player has 7 shields")
    void RESP_04_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        p1.addShields(4);
        p2.addShields(8);
        p3.addShields(7);
        p4.addShields(0);
        boolean hasWinner = controller.playTurn();

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
        Controller controller = new Controller(game);

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        p1.addShields(2);
        p2.addShields(0);
        p3.addShields(6);
        p4.addShields(5);
        boolean hasWinner = controller.playTurn();

        assertFalse(hasWinner);
        List<Player> winners = game.getWinners();
        assertEquals(0, winners.size());
    }

    // RESP-05 Tests
    @Test
    @DisplayName("Ensure P1 starts the game and is followed up by P2")
    void RESP_05_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);

        assertEquals(p1, game.getCurrentPlayer());
        controller.playTurn();
        assertEquals(p2, game.getCurrentPlayer());

    }

    @Test
    @DisplayName("Ensure P2 starts the game and is followed up by P3")
    void RESP_05_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);

        controller.playTurn();
        assertEquals(p2, game.getCurrentPlayer());
        controller.playTurn();
        assertEquals(p3, game.getCurrentPlayer());

    }

    @Test
    @DisplayName("Ensure P3 starts the game and is followed up by P4")
    void RESP_05_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        controller.playTurn();
        controller.playTurn();
        assertEquals(p3, game.getCurrentPlayer());
        controller.playTurn();
        assertEquals(p4, game.getCurrentPlayer());

    }

    @Test
    @DisplayName("Ensure P4 starts the game and is followed up by P1")
    void RESP_05_Test_04() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p1 = game.getPlayer(0);
        Player p4 = game.getPlayer(3);

        controller.playTurn();
        controller.playTurn();
        controller.playTurn();
        assertEquals(p4, game.getCurrentPlayer());
        controller.playTurn();
        assertEquals(p1, game.getCurrentPlayer());

    }

    //RESP-06 Tests
    @Test
    @DisplayName("Ensure the game displays the id of the winning player when there are real winners")
    void RESP_06_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        p1.addShields(4);
        p2.addShields(8);
        p3.addShields(7);
        p4.addShields(0);
        boolean hasWinner = controller.playTurn();
        assertTrue(hasWinner);

        StringWriter output = new StringWriter();
        game.getView().displayWinners(new PrintWriter(output), game.getWinners());
        String result = output.toString();

        assertTrue(result.contains("Winning Players:"));
        assertTrue(result.contains(p2.toString()));
        assertTrue(result.contains(p3.toString()));
    }

    //RESP-07 Tests
    @Test
    @DisplayName("There are no winners and the game indicates it is the turn of the next player")
    void RESP_07_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        p1.addShields(4);
        p2.addShields(1);
        p3.addShields(2);
        p4.addShields(0);
        boolean hasWinner = controller.playTurn();
        assertFalse(hasWinner);

        StringWriter output = new StringWriter();
        game.getView().displayCurrentPlayer(new PrintWriter(output), game.getCurrentPlayer());
        String result = output.toString();

        assertTrue(result.contains("Current Turn - " + p2.toString()));

    }

    //RESP-08 Tests
    @Test
    @DisplayName("There are no winners and the game displays the hand of the current player")
    void RESP_08_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p2 = game.getPlayer(1);
        boolean hasWinner = controller.playTurn();
        assertFalse(hasWinner);

        StringWriter output = new StringWriter();
        game.getView().displayCurrentPlayerHand(new PrintWriter(output), game.getCurrentPlayer());
        String result = output.toString();

        assertTrue(result.contains("HAND:"));

        for (AdventureCard card : p2.getHand()) {
            String expectedOutput = card.toString();
            assertTrue(result.contains(expectedOutput));
        }
    }

    @Test
    @DisplayName("The Game displays the current player's hand with only foe cards in increasing order")
    void RESP_08_Test_02() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getPlayer(0);

        p1.getHand().clear();
        p1.addCardToHand(new AdventureCard(CardType.F25));
        p1.addCardToHand(new AdventureCard(CardType.F30));
        p1.addCardToHand(new AdventureCard(CardType.F35));

        StringWriter output = new StringWriter();
        game.getView().displayCurrentPlayerHand(new PrintWriter(output), game.getCurrentPlayer());
        String result = output.toString();


        assertTrue(result.contains("HAND:"));
        assertTrue(result.indexOf("F25") < result.indexOf("F30"));
        assertTrue(result.indexOf("F30") < result.indexOf("F35"));
    }

    @Test
    @DisplayName("The Game displays the current player's hand with only weapon cards sorted correctly, where Sword appears before Horse")
    void RESP_08_Test_03() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getPlayer(0);

        p1.getHand().clear();
        p1.addCardToHand(new AdventureCard(CardType.EXCALIBUR));
        p1.addCardToHand(new AdventureCard(CardType.HORSE));
        p1.addCardToHand(new AdventureCard(CardType.SWORD));
        p1.addCardToHand(new AdventureCard(CardType.LANCE));

        StringWriter output = new StringWriter();
        game.getView().displayCurrentPlayerHand(new PrintWriter(output), game.getCurrentPlayer());
        String result = output.toString();

        assertTrue(result.contains("HAND:"));
        assertTrue(result.indexOf("S10") < result.indexOf("H10"));
        assertTrue(result.indexOf("L20") > result.indexOf("H10"));
        assertTrue(result.indexOf("L20") < result.indexOf("E30"));
    }

    @Test
    @DisplayName("The Game displays the current player's hand with a mix of foe and weapon cards")
    void RESP_08_Test_04() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getPlayer(0);

        p1.getHand().clear();
        p1.addCardToHand(new AdventureCard(CardType.F5));
        p1.addCardToHand(new AdventureCard(CardType.F70));
        p1.addCardToHand(new AdventureCard(CardType.SWORD));
        p1.addCardToHand(new AdventureCard(CardType.HORSE));

        StringWriter output = new StringWriter();
        game.getView().displayCurrentPlayerHand(new PrintWriter(output), game.getCurrentPlayer());
        String result = output.toString();

        assertTrue(result.contains("HAND:"));
        assertTrue(result.indexOf("F5") < result.indexOf("F70"));
        // Sword should be before the horse
        assertTrue(result.indexOf("S10") < result.indexOf("H10"));
        assertTrue(result.indexOf("F70") < result.indexOf("S10"));
    }

    //RESP-09 Tests
    @Test
    @DisplayName("The game properly indicates that the turn of the current player (p1) has ended and clears the hotseat")
    void RESP_09_Test_01() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getPlayer(0);

        StringWriter output = new StringWriter();
        String input = "\n";
        game.getView().endTurn(new PrintWriter(output), new Scanner(input), game.getCurrentPlayer());
        String result = output.toString();

        assertTrue(result.contains("The turn of " + p1 + " has ended"));
        assertTrue(result.contains("Press 'ENTER' to confirm the end of your turn"));
        assertTrue(result.contains("\n"));
    }

    @Test
    @DisplayName("The game properly indicates that the turn of the current player (p2) has ended and clears the display")
    void RESP_09_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p2 = game.getPlayer(1);
        controller.playTurn();

        StringWriter output = new StringWriter();
        String input = "\n";
        game.getView().endTurn(new PrintWriter(output), new Scanner(input), game.getCurrentPlayer());
        String result = output.toString();

        assertTrue(result.contains("The turn of " + p2 + " has ended"));
        assertTrue(result.contains("Press 'ENTER' to confirm the end of your turn"));
        assertTrue(result.contains("\n"));
    }

    @Test
    @DisplayName("The game properly indicates that the turn of the current player (p3) has ended and clears the display")
    void RESP_09_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p3 = game.getPlayer(2);
        controller.playTurn();
        controller.playTurn();

        StringWriter output = new StringWriter();
        String input = "\n";
        game.getView().endTurn(new PrintWriter(output), new Scanner(input), game.getCurrentPlayer());
        String result = output.toString();

        assertTrue(result.contains("The turn of " + p3 + " has ended"));
        assertTrue(result.contains("Press 'ENTER' to confirm the end of your turn"));
        assertTrue(result.contains("\n"));
    }

    @Test
    @DisplayName("The game properly indicates that the turn of the current player (p4) has ended and clears the display")
    void RESP_09_Test_04() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p4 = game.getPlayer(3);
        controller.playTurn();
        controller.playTurn();
        controller.playTurn();

        StringWriter output = new StringWriter();
        String input = "\n";
        game.getView().endTurn(new PrintWriter(output), new Scanner(input), game.getCurrentPlayer());
        String result = output.toString();

        assertTrue(result.contains("The turn of " + p4 + " has ended"));
        assertTrue(result.contains("Press 'ENTER' to confirm the end of your turn"));
        assertTrue(result.contains("\n"));
    }

    //RESP-10 Tests
    @Test
    @DisplayName("The game draws an Event Card but there are none left, and uses the shuffled discard deck instead")
    void RESP_10_Test_01() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        int initialDeckSize = game.getEventDeckSize();

        // Remove all cards from the Event Deck and place them in the discard deck
        for (int i = 0; i < initialDeckSize; i++) {
            EventCard drawnCard = game.drawEventCard();
            game.getEventDeck().addToDiscardPile(drawnCard);
        }

        assertEquals(0, game.getEventDeckSize());
        assertEquals(initialDeckSize, game.getEventDeck().getDiscardPileSize());

        // Trigger the re-shuffle of the discard pile
        game.drawEventCard();

        assertEquals(initialDeckSize - 1, game.getEventDeckSize());
        assertTrue(game.getEventDeck().getDiscardPile().isEmpty());
    }

    @Test
    @DisplayName("The discard pile is shuffled before being reused as the Event deck")
    void RESP_10_Test_02() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        int initialDeckSize = game.getEventDeckSize();

        // Remove all cards from the Event Deck and place them in the discard deck
        for (int i = 0; i < initialDeckSize; i++) {
            EventCard drawnCard = game.drawEventCard();
            game.getEventDeck().addToDiscardPile(drawnCard);
        }

        assertEquals(0, game.getEventDeckSize());
        assertEquals(initialDeckSize, game.getEventDeck().getDiscardPileSize());

        // Check that the first card of the discard pile is not the first card that appears in the Event deck after drawing from empty deck
        EventCard originalDiscardCard = new EventCard(game.getEventDeck().getDiscardPile().getFirst().getType());
        // Trigger the re-shuffle of the discard pile
        game.drawEventCard();
        assertNotEquals(originalDiscardCard, game.getEventDeck().getCards().getFirst());

        assertEquals(initialDeckSize - 1, game.getEventDeckSize());
        assertTrue(game.getEventDeck().getDiscardPile().isEmpty());
    }

    //RESP-11 Tests
    @Test
    @DisplayName("The game displays the proper drawn Event Plague Card")
    void RESP_11_Test_01() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        // Set the first card to be drawn as a plague card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.PLAGUE));
        EventCard card = game.drawEventCard();
        StringWriter output = new StringWriter();
        game.getView().displayEventCard(new PrintWriter(output), card);
        String result = output.toString();
        assertTrue(result.contains("Drawn Card: PLAGUE"));
    }

    @Test
    @DisplayName("The game displays the proper drawn Event Q2 Card")
    void RESP_11_Test_02() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        // Set the first card to be drawn as a plague card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.Q2));
        EventCard card = game.drawEventCard();
        StringWriter output = new StringWriter();
        game.getView().displayEventCard(new PrintWriter(output), card);
        String result = output.toString();
        assertTrue(result.contains("Drawn Card: Q2"));
    }

    //RESP-12 Tests
    @Test
    @DisplayName("The game draws a plague card and the effect is carried out to the player who already has more than 2 shields")
    void RESP_12_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        // Set the first card to be drawn as a plague card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.PLAGUE));

        Player p1 = game.getPlayer(0);
        p1.addShields(4);

        int originalDeckCount = game.getEventDeckSize();
        int originalShieldCount = game.getCurrentPlayer().getShields();
        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.PLAGUE, drawnCard.getType());

        controller.handleDrawnECard(drawnCard, new PrintWriter(System.out), new Scanner(System.in));

        assertEquals(originalDeckCount - 1, game.getEventDeckSize());
        assertEquals(originalShieldCount - 2, game.getCurrentPlayer().getShields());
    }

    @Test
    @DisplayName("The game draws a plague card and the effect is carried out to the player who already has less than 2 shields")
    void RESP_12_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        // Set the first card to be drawn as a plague card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.PLAGUE));

        Player p1 = game.getPlayer(0);
        p1.addShields(1);

        int originalDeckCount = game.getEventDeckSize();
        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.PLAGUE, drawnCard.getType());

        controller.handleDrawnECard(drawnCard, new PrintWriter(System.out), new Scanner(System.in));

        assertEquals(originalDeckCount - 1, game.getEventDeckSize());
        assertEquals(0, game.getCurrentPlayer().getShields());
    }

    //RESP-13 Tests
    @Test
    @DisplayName("Game decides how many cards to trim from players hand when hand is size 14")
    void RESP_13_Test_01() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getCurrentPlayer();
        p1.addCardToHand(game.drawAdventureCard());
        int trim = p1.addCardToHand(game.drawAdventureCard());

        assertEquals(14, p1.getHandSize());
        assertEquals(2, trim);
    }

    @Test
    @DisplayName("Game decides how many cards to trim from players hand when hand is less than 12")
    void RESP_13_Test_02() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getCurrentPlayer();
        // Remove cards so the player has a hand of size 10 before adding a card
        p1.getHand().removeFirst();
        p1.getHand().removeFirst();
        int trim = p1.addCardToHand(game.drawAdventureCard());

        assertEquals(11, p1.getHandSize());
        assertEquals(0, trim);
        assertEquals(11, p1.getHandSize());
    }

    //RESP-14 Tests
    @Test
    @DisplayName("For 2 cards to trim, the Game properly displays the hand of that player and then chooses to discard the first card each time")
    void RESP_14_Test_01() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getCurrentPlayer();
        p1.addCardToHand(game.drawAdventureCard());
        int trim = p1.addCardToHand(game.drawAdventureCard());
        StringWriter output = new StringWriter();
        String input = "0\n0\n";
        Scanner scanner = new Scanner(input);

        game.getView().trimCard(new PrintWriter(output), scanner, p1, game.getAdventureDeck(), trim);
        String result = output.toString();

        String expectedSubstring = "A trim is needed for " + p1;
        String[] parts = result.split(expectedSubstring);
        int occurrenceCount = parts.length - 1;
        assertEquals(2, occurrenceCount);

        // Make sure the players hand is displayed
        for(AdventureCard card : p1.getHand()) {
            assertTrue(result.contains(card.getType().getName()));
        }

    }

    @Test
    @DisplayName("For 1 card to trim, the Game properly displays the hand of that player and then chooses to discard the last card")
    void RESP_14_Test_02() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getCurrentPlayer();
        int trim = p1.addCardToHand(game.drawAdventureCard());
        StringWriter output = new StringWriter();
        int lastCardIndex = p1.getHandSize() - 1;
        String input = lastCardIndex + "\n";
        Scanner scanner = new Scanner(input);

        game.getView().trimCard(new PrintWriter(output), scanner, p1, game.getAdventureDeck(), trim);
        String result = output.toString();

        String expectedSubstring = "A trim is needed for " + p1;
        String[] parts = result.split(expectedSubstring);
        int occurrenceCount = parts.length - 1;
        assertEquals(1, occurrenceCount);

        // Make sure the players hand is displayed
        for(AdventureCard card : p1.getHand()) {
            assertTrue(result.contains(card.getType().getName()));
        }
    }

    //RESP-15 Tests
    @Test
    @DisplayName("For 2 cards to trim, the Game properly deletes the first 2 cards from the players hand")
    void RESP_15_Test_01() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getCurrentPlayer();
        p1.addCardToHand(game.drawAdventureCard());
        int trim = p1.addCardToHand(game.drawAdventureCard());
        StringWriter output = new StringWriter();
        String input = "0\n0\n";
        Scanner scanner = new Scanner(input);

        AdventureCard card1 = p1.getHand().getFirst();
        AdventureCard card2 = p1.getHand().get(1);
        int originalDiscardSize = game.getAdventureDeck().getDiscardPileSize();

        game.getView().trimCard(new PrintWriter(output), scanner, p1, game.getAdventureDeck(), trim);
        String result = output.toString();

        String expectedSubstring = "A trim is needed for " + p1;
        String[] parts = result.split(expectedSubstring);
        int occurrenceCount = parts.length - 1;
        assertEquals(2, occurrenceCount);

        // Make sure the players hand is displayed
        for(AdventureCard card : p1.getHand()) {
            assertTrue(result.contains(card.getType().getName()));
        }

        // Make sure the trimmed cards are not in the player's hand and the hand size is proper
        assertTrue(result.contains("The trim for " + p1 + " is complete."));
        assertEquals(12, p1.getHandSize());
        assertFalse(p1.getHand().contains(card1));
        assertFalse(p1.getHand().contains(card2));

        // Assert discard deck now contains the proper size
        assertEquals(originalDiscardSize + 2, game.getAdventureDeck().getDiscardPileSize());
    }

    @Test
    @DisplayName("For 1 card to trim, the Game properly deletes the last cards from the players hand")
    void RESP_15_Test_02() {
        Game game = new Game();
        game.initializeDecks();
        game.initializePlayers();

        Player p1 = game.getCurrentPlayer();
        int trim = p1.addCardToHand(game.drawAdventureCard());
        StringWriter output = new StringWriter();
        int lastCardIndex = p1.getHandSize() - 1;
        String input = lastCardIndex + "\n";
        Scanner scanner = new Scanner(input);

        AdventureCard card1 = p1.getHand().getLast();
        int originalDiscardSize = game.getAdventureDeck().getDiscardPileSize();

        game.getView().trimCard(new PrintWriter(output), scanner, p1, game.getAdventureDeck(), trim);
        String result = output.toString();

        String expectedSubstring = "A trim is needed for " + p1;
        String[] parts = result.split(expectedSubstring);
        int occurrenceCount = parts.length - 1;
        assertEquals(1, occurrenceCount);

        // Make sure the players hand is displayed
        for(AdventureCard card : p1.getHand()) {
            assertTrue(result.contains(card.getType().getName()));
        }

        // Make sure the trimmed cards are not in the player's hand and the hand size is proper
        assertTrue(result.contains("The trim for " + p1 + " is complete."));
        assertEquals(12, p1.getHandSize());
        assertFalse(p1.getHand().contains(card1));

        // Make sure the discard deck now contains the proper size
        assertEquals(originalDiscardSize + 1, game.getAdventureDeck().getDiscardPileSize());
    }

    @Test
    @DisplayName("The game draws a Queen's Favor card and the effect is carried out to the player who has 10 cards thus the trim does not occur")
    void RESP_16_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        // Set the first card to be drawn as a Queens Favor card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.QUEENS_FAVOR));

        Player p1 = game.getCurrentPlayer();
        p1.discard(0, game.getAdventureDeck());
        p1.discard(0, game.getAdventureDeck());

        int originalDeckCount = game.getEventDeckSize();
        int originalHandCount = game.getCurrentPlayer().getHandSize();
        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.QUEENS_FAVOR, drawnCard.getType());

        StringWriter output = new StringWriter();
        String input = "0\n0\n";
        Scanner scanner = new Scanner(input);

        controller.handleDrawnECard(drawnCard, new PrintWriter(output), scanner);
        String result = output.toString();

        assertEquals(originalDeckCount - 1, game.getEventDeckSize());
        assertEquals(originalHandCount + 2, game.getCurrentPlayer().getHandSize());
        assertFalse(result.contains("A trim is needed for " + p1));
    }

    @Test
    @DisplayName("The game draws a Queen's Favor card and the effect is carried out to the player who already has 12 cards and thus a trim occurs")
    void RESP_16_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        // Set the first card to be drawn as a Queens Favor card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.QUEENS_FAVOR));

        Player p1 = game.getCurrentPlayer();

        int originalDeckCount = game.getEventDeckSize();
        int originalHandCount = game.getCurrentPlayer().getHandSize();
        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.QUEENS_FAVOR, drawnCard.getType());

        StringWriter output = new StringWriter();
        String input = "0\n0\n";
        Scanner scanner = new Scanner(input);

        controller.handleDrawnECard(drawnCard, new PrintWriter(output), scanner);
        String result = output.toString();

        assertEquals(originalDeckCount - 1, game.getEventDeckSize());
        assertEquals(originalHandCount, game.getCurrentPlayer().getHandSize());
        assertTrue(result.contains("A trim is needed for " + p1));
    }

    // RESP-17 Tests
    @Test
    @DisplayName("The game draws a Prosperity card and the effect is carried out, no players need to trim since they are all at 10 cards")
    void RESP_17_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        // Set the first card to be drawn as a Prosperity card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.PROSPERITY));

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        // Make sure all players have 10 cards
        p1.discard(0, game.getAdventureDeck());
        p1.discard(0, game.getAdventureDeck());
        p2.discard(0, game.getAdventureDeck());
        p2.discard(0, game.getAdventureDeck());
        p3.discard(0, game.getAdventureDeck());
        p3.discard(0, game.getAdventureDeck());
        p4.discard(0, game.getAdventureDeck());
        p4.discard(0, game.getAdventureDeck());

        int originalDeckCount = game.getEventDeckSize();
        int originalAdventureDeckCount = game.getAdventureDeckSize();

        int p1OriginalHandCount = p1.getHandSize();
        int p2OriginalHandCount = p2.getHandSize();
        int p3OriginalHandCount = p3.getHandSize();
        int p4OriginalHandCount = p4.getHandSize();

        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.PROSPERITY, drawnCard.getType());

        StringWriter output = new StringWriter();
        String input = "\n";
        Scanner scanner = new Scanner(input);

        controller.handleDrawnECard(drawnCard, new PrintWriter(output), scanner);
        String result = output.toString();

        assertEquals(originalDeckCount - 1, game.getEventDeckSize());
        assertEquals(originalAdventureDeckCount - 8, game.getAdventureDeckSize());

        assertEquals(p1OriginalHandCount + 2, p1.getHandSize());
        assertEquals(p2OriginalHandCount + 2, p2.getHandSize());
        assertEquals(p3OriginalHandCount + 2, p3.getHandSize());
        assertEquals(p4OriginalHandCount + 2, p4.getHandSize());

        assertFalse(result.contains("A trim is needed for " + p1));
        assertFalse(result.contains("A trim is needed for " + p2));
        assertFalse(result.contains("A trim is needed for " + p3));
        assertFalse(result.contains("A trim is needed for " + p4));
    }

    @Test
    @DisplayName("The game draws a Prosperity card and the effect is carried out,  players 1 and 4 need to trim since they are all at 12 cards")
    void RESP_17_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        // Set the first card to be drawn as a Prosperity card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.PROSPERITY));

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        // Make sure all players have 10 cards
        p2.discard(0, game.getAdventureDeck());
        p2.discard(0, game.getAdventureDeck());
        p3.discard(0, game.getAdventureDeck());
        p3.discard(0, game.getAdventureDeck());

        int originalDeckCount = game.getEventDeckSize();
        int originalAdventureDeckCount = game.getAdventureDeckSize();

        int p2OriginalHandCount = p2.getHandSize();
        int p3OriginalHandCount = p3.getHandSize();

        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.PROSPERITY, drawnCard.getType());

        StringWriter output = new StringWriter();
        String input = "0\n0\n0\n0\n";
        Scanner scanner = new Scanner(input);

        controller.handleDrawnECard(drawnCard, new PrintWriter(output), scanner);
        String result = output.toString();

        assertEquals(originalDeckCount - 1, game.getEventDeckSize());
        assertEquals(originalAdventureDeckCount - 8, game.getAdventureDeckSize());

        assertEquals(12, p1.getHandSize());
        assertEquals(p2OriginalHandCount + 2, p2.getHandSize());
        assertEquals(p3OriginalHandCount + 2, p3.getHandSize());
        assertEquals(12, p4.getHandSize());

        assertTrue(result.contains("A trim is needed for " + p1));
        assertFalse(result.contains("A trim is needed for " + p2));
        assertFalse(result.contains("A trim is needed for " + p3));
        assertTrue(result.contains("A trim is needed for " + p4));
    }

    //RESP-18 Tests
    @Test
    @DisplayName("The game carries out the event of a Plague card, and then discards it")
    void RESP_18_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        // Set the first card to be drawn as a plague card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.PLAGUE));

        int originalDiscardCount = game.getEventDeck().getDiscardPileSize();
        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.PLAGUE, drawnCard.getType());
        controller.handleDrawnECard(drawnCard, new PrintWriter(System.out), new Scanner(System.in));

        assertEquals(originalDiscardCount + 1, game.getEventDeck().getDiscardPileSize());
        assertEquals(game.getEventDeck().getDiscardPile().getLast(), drawnCard);

    }

    @Test
    @DisplayName("The game carries out the event of a Queens Favor card, and then discards it")
    void RESP_18_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        // Make sure a discard does not occur
        Player p1 = game.getCurrentPlayer();
        p1.discard(0, game.getAdventureDeck());
        p1.discard(0, game.getAdventureDeck());

        // Set the first card to be drawn as a Queens Favor card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.QUEENS_FAVOR));

        int originalDiscardCount = game.getEventDeck().getDiscardPileSize();
        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.QUEENS_FAVOR, drawnCard.getType());
        controller.handleDrawnECard(drawnCard, new PrintWriter(System.out), new Scanner(System.in));

        assertEquals(originalDiscardCount + 1, game.getEventDeck().getDiscardPileSize());
        assertEquals(game.getEventDeck().getDiscardPile().getLast(), drawnCard);

    }

    @Test
    @DisplayName("The game carries out the event of a Prosperity card, and then discards it")
    void RESP_18_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);
        Player p3 = game.getPlayer(2);
        Player p4 = game.getPlayer(3);

        // Make sure all players have 10 cards so no trim occurs
        p1.discard(0, game.getAdventureDeck());
        p1.discard(0, game.getAdventureDeck());
        p2.discard(0, game.getAdventureDeck());
        p2.discard(0, game.getAdventureDeck());
        p3.discard(0, game.getAdventureDeck());
        p3.discard(0, game.getAdventureDeck());
        p4.discard(0, game.getAdventureDeck());
        p4.discard(0, game.getAdventureDeck());

        // Set the first card to be drawn as a Prosperity card
        game.getEventDeck().getCards().set(0, new EventCard(CardType.PROSPERITY));

        int originalDiscardCount = game.getEventDeck().getDiscardPileSize();
        EventCard drawnCard = game.drawEventCard();
        assertEquals(CardType.PROSPERITY, drawnCard.getType());
        controller.handleDrawnECard(drawnCard, new PrintWriter(System.out), new Scanner(System.in));

        assertEquals(originalDiscardCount + 1, game.getEventDeck().getDiscardPileSize());
        assertEquals(game.getEventDeck().getDiscardPile().getLast(), drawnCard);

    }

    //RESP-19 Tests
    @Test
    @DisplayName("The game checks for a sponsor and the current player (p1) chooses to be the sponsor")
    void RESP_19_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        String result = output.toString();
        assertEquals(sponsorIndex, game.getCurrentPlayer().getIndex());
        assertTrue(result.contains(game.getCurrentPlayer().toString() + " is the sponsor"));
    }

    @Test
    @DisplayName("The game checks for a sponsor and the player after the current one (p2) chooses to be the sponsor")
    void RESP_19_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "no\nyes\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        String result = output.toString();

        int currentPlayerId = game.getCurrentPlayer().getIndex();
        int expectedSponsorId = (currentPlayerId + 1) % 4;

        assertEquals(sponsorIndex, expectedSponsorId);
        assertTrue(result.contains(game.getPlayer(expectedSponsorId).toString() + " is the sponsor"));
    }

    @Test
    @DisplayName("The game checks for a sponsor and the player 2 positions after the current one (p3) chooses to be the sponsor")
    void RESP_19_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "no\nno\nyes\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        String result = output.toString();

        int currentPlayerId = game.getCurrentPlayer().getIndex();
        int expectedSponsorId = (currentPlayerId + 2) % 4;

        assertEquals(sponsorIndex, expectedSponsorId);
        assertTrue(result.contains(game.getPlayer(expectedSponsorId).toString() + " is the sponsor"));
    }

    @Test
    @DisplayName("The game checks for a sponsor and the player 3 positions after the current one (p4) chooses to be the sponsor")
    void RESP_19_Test_04() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "no\nno\nno\nyes\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        String result = output.toString();

        int currentPlayerId = game.getCurrentPlayer().getIndex();
        int expectedSponsorId = (currentPlayerId + 3) % 4;

        assertEquals(sponsorIndex, expectedSponsorId);
        assertTrue(result.contains(game.getPlayer(expectedSponsorId).toString() + " is the sponsor"));
    }

    //RESP-20 Tests
    @Test
    @DisplayName("The game checks for a sponsor and nobody accepts, and the drawn Q card is discarded")
    void RESP_20_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "no\nno\nno\nno\n";

        int originalDiscardCount = game.getEventDeck().getDiscardPileSize();
        game.getEventDeck().getCards().set(0, new EventCard(CardType.Q2));
        EventCard drawnCard = game.drawEventCard();

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        if(sponsorIndex == -1) {
            game.getEventDeck().addToDiscardPile(drawnCard);
        }
        String result = output.toString();

        assertEquals(sponsorIndex, -1);
        assertTrue(result.contains("None of the players accepted the quest"));
        assertTrue(game.getEventDeck().getDiscardPile().contains(drawnCard));
        assertEquals(originalDiscardCount + 1, game.getEventDeck().getDiscardPileSize());
    }

    //RESP-21 Tests
    @Test
    @DisplayName("Ensure the sponsors deck is displayed and the sponsor is prompted to either quit or choose a card")
    void RESP_21_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";
        String input2 = "0\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        controller.getView().displayCurrentPlayerHand(new PrintWriter(output), game.getPlayer(sponsorIndex));
        controller.getView().getQuestPosition(new PrintWriter(output), new Scanner(input2));
        String result = output.toString();

        assertTrue(result.contains("HAND:"));
        assertTrue(result.contains("Enter the index of the card in your hand you would like to add to the stage of the quest, or type 'q' to quit building this stage"));
        for (AdventureCard card : game.getPlayer(sponsorIndex).getHand()) {
            String expectedOutput = card.toString();
            assertTrue(result.contains(expectedOutput));
        }

    }

    @Test
    @DisplayName("Ensure the sponsors deck is displayed and the sponsor is prompted to either quit or choose a card, and the sponsor picks the first card")
    void RESP_21_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";
        String input2 = "0\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        controller.getView().displayCurrentPlayerHand(new PrintWriter(output), game.getPlayer(sponsorIndex));
        int position = controller.getView().getQuestPosition(new PrintWriter(output), new Scanner(input2));
        String result = output.toString();

        assertEquals(position, 0);
        assertTrue(result.contains("HAND:"));
        assertTrue(result.contains("Enter the index of the card in your hand you would like to add to the stage of the quest, or type 'q' to quit building this stage"));
        for (AdventureCard card : game.getPlayer(sponsorIndex).getHand()) {
            String expectedOutput = card.toString();
            assertTrue(result.contains(expectedOutput));
        }

    }

    @Test
    @DisplayName("Ensure the sponsors deck is displayed and the sponsor is prompted to either quit or choose a card, and the sponsor picks to quit")
    void RESP_21_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";
        String input2 = "q\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        controller.getView().displayCurrentPlayerHand(new PrintWriter(output), game.getPlayer(sponsorIndex));
        int position = controller.getView().getQuestPosition(new PrintWriter(output), new Scanner(input2));
        String result = output.toString();

        assertEquals(position, -1);
        assertTrue(result.contains("HAND:"));
        assertTrue(result.contains("Enter the index of the card in your hand you would like to add to the stage of the quest, or type 'q' to quit building this stage"));
        for (AdventureCard card : game.getPlayer(sponsorIndex).getHand()) {
            String expectedOutput = card.toString();
            assertTrue(result.contains(expectedOutput));
        }

    }

    @Test
    @DisplayName("We are on the first stage, and check if it is empty before anything else")
    void RESP_22_Test_01() {
        Game game = new Game();
        game.getQuest().add(new ArrayList<>());
        boolean isEmpty = game.isStageEmpty(0);
        assertTrue(isEmpty);

    }

    @Test
    @DisplayName("We are on the second stage, and check if it is empty before anything else")
    void RESP_22_Test_02() {
        Game game = new Game();
        ArrayList<AdventureCard> stage = new ArrayList<>();
        stage.add(new AdventureCard(CardType.F25));

        game.getQuest().add(stage);
        game.getQuest().add(new ArrayList<>());
        boolean isEmpty = game.isStageEmpty(1);
        assertTrue(isEmpty);

    }

    @Test
    @DisplayName("The sponsor quits but the first stage has no cards associated, thus the proper error message is displayed")
    void RESP_22_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";
        game.getQuest().add(new ArrayList<>());

        controller.getSponsor(new PrintWriter(output), new Scanner(input));
        if(game.isStageEmpty(game.getCurrentStageIndex())) {
            game.getView().displayMessage(new PrintWriter(output), "Error: A stage cannot be empty");
        }

        String result = output.toString();
        assertTrue(result.contains("Error: A stage cannot be empty"));
        assertTrue(game.isStageEmpty(0));

    }

    @Test
    @DisplayName("The sponsor quits but the first stage has no cards, thus the proper error message is displayed and the user is asked to choose again")
    void RESP_22_Test_04() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));

        // The sponsor chooses to quit before adding a card to their stage, the error message should be displayed and then to quit,
        // The sponsor adds a valid card and quits

        String input2 = "q\n0\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);

        String result = output.toString();
        assertTrue(result.contains("Error: A stage cannot be empty"));

    }

    @Test
    @DisplayName("We are on the second stage, and check if it is sufficient compared to the first")
    void RESP_23_Test_01() {
        Game game = new Game();

        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));

        game.getQuest().add(stage1);
        game.getQuest().add(stage2);


        boolean isInsufficient = game.isStageInsufficient(1);
        assertTrue(isInsufficient);

    }

    @Test
    @DisplayName("We are on the third stage, and check if it is sufficient compared to the second")
    void RESP_23_Test_02() {
        Game game = new Game();

        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F5));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F35));
        ArrayList<AdventureCard> stage3 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F25));

        game.getQuest().add(stage1);
        game.getQuest().add(stage2);
        game.getQuest().add(stage3);


        boolean isInsufficient = game.isStageInsufficient(2);
        assertTrue(isInsufficient);

    }

    @Test
    @DisplayName("The sponsor quits but the second stage is insufficient compared to the first, thus the proper error message is displayed")
    void RESP_23_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";

        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));

        game.getQuest().add(stage1);
        game.getQuest().add(stage2);
        game.setCurrentStageIndex(1);

        controller.getSponsor(new PrintWriter(output), new Scanner(input));
        if(game.isStageInsufficient(game.getCurrentStageIndex())) {
            game.getView().displayMessage(new PrintWriter(output), "Error: Insufficient value for this stage");
        }

        String result = output.toString();
        assertTrue(result.contains("Error: Insufficient value for this stage"));
        assertTrue(game.isStageInsufficient(game.getCurrentStageIndex()));

    }

    @Test
    @DisplayName("The sponsor quits but the second stage is insufficient compared to the first, thus the proper error message is displayed and the user needs to pick again to quit")
    void RESP_23_Test_04() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";

        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));

        game.getQuest().add(stage1);
        game.setCurrentStageIndex(1);

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        sponsor.getHand().set(0, new AdventureCard(CardType.F5));
        sponsor.getHand().set(1, new AdventureCard(CardType.EXCALIBUR));

        // The sponsor chooses the F5 card and quits, the error message should be displayed and then to quit,
        // the sponsor then chooses a valid selection and quits again
        String input2 = "0\nq\n1\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);

        String result = output.toString();
        assertTrue(result.contains("Error: Insufficient value for this stage"));
        assertTrue(result.contains("Enter the index of the card in your hand you would like to add to the stage of the quest"));
    }

    @Test
    @DisplayName("The sponsor picks a weapon before a foe, and we check if this is the case (status code 1)")
    void RESP_24_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        // Make the input the last card in the hand sine it will always be a foe weapon
        AdventureCard weaponCard = sponsor.getHand().getLast();

        game.getQuest().add(new ArrayList<>());
        int option = game.isStageSelectionValid(weaponCard);

        assertEquals(1, option);
    }

    @Test
    @DisplayName("The sponsor picks a duplicate weapons, and we check if this is the case (status code 2)")
    void RESP_24_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        // Set the same weapon for the players last 2 cards
        sponsor.getHand().set(sponsor.getHandSize() - 1, new AdventureCard(CardType.EXCALIBUR));
        sponsor.getHand().set(sponsor.getHandSize() - 2, new AdventureCard(CardType.EXCALIBUR));

        // Force the sponsor to pick a foe, and then pick 2 of the same weapon cards
        AdventureCard cardFoe = sponsor.getHand().getFirst();
        AdventureCard weaponCard1 = sponsor.getHand().getLast();
        AdventureCard weaponCard2 = sponsor.getHand().get(sponsor.getHandSize() - 2);

        game.getQuest().add(new ArrayList<>());
        game.getQuest().getFirst().add(cardFoe);
        game.getQuest().getFirst().add(weaponCard1);
        int option = game.isStageSelectionValid(weaponCard2);

        assertEquals(2, option);
    }

    @Test
    @DisplayName("The sponsor picks 2 foes, and we check if this is the case (status code 3)")
    void RESP_24_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);

        StringWriter output = new StringWriter();
        String input = "yes\n";

        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);


        // Force the sponsor to pick a foe, and then pick 2 of the same weapon cards
        AdventureCard cardFoe1 = sponsor.getHand().getFirst();
        AdventureCard cardFoe2 = sponsor.getHand().get(1);

        game.getQuest().add(new ArrayList<>());
        game.getQuest().getFirst().add(cardFoe1);
        int option = game.isStageSelectionValid(cardFoe2);

        assertEquals(3, option);
    }

    @Test
    @DisplayName("The sponsor picks a weapon before a foe, which causes the Game to print out the appropriate error message and then prompts the user to pick again")
    void RESP_25_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "yes\n";
        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        // Make the input is the last card in the hand since it will always be a foe weapon
        // Make sure the sponsor then picks a foe so they can quit
        String input2 = sponsor.getHandSize() - 1 + "\nq\n0\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);

        String result = output.toString();
        assertTrue(result.contains("Invalid selection: You must choose a foe card first"));
        assertTrue(result.contains("Enter the index of the card in your hand you would like to add to the stage of the quest"));
    }

    @Test
    @DisplayName("The sponsor picks the same type of weapon (Excalibur), which causes the Game to print out the appropriate error message and then prompts the user to pick again")
    void RESP_25_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "yes\n";
        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        // Set the same weapon for the players last 2 cards
        sponsor.getHand().set(sponsor.getHandSize() - 1, new AdventureCard(CardType.EXCALIBUR));
        sponsor.getHand().set(sponsor.getHandSize() - 2, new AdventureCard(CardType.EXCALIBUR));

        // Make the input a foe card, and then the last 2 cards in the hand since they are set to the same type of card
        String input2 = "0\n" + (sponsor.getHandSize() - 1) + "\n" + (sponsor.getHandSize() - 2) + "\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);

        String result = output.toString();
        assertTrue(result.contains("Invalid selection: You cannot have duplicate weapons in a stage"));
        assertTrue(result.contains("Enter the index of the card in your hand you would like to add to the stage of the quest"));
    }

    @Test
    @DisplayName("The sponsor picks 2 foe cards, which causes the Game to print out the appropriate error message and then prompts the user to pick again")
    void RESP_25_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "yes\n";
        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        // Set the same weapon for the players last 2 cards
        sponsor.getHand().set(0, new AdventureCard(CardType.F5));
        sponsor.getHand().set(1, new AdventureCard(CardType.F5));

        // Make the input the first 2 cards since they were set to F5 foe cards
        String input2 = "0\n1\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);

        String result = output.toString();
        assertTrue(result.contains("Invalid selection: You cannot choose 2 foe cards in a stage"));
        assertTrue(result.contains("Enter the index of the card in your hand you would like to add to the stage of the quest"));
    }

    @Test
    @DisplayName("The sponsor picks a valid selection (F25 D5) and the selected cards are included in the stage set, and each card is displayed when selected")
    void RESP_26_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "yes\n";
        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        AdventureCard card1 = new AdventureCard(CardType.F25);
        AdventureCard card2 = new AdventureCard(CardType.DAGGER);

        sponsor.getHand().set(0, card1);
        sponsor.getHand().set(sponsor.getHandSize() - 1, card2);

        // Make the input the first card and last card (F25 D5) and then quit
        String input2 = "0\n" + (sponsor.getHandSize() - 1) + "\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);

        String result = output.toString();
        assertTrue(game.getQuest().get(game.getCurrentStageIndex()).contains(card1));
        assertTrue(game.getQuest().get(game.getCurrentStageIndex()).contains(card2));
        assertTrue(result.contains("Current Stage:"));
        assertTrue(result.contains(card1.toString()));
        assertTrue(result.contains(card2.toString()));
    }

    @Test
    @DisplayName("The sponsor picks a valid selection (F25 D5 D15) and the selected cards are included in the stage set, and each card is displayed when selected")
    void RESP_26_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "yes\n";
        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        AdventureCard card1 = new AdventureCard(CardType.F25);
        AdventureCard card2 = new AdventureCard(CardType.DAGGER);
        AdventureCard card3 = new AdventureCard(CardType.BATTLE_AXE);

        sponsor.getHand().set(0, card1);
        sponsor.getHand().set(sponsor.getHandSize() - 1, card2);
        sponsor.getHand().set(sponsor.getHandSize() - 2, card3);

        // Make the input the first card and last card (F25 D5) and then quit
        String input2 = "0\n" + (sponsor.getHandSize() - 1) + "\n" + (sponsor.getHandSize() - 2) + "\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);

        String result = output.toString();
        assertTrue(game.getQuest().get(game.getCurrentStageIndex()).contains(card1));
        assertTrue(game.getQuest().get(game.getCurrentStageIndex()).contains(card2));
        assertTrue(game.getQuest().get(game.getCurrentStageIndex()).contains(card3));
        assertTrue(result.contains("Current Stage:"));
        assertTrue(result.contains(card1.toString()));
        assertTrue(result.contains(card2.toString()));
        assertTrue(result.contains(card3.toString()));
    }

    @Test
    @DisplayName("The sponsor picks a valid stage (F5) and the selected cards are included in the stage set, and the stage is displayed, while removing the cards from the sponsors hand")
    void RESP_27_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "yes\n";
        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        AdventureCard card1 = new AdventureCard(CardType.F25);

        sponsor.getHand().set(0, card1);

        // Make the input the first card and last card (F25 D5) and then quit
        String input2 = "0\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);
        String result = output.toString();

        assertTrue(game.getQuest().get(game.getCurrentStageIndex()).contains(card1));
        assertTrue(result.contains("Cards for Stage " + (game.getCurrentStageIndex() + 1) + ": "));
        assertTrue(result.contains(card1.toString()));

        assertFalse(sponsor.getHand().contains(card1));
    }
    @Test
    @DisplayName("The sponsor picks a valid stage (F25, D5, B15) and the selected cards are included in the stage set, and the stage is displayed, while removing the cards from the sponsors hand")
    void RESP_27_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "yes\n";
        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        AdventureCard card1 = new AdventureCard(CardType.F25);
        AdventureCard card2 = new AdventureCard(CardType.DAGGER);
        AdventureCard card3 = new AdventureCard(CardType.BATTLE_AXE);

        sponsor.getHand().set(0, card1);
        sponsor.getHand().set(sponsor.getHandSize() - 1, card2);
        sponsor.getHand().set(sponsor.getHandSize() - 2, card3);

        // Make the input the first card and last card (F25 D5, and B15) and then quit
        String input2 = "0\n" + (sponsor.getHandSize() - 1) + "\n" + (sponsor.getHandSize() - 2) + "\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), 1);

        String result = output.toString();
        assertTrue(game.getQuest().get(game.getCurrentStageIndex()).contains(card1));
        assertTrue(result.contains("Cards for Stage " + (game.getCurrentStageIndex() + 1) + ": "));
        assertTrue(result.contains(card1.toString()));
        assertTrue(result.contains(card2.toString()));
        assertTrue(result.contains(card3.toString()));

        assertFalse(sponsor.getHand().contains(card1));
        assertFalse(sponsor.getHand().contains(card2));
        assertFalse(sponsor.getHand().contains(card3));
    }

    @Test
    @DisplayName("The sponsor creates a valid quest that has 2 stages, first stage: F25, second stage: F5, E30")
    void RESP_28_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "yes\n";
        int sponsorIndex = controller.getSponsor(new PrintWriter(output), new Scanner(input));
        Player sponsor = game.getPlayer(sponsorIndex);

        AdventureCard card1 = new AdventureCard(CardType.F25);
        AdventureCard card2 = new AdventureCard(CardType.F5);
        AdventureCard card3 = new AdventureCard(CardType.EXCALIBUR);

        sponsor.getHand().set(0, card1);
        sponsor.getHand().set(1, card2);
        sponsor.getHand().set(sponsor.getHandSize() - 1, card3);

        game.getEventDeck().getCards().set(0, new EventCard(CardType.Q2));
        EventCard qCard = game.drawEventCard();

        assertEquals(CardType.Q2, qCard.getType());

        // Make the input the first (F25) then quit, then make the next inputs F5 and E30 then quit
        String input2 = "0\nq\n0\n" + (sponsor.getHandSize() - 2) +"\nq\n";

        controller.setupQuest(new PrintWriter(output), new Scanner(input2), game.getPlayer(sponsorIndex), qCard.getType().getValue());

        // Ensure the cards are in their correct stage for the quest
        assertTrue(game.getQuest().get(0).contains(card1));
        assertTrue(game.getQuest().get(1).contains(card2));
        assertTrue(game.getQuest().get(1).contains(card3));

        assertFalse(sponsor.getHand().contains(card1));
        assertFalse(sponsor.getHand().contains(card2));
        assertFalse(sponsor.getHand().contains(card3));

        assertEquals(1, game.getCurrentStageIndex());

    }

    @Test
    @DisplayName("The sponsor creates a valid quest that has 3 stages, and each card of each stage are identified")
    void RESP_29_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        // Set up the stages
        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));
        stage2.add(new AdventureCard(CardType.EXCALIBUR));
        ArrayList<AdventureCard> stage3 = new ArrayList<>();
        stage3.add(new AdventureCard(CardType.F70));
        game.getQuest().add(stage1);
        game.getQuest().add(stage2);
        game.getQuest().add(stage3);

        controller.getView().identifyStages(new PrintWriter(output), game.getQuest());
        String result = output.toString();

        assertTrue(result.contains("Stage 1: X"));
        assertTrue(result.contains("Stage 2: XX"));
        assertTrue(result.contains("Stage 3: X"));

    }

    @Test
    @DisplayName("The sponsor creates a valid quest that has 2 stages, and each card of each stage are identified")
    void RESP_29_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        // Set up the stages
        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        stage1.add(new AdventureCard(CardType.DAGGER));
        stage1.add(new AdventureCard(CardType.HORSE));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));
        stage2.add(new AdventureCard(CardType.EXCALIBUR));
        stage2.add(new AdventureCard(CardType.SWORD));

        game.getQuest().add(stage1);
        game.getQuest().add(stage2);

        controller.getView().identifyStages(new PrintWriter(output), game.getQuest());
        String result = output.toString();

        assertTrue(result.contains("Stage 1: XXX"));
        assertTrue(result.contains("Stage 2: XXX"));

    }

    @Test
    @DisplayName("The sponsor is p1 and every other player is eligible")
    void RESP_30_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        // Set up the stages
        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        stage1.add(new AdventureCard(CardType.DAGGER));
        stage1.add(new AdventureCard(CardType.HORSE));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));
        stage2.add(new AdventureCard(CardType.EXCALIBUR));
        stage2.add(new AdventureCard(CardType.SWORD));
        game.getQuest().add(stage1);
        game.getQuest().add(stage2);

        ArrayList<Player> eligibleParticipants = controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        String result = output.toString();

        assertFalse(eligibleParticipants.contains(game.getPlayer(0)));
        assertTrue(eligibleParticipants.contains(game.getPlayer(1)));
        assertTrue(eligibleParticipants.contains(game.getPlayer(2)));
        assertTrue(eligibleParticipants.contains(game.getPlayer(3)));

        assertTrue(result.contains(game.getPlayer(1).toString()));
        assertTrue(result.contains(game.getPlayer(2).toString()));
        assertTrue(result.contains(game.getPlayer(3).toString()));
    }

    @Test
    @DisplayName("The sponsor is p1 and player 2 is ineligible")
    void RESP_30_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        // Set up the stages
        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        stage1.add(new AdventureCard(CardType.DAGGER));
        stage1.add(new AdventureCard(CardType.HORSE));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));
        stage2.add(new AdventureCard(CardType.EXCALIBUR));
        stage2.add(new AdventureCard(CardType.SWORD));
        game.getQuest().add(stage1);
        game.getQuest().add(stage2);

        game.getPlayer(1).setEligibility(false);

        ArrayList<Player> eligibleParticipants = controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        String result = output.toString();

        assertFalse(eligibleParticipants.contains(game.getPlayer(0)));
        assertFalse(eligibleParticipants.contains(game.getPlayer(1)));
        assertTrue(eligibleParticipants.contains(game.getPlayer(2)));
        assertTrue(eligibleParticipants.contains(game.getPlayer(3)));

        assertTrue(result.contains(game.getPlayer(2).toString()));
        assertTrue(result.contains(game.getPlayer(3).toString()));

    }

    @Test
    @DisplayName("The sponsor is p1 and player 2 and 4 are ineligible")
    void RESP_30_Test_03() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        // Set up the stages
        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        stage1.add(new AdventureCard(CardType.DAGGER));
        stage1.add(new AdventureCard(CardType.HORSE));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));
        stage2.add(new AdventureCard(CardType.EXCALIBUR));
        stage2.add(new AdventureCard(CardType.SWORD));
        game.getQuest().add(stage1);
        game.getQuest().add(stage2);

        game.getPlayer(1).setEligibility(false);
        game.getPlayer(3).setEligibility(false);

        ArrayList<Player> eligibleParticipants = controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        String result = output.toString();

        assertFalse(eligibleParticipants.contains(game.getPlayer(0)));
        assertFalse(eligibleParticipants.contains(game.getPlayer(1)));
        assertTrue(eligibleParticipants.contains(game.getPlayer(2)));
        assertFalse(eligibleParticipants.contains(game.getPlayer(3)));

        assertTrue(result.contains(game.getPlayer(2).toString()));

    }

    @Test
    @DisplayName("The sponsor is p1 and every other player is eligible, and every other player accepts to participate")
    void RESP_31_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        // Set up the stages
        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        stage1.add(new AdventureCard(CardType.DAGGER));
        stage1.add(new AdventureCard(CardType.HORSE));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));
        stage2.add(new AdventureCard(CardType.EXCALIBUR));
        stage2.add(new AdventureCard(CardType.SWORD));
        game.getQuest().add(stage1);
        game.getQuest().add(stage2);

        String input = "yes\nyes\nyes";

        controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        controller.getPromptedEligiblePlayers(new PrintWriter(output), new Scanner(input));

        assertFalse(game.getEligibleParticipants().contains(game.getPlayer(0)));
        assertTrue(game.getEligibleParticipants().contains(game.getPlayer(1)));
        assertTrue(game.getEligibleParticipants().contains(game.getPlayer(2)));
        assertTrue(game.getEligibleParticipants().contains(game.getPlayer(3)));

    }

    @Test
    @DisplayName("The sponsor is p1 and every other player is eligible, and only players 2 and 4 accept to participate")
    void RESP_31_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        // Set up the stages
        ArrayList<AdventureCard> stage1 = new ArrayList<>();
        stage1.add(new AdventureCard(CardType.F25));
        stage1.add(new AdventureCard(CardType.DAGGER));
        stage1.add(new AdventureCard(CardType.HORSE));
        ArrayList<AdventureCard> stage2 = new ArrayList<>();
        stage2.add(new AdventureCard(CardType.F5));
        stage2.add(new AdventureCard(CardType.EXCALIBUR));
        stage2.add(new AdventureCard(CardType.SWORD));
        game.getQuest().add(stage1);
        game.getQuest().add(stage2);

        String input = "yes\nno\nyes";

        controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        controller.getPromptedEligiblePlayers(new PrintWriter(output), new Scanner(input));

        assertFalse(game.getEligibleParticipants().contains(game.getPlayer(0)));
        assertTrue(game.getEligibleParticipants().contains(game.getPlayer(1)));
        assertFalse(game.getEligibleParticipants().contains(game.getPlayer(2)));
        assertTrue(game.getEligibleParticipants().contains(game.getPlayer(3)));

    }

    @Test
    @DisplayName("The players who accept to participate each draw an adventure card, and P3 needs to trim")
    void RESP_32_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        // Only ensure p3 trims
        game.getPlayer(1).discard(0, game.getAdventureDeck());
        game.getPlayer(1).discard(0, game.getAdventureDeck());
        game.getPlayer(3).discard(0, game.getAdventureDeck());
        game.getPlayer(3).discard(0, game.getAdventureDeck());

        String input = "yes\nyes\nyes";
        String input2 = "0\n0\n";

        int p2OriginalHand = game.getPlayer(1).getHand().size();
        int p4OriginalHand = game.getPlayer(3).getHand().size();

        controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        controller.getPromptedEligiblePlayers(new PrintWriter(output), new Scanner(input));
        controller.handleParticipation(new PrintWriter(output), new Scanner(input2));

        assertEquals(p2OriginalHand + 1, game.getPlayer(1).getHandSize());
        assertEquals(12, game.getPlayer(2).getHandSize());
        assertEquals(p4OriginalHand + 1, game.getPlayer(3).getHandSize());

    }

    @Test
    @DisplayName("All players decide to not participate and thus the quest ends")
    void RESP_33_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();

        String input = "no\nno\nno";


        controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        controller.getPromptedEligiblePlayers(new PrintWriter(output), new Scanner(input));
        boolean isQuestDone = game.isQuestDone();

        assertTrue(isQuestDone);

    }

    @Test
    @DisplayName("All players are ineligible since they lost an attack, and thus the quest ends")
    void RESP_33_Test_02() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();
        String input = "";

        game.getPlayer(1).setEligibility(false);
        game.getPlayer(2).setEligibility(false);
        game.getPlayer(3).setEligibility(false);

        controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        controller.getPromptedEligiblePlayers(new PrintWriter(output), new Scanner(input));
        boolean isQuestDone = game.isQuestDone();

        assertTrue(isQuestDone);
    }

    @Test
    @DisplayName("All players are eligible, and they are prompted to choose a position of a card")
    void RESP_34_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();
        String input = "yes\nyes\nyes";
        String input2 = "0\nq\n0\nq\n0\nq\n";

        controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        controller.getPromptedEligiblePlayers(new PrintWriter(output), new Scanner(input));
        boolean isQuestDone = game.isQuestDone();
        assertFalse(isQuestDone);

        controller.voidSetupAttacks(new PrintWriter(output), new Scanner(input2));
        String result = output.toString();

        assertTrue(result.contains(game.getPlayer(1).toString() + ", enter the index of the card in your hand you would like to add to the attack, or type 'q' to quit building this attack"));
        assertTrue(result.contains(game.getPlayer(2).toString() + ", enter the index of the card in your hand you would like to add to the attack, or type 'q' to quit building this attack"));
        assertTrue(result.contains(game.getPlayer(3).toString() + ", enter the index of the card in your hand you would like to add to the attack, or type 'q' to quit building this attack"));

    }

    @Test
    @DisplayName("Only P2 is eligible, and selects an Excalibur which is added to their attack and displayed")
    void RESP_35_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();
        String input = "yes\nno\nno";
        String input2 = "0\nq\n";

        AdventureCard card = new AdventureCard(CardType.EXCALIBUR);
        game.getPlayer(1).getHand().set(0, card);

        controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        controller.getPromptedEligiblePlayers(new PrintWriter(output), new Scanner(input));
        boolean isQuestDone = game.isQuestDone();
        assertFalse(isQuestDone);

        controller.voidSetupAttacks(new PrintWriter(output), new Scanner(input2));
        String result = output.toString();

        assertEquals(1, game.getPlayer(1).getAttack().size());
        assertTrue(game.getPlayer(1).getAttack().contains(card));
        assertTrue(result.contains(game.getPlayer(1).toString() + ", Current Attack: E30"));
    }

    @Test
    @DisplayName("Only P2 is eligible, and selects an Excalibur twice, thus is re-prompted to pick again and quits")
    void RESP_36_Test_01() {
        Game game = new Game();
        Controller controller = new Controller(game);
        StringWriter output = new StringWriter();
        String input = "yes\nno\nno";
        String input2 = "0\n1\nq\n";

        // Set Excalibur in position 0 and 1 in P2's hand
        AdventureCard card = new AdventureCard(CardType.EXCALIBUR);
        AdventureCard card2 = new AdventureCard(CardType.EXCALIBUR);
        game.getPlayer(1).getHand().set(0, card);
        game.getPlayer(1).getHand().set(1, card2);

        controller.getAndDisplayEligibleParticipants(new PrintWriter(output), 0);
        controller.getPromptedEligiblePlayers(new PrintWriter(output), new Scanner(input));
        boolean isQuestDone = game.isQuestDone();
        assertFalse(isQuestDone);

        controller.voidSetupAttacks(new PrintWriter(output), new Scanner(input2));
        String result = output.toString();

        assertEquals(1, game.getPlayer(1).getAttack().size());
        assertTrue(game.getPlayer(1).getAttack().contains(card));
        assertFalse(game.getPlayer(1).getAttack().contains(card2));
        assertTrue(result.contains("Invalid selection: You cannot have duplicate weapons in an attack"));
    }

}