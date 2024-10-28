import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private Game game;
    private View view;

    public Controller(Game game) {
        this.game = game;
        view = new View();

        game.initializeDecks();
        game.initializePlayers();
    }

    public void endQuest(PrintWriter output, Scanner input, int sponsorIndex, EventCard qCard) {

        int cardCount = 0;
        int numStages = game.getQuest().size();

        // Remove all cards from the quest and discard them
        for (ArrayList<AdventureCard> stage : game.getQuest()) {
            ArrayList<AdventureCard> cardsToDiscard = new ArrayList<>(stage);

            // Add each card to the discard pile
            for (AdventureCard card : cardsToDiscard) {
                cardCount += 1;
                game.getAdventureDeck().addToDiscardPile(card);
            }

            stage.clear();
        }

        // Draw cards and trim if needed
        Player sponsor = game.getPlayer(sponsorIndex);

        view.displayMessage(output, "The sponsor must draw " + (cardCount + numStages) + " adventure cards");

        for(int i = 0; i < (cardCount + numStages); i++) {
            int trim = sponsor.addCardToHand(game.drawAdventureCard());
            view.trimCard(output, input, sponsor, game.getAdventureDeck(), trim);
        }

        // Discard the q card
        game.getEventDeck().addToDiscardPile(qCard);

        // Reset all quest related variables
        game.getQuest().clear();
        game.getEligibleParticipants().clear();
        game.setCurrentStageIndex(0);

        for(int i = 0; i < 4; i++) {
            Player p = game.getPlayer(i);
            p.setEligibility(true);

            game.getEligibleParticipants().add(p);
        }

    }

    public boolean endResolution(PrintWriter output, int stageIndex) {
        // Discard all cards used to attack
        for(Player p : game.getEligibleParticipants()) {
            ArrayList<AdventureCard> attackHand = p.getAttack();
            ArrayList<AdventureCard> cardsToDiscard = new ArrayList<>(attackHand);

            // Remove each card from the player's attack hand and add it to the discard pile
            for(AdventureCard card : cardsToDiscard) {
                game.getAdventureDeck().addToDiscardPile(card);
            }
            attackHand.clear();
        }

        game.getEligibleParticipants().removeIf(p -> !p.getEligibility());
        if(game.getEligibleParticipants().isEmpty()) {
            view.displayMessage(output, "The quest is now finished since nobody is eligible to continue");
            return true;
        }
        // Last stage just happened and there are still participants who are eligible
        if(stageIndex == game.getQuest().size() - 1) {
            view.displayMessage(output, "Winner(s) of the quest:");
            view.displayMessage(output, "Each have gained " + (stageIndex + 1) + " shields.");
            for(Player p : game.getEligibleParticipants()) {
                view.displayMessage(output, p.toString());
                p.addShields(stageIndex + 1);
            }
            return true;
        }
        return false;
    }

    public void resolveAttacks(PrintWriter output, int stageIndex) {
        int stageAttackValue = game.getAttackValue(stageIndex);

        output.println("-- Resolving attacks --");
        // Display the Stage's attack
        output.print("Attack for Stage" + (stageIndex + 1) + ": ");
        for(AdventureCard card : game.getQuest().get(stageIndex)) {
            output.print(card + " ");
        }
        output.println();
        output.println();

        for(Player p : game.getEligibleParticipants()) {
            int attackValue = game.getAttackValue(p);

            // Display the players attack
            output.print("Attack for " + p + ": ");
            for(AdventureCard card : p.getAttack()) {
                output.print(card + " ");
            }
            output.println();

            if(attackValue < stageAttackValue) {
                p.setEligibility(false);
                view.displayMessage(output, p.toString() + " is now ineligible since their attack value is less than the stage value");
            } else {
                view.displayMessage(output, p + " is eligible to continue");
            }
            output.println();
        }
    }

    public void setupAttackForPlayer(PrintWriter output, Scanner input, Player p) {
        view.displayCurrentPlayerHand(output, p);

        while (true) {
            int position = view.getAttackPosition(output, input, p);

            if(position == -1) {
                break;
            }

            AdventureCard card = p.getHand().get(position);

            //Check if a foe was selected
            if(card.getType().isFoe()) {
                view.displayMessage(output, "Invalid selection: You cannot select a foe in an attack");
                continue;
            }

            // Check for duplicates
            if(!p.getAttack().isEmpty()) {
                boolean duplicate = false;
                for (AdventureCard attackCard : p.getAttack()) {
                    if (attackCard.getType().getName().equals(card.getType().getName())) {
                        duplicate = true;
                        break;
                    }
                }
                if(duplicate) {
                    view.displayMessage(output, "Invalid selection: You cannot have duplicate weapons in an attack");
                    continue;
                }
            }

            p.getAttack().add(card);
            view.displayPlayerAttack(output, p);
        }

        output.println();
        if(p.getAttack().isEmpty()) {
            output.print("Attack for " + p + ": No cards were selected");
        } else {
            output.print("Attack for " + p + ": ");
            for(AdventureCard card : p.getAttack()) {
                output.print(card + " ");
                p.getHand().remove(card);
            }
        }

        output.println();
        output.flush();
    }

    public void voidSetupAttacks(PrintWriter output, Scanner input) {
        for(Player p : game.getEligibleParticipants()) {
            setupAttackForPlayer(output, input, p);
        }
    }

    public void addCardToParticipantHand(PrintWriter output, Scanner input, Player p) {
        int trim = p.addCardToHand(game.drawAdventureCard());
        view.trimCard(output, input, p, game.getAdventureDeck(), trim);
    }

    public void handleParticipation(PrintWriter output, Scanner input) {
        for(Player p : game.getEligibleParticipants()) {
            addCardToParticipantHand(output, input, p);
        }
    }

    public ArrayList<Player> getPromptedEligiblePlayers(PrintWriter output, Scanner input) {
        ArrayList<Player> eligiblePlayers = new ArrayList<>();

        for(Player p: game.getEligibleParticipants()) {
            boolean isPlaying = view.getParticipantAnswer(output, input, p);
            if(isPlaying) {
                eligiblePlayers.add(p);
            } else {
                p.setEligibility(false);
            }
        }
        game.setEligibleParticipants(eligiblePlayers);
        return game.getEligibleParticipants();
    }

    public ArrayList<Player> getAndDisplayEligibleParticipants(PrintWriter output, int sponsorIndex) {

        game.getEligibleParticipants().removeIf(p -> p.getIndex() == sponsorIndex || !p.getEligibility());

        view.displayEligibleParticipants(output, game.getEligibleParticipants());
        return game.getEligibleParticipants();
    }

    public void setupQuest(PrintWriter output, Scanner input, Player sponsor, int stages) {

        for(int i = 0; i < stages; i++) {
            game.getQuest().add(new ArrayList<>());
            view.displayCurrentPlayerHand(output, sponsor);

            while (true) {
                int position = view.getQuestPosition(output, input);
                int currentStageIndex = game.getCurrentStageIndex();

                if (position == -1) {
                    if (game.isStageEmpty(currentStageIndex)) {
                        view.displayMessage(output, "Error: A stage cannot be empty");
                        continue;
                    } else if (game.isStageInsufficient(currentStageIndex)) {
                        view.displayMessage(output, "Error: Insufficient value for this stage");
                        continue;
                    } else {
                        break;
                    }
                }

                AdventureCard card = sponsor.getHand().get(position);
                int option = game.isStageSelectionValid(card);

                if (option == 1) {
                    view.displayMessage(output, "Invalid selection: You must choose a foe card first");
                    continue;

                } else if (option == 2) {
                    view.displayMessage(output, "Invalid selection: You cannot have duplicate weapons in a stage");
                    continue;
                } else if (option == 3) {
                    view.displayMessage(output,"Invalid selection: You cannot choose 2 foe cards in a stage");
                    continue;
                }
                game.getQuest().get(currentStageIndex).add(card);
                view.displayCurrentStage(output, game.getQuest().get(currentStageIndex));
            }

            output.println();
            output.print("Cards for Stage " + (game.getCurrentStageIndex() + 1) + ": ");
            for(AdventureCard card : game.getQuest().get(game.getCurrentStageIndex())) {
                output.print(card + " ");
                sponsor.getHand().remove(card);
            }
            output.println();
            output.flush();
            game.setCurrentStageIndex(game.getCurrentStageIndex() + 1);
        }
        game.setCurrentStageIndex(game.getCurrentStageIndex() - 1);

    }

    public int getSponsor(PrintWriter output, Scanner input) {
        int currentPlayerIndex = game.getCurrentPlayer().getIndex();
        int i = currentPlayerIndex;

        while (true) {
            boolean sponsor = view.getSponsor(output, input, i);
            if(sponsor) {
                return i;
            }
            i = (i + 1) % 4;

            if(i == currentPlayerIndex) {
                view.displayMessage(output, "None of the players accepted the quest");
                return -1;
            }
        }

    }

    public boolean playTurn(PrintWriter output, Scanner input) {

        EventCard drawnCard = game.drawEventCard();
        view.displayEventCard(output, drawnCard);

        if(drawnCard.getType().isEvent()) {
            handleDrawnECard(drawnCard, output, input);
        } else {

            int sponsorIndex = getSponsor(output, input);
            if(sponsorIndex == -1) {
                game.getEventDeck().addToDiscardPile(drawnCard);
            } else {
                Player sponsor = game.getPlayer(sponsorIndex);
                setupQuest(output, input, sponsor, drawnCard.getType().getValue());

                for(int i = 0; i < game.getQuest().size(); i++) {
                    getAndDisplayEligibleParticipants(output, sponsorIndex);
                    view.identifyStages(output, game.getQuest());
                    getPromptedEligiblePlayers(output, input);
                    handleParticipation(output, input);
                    view.identifyStages(output, game.getQuest());
                    voidSetupAttacks(output, input);
                    resolveAttacks(output, i);
                    boolean isDone = endResolution(output, i);
                    if(isDone) break;
                }
                endQuest(output, input, sponsorIndex, drawnCard);
            }

        }

        if (game.hasWinner()) {
            view.displayWinners(output, game.getWinners());
            return true;
        }

        view.endTurn(output, input, game.getCurrentPlayer());

        game.updateNextPlayer();
        return false;
    }

    public boolean playTurn() {
        if (game.hasWinner()) {
            return true;
        }

        game.updateNextPlayer();
        return false;
    }

    public void handleDrawnECard(EventCard card, PrintWriter output, Scanner input) {
        if (card.getType() == CardType.PLAGUE) {
            int shields = game.getCurrentPlayer().getShields();
            game.getCurrentPlayer().addShields(shields < 2 ? -shields : -2);
        } else if (card.getType() == CardType.QUEENS_FAVOR) {
            game.getCurrentPlayer().addCardToHand(game.drawAdventureCard());
            int trim = game.getCurrentPlayer().addCardToHand(game.drawAdventureCard());
            view.trimCard(output, input, game.getCurrentPlayer(), game.getAdventureDeck(), trim);
        } else if (card.getType() == CardType.PROSPERITY) {
            for(int i = 0; i < 4; i++) {
                Player p = game.getPlayer(i);
                p.addCardToHand(game.drawAdventureCard());
                int trim = p.addCardToHand(game.drawAdventureCard());
                view.trimCard(output, input, p, game.getAdventureDeck(), trim);
            }
        }
        game.getEventDeck().addToDiscardPile(card);
    }

    public View getView() { return view; }
}
