import Cards.AdventureCard;
import Cards.EventCard;
import Enums.CardType;

import java.io.PrintWriter;
import java.util.*;

public class View {

    public boolean getParticipantAnswer(PrintWriter output, Scanner input, Player p) {
        output.println(p.toString() + ", would you like to participate in this stage? (yes/no)");
        output.flush();

        String inputStr = input.nextLine();

        return (inputStr.equals("yes"));
    }

    public void displayEligibleParticipants(PrintWriter output, ArrayList<Player> players) {
        output.println("Eligible participants: ");
        for(Player p : players) {
            output.println(p.toString());
        }
        output.println();
        output.flush();
    }

    public void identifyStages(PrintWriter output, ArrayList<ArrayList<AdventureCard>> quest) {
        for(int i = 0; i < quest.size(); i++) {
            output.print("Stage " + (i + 1) + ": ");
            for(AdventureCard _ : quest.get(i)) {
                output.print("X");
            }
            output.println();
        }
        output.flush();
    }

    int getQuestPosition(PrintWriter output, Scanner input) {
        output.println("Enter the index of the card in your hand you would like to add to the stage of the quest, or type 'q' to quit building this stage");
        output.flush();

        String inputStr = input.nextLine();
        if(inputStr.equals("q")) {
            return -1;
        }

        return Integer.parseInt(inputStr);
    }

    public void displayMessage(PrintWriter output, String message) {
        output.println(message);
        output.flush();
    }

    public boolean getSponsor(PrintWriter output, Scanner input, int index) {
        output.println("Would you like to sponsor the quest? (yes/no)");
        output.flush();

        String response = input.nextLine();
        if(response.equals("yes")) {
            output.println("Player ID: P" + (index + 1) + " is the sponsor of this quest");
            output.flush();
        }
        return (response.equals("yes"));
    }

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
            output.flush();
        }

        output.println("The trim for " + player + " is complete.");
        displayCurrentPlayerHand(output, player);

    }

    public void displayCurrentStage(PrintWriter output, ArrayList<AdventureCard> stage) {
        output.print("Current Stage: ");
        for(AdventureCard card : stage) {
            output.print(card + " ");
        }
        output.println();
        output.flush();
    }

    public void displayEventCard(PrintWriter output, EventCard card) {
        output.println("Drawn Card: " + card.toString());
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

        output.println(player + " - HAND:");
        for (AdventureCard card : player.getHand()) {
            if (card.getType().isFoe()) {
                foes.add(card);
            } else if (card.getType().isWeapon()) {
                weapons.add(card);
            }
        }

        output.print("Foes: ");
        for (AdventureCard card : foes) {
            output.print(card + " ");
        }
        output.println();
        output.print("Weapons: ");
        for (AdventureCard card : weapons) {
            output.print(card + " ");
        }
        output.println();
        output.println();

        output.flush();
    }
}
