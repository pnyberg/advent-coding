import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day4 {
	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			int numFullyContained	= 0;
			int numOverlapping		= 0;
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				// extract info
				String[] pairs		= line.split(",");
				String[] firstPair	= pairs[0].split("-");
				String[] secondPair	= pairs[1].split("-");
				int firstPairLower = Integer.parseInt(firstPair[0]);
				int firstPairUpper = Integer.parseInt(firstPair[1]);
				int secondPairLower = Integer.parseInt(secondPair[0]);
				int secondPairUpper = Integer.parseInt(secondPair[1]);

				// compare bounds
				boolean firstContainsSecond			= firstPairLower <= secondPairLower && secondPairUpper <= firstPairUpper;
				boolean secondContainsFirst			= secondPairLower <= firstPairLower && firstPairUpper <= secondPairUpper;
				boolean firstContainsSecondLower	= firstPairLower <= secondPairLower && secondPairLower <= firstPairUpper;
				boolean firstContainsSecondUpper	= firstPairLower <= secondPairUpper && secondPairUpper <= firstPairUpper;
				if(firstContainsSecond || secondContainsFirst) {
					numFullyContained++;
				}
				if(firstContainsSecondLower || firstContainsSecondUpper || secondContainsFirst) {
					numOverlapping++;
				}
			}

			System.out.println("Num fully contained: " + numFullyContained);
			System.out.println("Num overlapping: " + numOverlapping);
		} catch(IOException e) {
			// ignore
		}
	}
}