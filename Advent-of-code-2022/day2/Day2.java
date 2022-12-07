import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day2 {
	private static int ROCK = 1;
	private static int PAPER = 2;
	private static int SCISSOR = 3;

	private static int LOSS = 0;
	private static int DRAW = 3;
	private static int WIN = 6;

	private static int calcScore(char ownCode, char opponentCode) {
		// own:			X for Rock(1), Y for Paper(2), and Z for Scissors(3)
		// opponent:	A for Rock, B for Paper, and C for Scissors
		int selectionScore = (ownCode == 'X' ? ROCK : (ownCode == 'Y' ? PAPER : SCISSOR));

		int outcomeScore = LOSS;
		if(Day2.isWin(ownCode, opponentCode)) {
			outcomeScore = WIN;
		} else if(Day2.isDraw(ownCode, opponentCode)) {
			outcomeScore = DRAW;
		}
		return selectionScore + outcomeScore;
	}

	private static boolean isWin(char ownCode, char opponentCode) {
		if(ownCode == 'X') {
			// rock beats scissors
			return opponentCode == 'C';
		}
		if(ownCode == 'Y') {
			// paper beats rock
			return opponentCode == 'A';
		}
		if(ownCode == 'Z') {
			// scissors beat paper
			return opponentCode == 'B';
		}
		return false;
	}

	private static boolean isDraw(char ownCode, char opponentCode) {
		if(ownCode == 'X') {
			// rock == rock
			return opponentCode == 'A';
		}
		if(ownCode == 'Y') {
			// paper == paper
			return opponentCode == 'B';
		}
		if(ownCode == 'Z') {
			// scissors == scissors
			return opponentCode == 'C';
		}
		return false;
	}

	// -------------

	private static int calcScore2(char opponentCode, char outcomeCode) {
		// opponent:	A for Rock, B for Paper, and C for Scissors
		// outcome:		X for loss, Y for draw, and Z win
		int outcomeScore = (outcomeCode == 'X' ? LOSS : (outcomeCode == 'Y' ? DRAW : WIN));

		int selectionScore = 0;
		if(outcomeCode == 'X') {
			// own loss
			if(opponentCode == 'A') {
				selectionScore = SCISSOR;
			} else if(opponentCode == 'B') {
				selectionScore = ROCK;
			} else if(opponentCode == 'C') {
				selectionScore = PAPER;
			}
		} else if(outcomeCode == 'Y') {
			// own draw
			if(opponentCode == 'A') {
				selectionScore = ROCK;
			} else if(opponentCode == 'B') {
				selectionScore = PAPER;
			} else if(opponentCode == 'C') {
				selectionScore = SCISSOR;
			}
		} else {
			// own win
			if(opponentCode == 'A') {
				selectionScore = PAPER;
			} else if(opponentCode == 'B') {
				selectionScore = SCISSOR;
			} else if(opponentCode == 'C') {
				selectionScore = ROCK;
			}
		}
		return selectionScore + outcomeScore;
	}

	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int totScore = 0;
		int totScore2 = 0;
		try {
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				char opponentCode	= line.charAt(0);
				char secondCode		= line.charAt(2);

				// calc first
				int score			= Day2.calcScore(secondCode, opponentCode);
				totScore += score;

				// calc second
				int score2			= Day2.calcScore2(opponentCode, secondCode);
				totScore2 += score2;
			}
		} catch(IOException e) {
			// ignore
		}
		System.out.println("First: " + totScore);
		System.out.println("Second: " + totScore2);
	}
}