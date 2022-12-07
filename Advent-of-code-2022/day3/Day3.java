import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day3 {
	private static int scoreItem(char item) {
		if(Character.isLowerCase(item)) {
			// priority 1-26, ascii: 97-122
			return ((int)item) - 96;
		}
		// upper case
		// priority 27-52, ascii 65-90
		return ((int)item) - 38;
	}

	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			int totScore	= 0;
			int badgeScore	= 0;
			List<String> knapsacks = new ArrayList<>();
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				// split items
				int numItems				= line.length();
				String firstCompartment		= line.substring(0, numItems / 2);
				String secondCompartment	= line.substring(numItems / 2);

				// store knapsack
				knapsacks.add(line);

				// check items in both compartments
				List<Character> firstItems = firstCompartment.chars()
											.mapToObj(e -> (char)e)
											.collect(Collectors.toList());
				List<Character> secondItems = secondCompartment.chars()
											.mapToObj(e -> (char)e)
											.collect(Collectors.toList());
				firstItems.retainAll(secondItems);
				Set<Character> commonItems = new HashSet<>(firstItems);

				// score items
				int score = 0;
				for(Character item : commonItems) {
					score += Day3.scoreItem(item);
				}
				totScore += score;

				// knapsacks
				if(knapsacks.size() == 3) {
					// get common item
					List<Character> firstKnapsackItems = knapsacks.get(0).chars()
															.mapToObj(e -> (char)e)
															.collect(Collectors.toList());
					List<Character> secondKnapsackItems = knapsacks.get(1).chars()
															.mapToObj(e -> (char)e)
															.collect(Collectors.toList());
					List<Character> thirdKnapsackItems = knapsacks.get(2).chars()
															.mapToObj(e -> (char)e)
															.collect(Collectors.toList());
					firstKnapsackItems.retainAll(secondKnapsackItems);
					firstKnapsackItems.retainAll(thirdKnapsackItems);
					Set<Character> commonKnapsackItems = new HashSet<>(firstKnapsackItems);
					char commonKnapsackItem = (char)(commonKnapsackItems.toArray()[0]);

					// calc score
					badgeScore += Day3.scoreItem(commonKnapsackItem);

					// empty stored
					knapsacks.clear();
				}
			}
			System.out.println(totScore);
			System.out.println(badgeScore);
		} catch(IOException e) {
			// ignore
		}
	}
}