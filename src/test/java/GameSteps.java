import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;
import io.cucumber.java.en.*;

import java.io.PrintWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameSteps {

    private Controller controller;
    private Game game;
    private View view;
    private final PrintWriter output = new PrintWriter(System.out);
    private StringBuilder participants = new StringBuilder();

    @Given("a new game starts")
    public void a_new_game_starts() {
        game = new Game();
        controller = new Controller(game);
        view = controller.getView();
    }

    @Given("the Adventure Deck's first {int} cards are {string}")
    public void adventure_decks_first_cards_are(int numCards, String cards) {
        String[] cardEnums = cards.split(",");

        // Replace the first numCards in the deck with the specified cards
        for (int i = 0; i < numCards; i++) {
            String cardEnum = cardEnums[i];
            CardType cardType = CardType.valueOf(cardEnum);

            game.getAdventureDeck().getCards().set(i, new AdventureCard(cardType));
        }
    }

    @Given("Player {int} has a hand of {string}")
    public void a_player_gets_their_hand(int playerId, String cards) {
        Player p = game.getPlayer(playerId - 1);
        String[] cardEnums = cards.split(",");

        ArrayList<AdventureCard> hand = new ArrayList<>();
        for (String cardEnum : cardEnums) {
            CardType cardType = CardType.valueOf(cardEnum);
            hand.add(new AdventureCard(cardType));
        }

        p.getHand().clear();
        p.getHand().addAll(hand);
    }

    @When("Player {int} draws a quest of {int} stages")
    public void a_player_draws_a_quest(int playerId, int questNum) {

        EventCard eCard = null;
        if(questNum == 2) {
            eCard = new EventCard(CardType.Q2);
        } else if (questNum == 3) {
            eCard = new EventCard(CardType.Q3);
        } else if (questNum == 4) {
            eCard = new EventCard(CardType.Q4);
        } else if (questNum == 5) {
            eCard = new EventCard(CardType.Q5);
        }

        game.getEventDeck().getCards().set(0, eCard);
        EventCard drawnCard = game.drawEventCard();
        view.displayEventCard(output, drawnCard);
    }

    @Then("Player {int} sponsors the quest")
    public void a_player_decides_to_sponsor(int sponsorId) {

        int currentPlayerIndex = game.getCurrentPlayer().getIndex();
        StringBuilder input = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int playerIndex = (currentPlayerIndex + i) % 4;
            if (playerIndex == sponsorId - 1) {
                input.append("yes\n");
            } else {
                input.append("no\n");
            }
        }

        Scanner scanner = new Scanner(input.toString());
        int sponsorIndex = controller.getSponsor(output, scanner);
        game.setSponsorIndex(sponsorIndex);

    }

    @Then("Sponsor builds stage {int} using the cards at position {string}")
    public void sponsor_builds_stage(int stage, String cards) {
        String input = cards.replace(",", "\n");
        input += "\nq";

        Scanner scanner = new Scanner(input);
        controller.buildStage(output, scanner, game.getPlayer(game.getSponsorIndex()));
    }

    @Then("the Game begins the next stage of the quest")
    public void display_eligible_participants() {
        controller.getAndDisplayEligibleParticipants(output, game.getSponsorIndex());
    }

    @Then("Players {string} choose to participate in stage {int} while Players {string} decline")
    public void players_participate_in_a_stage(String participants, int stage, String decline) {
        String[] participantPlayerIdArray = participants.isEmpty() ? new String[0] : participants.split(",");
        String[] declinePlayerIdArray = decline.isEmpty() ? new String[0] : decline.split(",");

        StringBuilder input = new StringBuilder();

        // Create the input string based on those who participate and those who decline
        for (int i = 1; i <= 4; i++) {
            if (Arrays.asList(participantPlayerIdArray).contains(String.valueOf(i))) {
                input.append("yes\n");
            } else if (Arrays.asList(declinePlayerIdArray).contains(String.valueOf(i))) {
                input.append("no\n");
            }
        }

        controller.getPromptedEligiblePlayers(output, new Scanner(input.toString()));
    }

    @Then("Player {int} draws and discards the cards at position {string}")
    public void players_discards_cards(int playerId, String cards) {
        String input = cards.replace(",", "\n");
        Player p = game.getPlayer(playerId - 1);

        controller.addCardToParticipantHand(output, new Scanner(input), p);
        System.out.println("TEST");
    }

    @Then("Player {int} builds attack for current stage using the cards at position {string}")
    public void player_builds_attack(int playerId, String cards) {
        view.identifyStages(output, game.getQuest());

        String input = cards.replace(",", "\n");
        input += "\nq";

        Player p = game.getPlayer(playerId - 1);
        controller.setupAttackForPlayer(output, new Scanner(input), p);
        System.out.println("TEST");
    }

    @Then("the Game resolves the attacks for stage {int}")
    public void game_resolves_attacks(int stage) {
        controller.resolveAttacks(output, stage - 1);
    }

    @Then("all participants discard their cards used for their attack in stage {int}")
    public void participants_discard_their_cards(int stage) {
        controller.endResolution(output, stage - 1);
        System.out.println("TEST");
    }

    @Then("the Sponsor discards all cards used in the {string} quest and draws and trims cards in position {string}")
    public void end_quest(String eCard, String cards) {
        CardType cardType = CardType.valueOf(eCard);
        String input = cards.replace(",", "\n");

        controller.endQuest(output, new Scanner(input), game.getSponsorIndex(), new EventCard(cardType));
        System.out.println("TEST");
    }

    @Then("Player {int} should have {int} shields")
    public void validate_player_shield_count(int playerId, int numShields) {
        Player p = game.getPlayer(playerId - 1);
        assertEquals(numShields, p.getShields());
    }

    @Then("Player {int} should have a hand of size {int}")
    public void validate_player_hand_count(int playerId, int handSize) {
        Player p = game.getPlayer(playerId - 1);
        assertEquals(handSize, p.getHandSize());
    }

    @Then("Player {int} should have a hand containing the cards {string}")
    public void validate_player_hand(int playerId, String cards) {
        Player p = game.getPlayer(playerId - 1);

        String[] cardEnums = cards.split(",");

        // Validate the cards
        for (int i = 0; i < p.getHandSize(); i++) {
            String cardEnum = cardEnums[i];
            CardType cardType = CardType.valueOf(cardEnum);

            assertEquals(cardType, p.getHand().get(i).getType());
        }
    }

    @Then("The winners of the game are displayed")
    public void display_winners() {
        view.displayWinners(output, game.getWinners());
    }

    @Then("Player {int} should be a winner")
    public void validate_winner(int playerId) {
        Player p = game.getPlayer(playerId - 1);
        assertTrue(game.getWinners().contains(p));
    }

}

























