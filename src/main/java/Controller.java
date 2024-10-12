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

    public void setupQuest(PrintWriter output, Scanner input, Player sponsor, int stages) {
        game.getQuest().add(new ArrayList<>());
        view.displayCurrentPlayerHand(output, sponsor);

        while (true) {
            int position = view.getQuestPosition(output, input);
            int currentStageIndex = game.getCurrentStageIndex();

            if (position == -1) {
                if (game.isStageEmpty(currentStageIndex)) {
                    view.displayMessage(output, "Error: A stage cannot be empty");
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
        }

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
