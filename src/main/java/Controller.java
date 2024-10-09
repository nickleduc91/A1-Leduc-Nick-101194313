import Cards.EventCard;
import Enums.CardType;

import java.io.PrintWriter;
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
        }

    }
}
