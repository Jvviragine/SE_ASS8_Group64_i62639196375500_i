import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

	// Stores the list of Players
	ArrayList players = new ArrayList();

	// Where each player is at in the board
	int[] places = new int[6];

	// Points of each plyer
	int[] purses = new int[6];

	// If a player in on the Penalty Box
	boolean[] inPenaltyBox = new boolean[6];

	// Types of Questions
	LinkedList popQuestions = new LinkedList();
	LinkedList scienceQuestions = new LinkedList();
	LinkedList sportsQuestions = new LinkedList();
	LinkedList rockQuestions = new LinkedList();

	// Tracks the current player working
	int currentPlayer = 0;

	// Tracks if the current player is getting outside the penalty box
	boolean isGettingOutOfPenaltyBox;

	// Constructor 
	public Game() {
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
		}
	}

	public String createRockQuestion(int index) {
		return "Rock Question " + index;
	}

	// See if it's possible to play the game - Minimum of 2 players
	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	// Adds a Player to te Game
	public boolean add(String playerName) {

		players.add(playerName); // Adds the recently added player to the list of player
		places[howManyPlayers()] = 0; // Initializes the player's initial position to 0 (beginning of the board)
		purses[howManyPlayers()] = 0; // Initializes the player's initial points to 0
		inPenaltyBox[howManyPlayers()] = false; // Since the player does not start in the Panlty box, all values are setted to 0

		System.out.println(playerName + " was added"); // Notifies the user that the player was added
		System.out.println("They are player number " + players.size());
		return true; // Why return a boolean? Always allows to add players (change later)
	}

	// Returns the number of players in the Game
	public int howManyPlayers() {
		return players.size();
	}

	// Simulates a Dice Roll (doesn't generate the Random Number within the method, takes as Input)
	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player"); // Indicates the Current Player
		System.out.println("They have rolled a " + roll); // Outputs the number that the current player rolled

		// In the Case the Current Player is in the Penalty Box
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) { // Player has to Roll an Odd number to leave the Penalty Box
				isGettingOutOfPenaltyBox = true; // Indicates that the player is leaving the Penalty Box

				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box"); // Shows the player will be leaving the Penalty Box
				places[currentPlayer] = places[currentPlayer] + roll; // Advances as many locations as dice rolls
				if (places[currentPlayer] > 11) // Max ammount of Places is 12 - "Goes in Circle"
					places[currentPlayer] = places[currentPlayer] - 12; // Makes sure it does not go off bounds

				System.out.println(players.get(currentPlayer)
						+ "'s new location is "
						+ places[currentPlayer]); // Displays the New Location of the Player

						// Calls the askQuestion
				System.out.println("The category is " + currentCategory()); // Displays the Category of the Question the player has to answer next 
				askQuestion(); // Asks Question to the current player
			} 
			else { // Rolled an Even number -> Bad Luck, you remain here...
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box"); // Displays that the User is not leaving the Penalty box on this round
				isGettingOutOfPenaltyBox = false; // Keeps the leaving Penalty Box as False
			}

		} 
		// If the Player rolled a Dice but the player is NOT in the Penalty Box
		else {

			places[currentPlayer] = places[currentPlayer] + roll; // Just incrases the Position
			// REPEATED CODE - Line 74
			if (places[currentPlayer] > 11)
				places[currentPlayer] = places[currentPlayer] - 12;

			System.out.println(players.get(currentPlayer)
					+ "'s new location is "
					+ places[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
			// ALL REPETITION
		}

	}

	// Gets the Question from a certain category (NEEDS TESTING)
	private void askQuestion() {
		// removeFirst() doesn't remove if the list is empty. However, it's a bug in the Logic (run out of questions)
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());
	}

	// Determines the Category based on the Position - Each position has a Fixed Type of Question
	private String currentCategory() {
		if (places[currentPlayer] == 0)
			return "Pop";
		if (places[currentPlayer] == 4)
			return "Pop";
		if (places[currentPlayer] == 8)
			return "Pop";
		if (places[currentPlayer] == 1)
			return "Science";
		if (places[currentPlayer] == 5)
			return "Science";
		if (places[currentPlayer] == 9)
			return "Science";
		if (places[currentPlayer] == 2)
			return "Sports";
		if (places[currentPlayer] == 6)
			return "Sports";
		if (places[currentPlayer] == 10)
			return "Sports";
		return "Rock"; // Places 3, 7, 11
	}

	// Checks if the Answer is correct without even taking the Answer -> Just works for simulating right and wrong with numbers
	public boolean wasCorrectlyAnswered() {
		// Checks if the Player was in the Penalty Box
		if (inPenaltyBox[currentPlayer]) {
			// Checks if the Player is Getting Outside the Penalty Box
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!"); // Displays that the user has Answered Correctely
				purses[currentPlayer]++; // Increases the Points of the current player
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins."); // Displays the Total Number of Points

				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size())
					currentPlayer = 0;

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size())
					currentPlayer = 0;
				return true;
			}

		} else {

			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size())
				currentPlayer = 0;

			return winner;
		}
	}

	public boolean wrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;
		return true;
	}

	// Checks when the Player has Reched 6 Points - The Winner
	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6); 
	}
}
