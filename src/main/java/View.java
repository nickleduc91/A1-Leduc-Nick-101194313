import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;

import java.io.PrintWriter;
import java.util.*;

public class View {

    public void trimCard(PrintWriter output, Scanner input, Player player, Deck<AdventureCard> deck, int trim) {
        if(trim == 0) return;

        for(int i = 0; i < trim; i++) {
            output.println("A trim is needed for " + player);
            displayCurrentPlayerHand(output, player);
            output.println("Input the card index you would like to delete:");
            output.flush();

            String inputStr = input.nextLine();
            int cardIndex = Integer.parseInt(inputStr);
            player.discard(cardIndex, deck);
            output.println("Discarded card at index " + cardIndex);
            output.println();
            output.flush();
        }

        output.println("The trim for " + player + " is complete.");
        displayCurrentPlayerHand(output, player);

    }

    public void displayEventCard(PrintWriter output, EventCard card) {
        output.println("Drawn Card: " + card.toString());
        output.println();
        output.flush();
    }

    public void endTurn(PrintWriter output, Scanner input, Player player) {
        output.println("The turn of " + player + " has ended");
        output.println("Press 'ENTER' to confirm the end of your turn");
        output.flush();
        input.nextLine();
        for (int i = 0; i < 25; i++) {
            output.println("\n");
        }

        output.flush();
    }

    public void displayWinners(PrintWriter output, List<Player> players) {
        output.println("Winning Players:");
        for (Player player : players) {
            output.println(player);
        }

        output.flush();
    }

    public void displayCurrentPlayer(PrintWriter output, Player player) {
        output.println("Current Turn - " + player);
        output.flush();
    }

    public void displayCurrentPlayerHand(PrintWriter output, Player player) {
        List<AdventureCard> foes = new ArrayList<>();
        List<AdventureCard> weapons = new ArrayList<>();

        output.println("HAND:");
        for (AdventureCard card : player.getHand()) {
            if (card.getType().isFoe()) {
                foes.add(card);
            } else if (card.getType().isWeapon()) {
                weapons.add(card);
            }
        }

        foes.sort(Comparator.comparing(AdventureCard::getValue));
        weapons.sort(Comparator.comparing(AdventureCard::getValue));
        weapons.sort((card1, card2) -> {
            if(card1.getType() == CardType.HORSE && card2.getType() == CardType.SWORD) {
                return 1;
            } else if(card1.getType() == CardType.SWORD && card2.getType() == CardType.HORSE) {
                return -1;
            }
            return 0;
        });

        output.print("Foes: ");
        for (AdventureCard card : foes) {
            output.print(card + " ");
        }
        output.println();
        output.print("Weapons: ");
        for (AdventureCard card : weapons) {
            output.print(card + " ");
        }
        output.println("\n----------------------------------");

        output.flush();
    }
}
