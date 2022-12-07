import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public class Day1 {
	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		// count calories
		ArrayList<Integer> elfCalories = new ArrayList<>();
		int caloryCount = 0;
		try {
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				if(line.equals("")) {
					elfCalories.add(caloryCount);
					caloryCount = 0;
				} else {
					int calory = Integer.parseInt(line);
					caloryCount += calory;
				}
			}
			if(caloryCount > 0) {
				elfCalories.add(caloryCount);
			}
		} catch(IOException e) {
			// ignore
		}

		Collections.sort(elfCalories, Collections.reverseOrder());

		// print out result
		int i = 1;
		for(int elfCalory : elfCalories) {
			System.out.println(i++ + ": " + elfCalory);
		}
		System.out.println("Top three: " + (elfCalories.get(0) + elfCalories.get(1) + elfCalories.get(2)));
	}
}