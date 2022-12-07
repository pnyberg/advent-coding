import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class Day6 {
	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int distinctCharacters = 14;

		try {
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				for(int i = 0; i < line.length() - (distinctCharacters-1); i++) {
					boolean found = true;
					MAIN_LOOP:
					for(int k = i; k <= i + (distinctCharacters-1); k++) {
						for(int n = k+1; n <= i + (distinctCharacters-1); n++) {
							if(line.charAt(k) == line.charAt(n)) {
								found = false;
								break MAIN_LOOP;
							}
						}
					}
					if(found) {
						System.out.println(i+distinctCharacters);
						break;
					}
				}
			}
		} catch(IOException e) {
			// ignore
		}
	}
}