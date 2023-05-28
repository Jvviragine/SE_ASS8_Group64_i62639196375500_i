
import java.util.Random;

public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {

		// Instantiates the Game
		Game aGame = new Game();

		// Adding the Players
		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");

		// Generates Random numbers for the Roll Dice
		Random rand = new Random();

		// Keeps playing until there is a winner
		do {

			aGame.roll(rand.nextInt(5) + 1); // Rolls a 6 sided Dice

			// Simulates a Wrong/Right Answer based on a number from 1/8
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer(); // Used instead of actually creating questions 
			} else {
				notAWinner = aGame.wasCorrectlyAnswered() ; // Used instead of actually creating questions 
			}

		} while (notAWinner);

		System.out.println(aGame.purses[0]);

	}
}
