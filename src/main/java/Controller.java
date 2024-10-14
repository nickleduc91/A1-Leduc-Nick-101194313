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
            }
            output.println();
        }
    }

    public void voidSetupAttacks(PrintWriter output, Scanner input) {
        for(Player p : game.getEligibleParticipants()) {
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

    }

    public void handleParticipation(PrintWriter output, Scanner input) {
        for(Player p : game.getEligibleParticipants()) {
            int trim = p.addCardToHand(game.drawAdventureCard());
            view.trimCard(output, input, p, game.getAdventureDeck(), trim);
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
