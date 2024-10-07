import java.io.PrintWriter;
import java.util.List;

public class View {

    public void displayWinners(PrintWriter output, List<Player> players) {
        output.println("Winning Players:");
        for (Player player : players) {
            output.println(player);
        }

        output.flush();
    }
}
