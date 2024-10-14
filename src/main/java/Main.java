import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Controller controller = new Controller(game);
        boolean gameIsWon = false;

        while(!gameIsWon) {
            gameIsWon = controller.playTurn(new PrintWriter(System.out), new Scanner(System.in));
        }

    }
}