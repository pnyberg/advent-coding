import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class Day5 {
	private static boolean advanced = true;

	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			ArrayList<String> setupStrings	= new ArrayList<>();
			ArrayList<MoveCommand> commands	= new ArrayList<>();
			boolean atInstructions = false;
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				// check divider
				if(line.equals("")) {
					atInstructions = true;
					continue;
				}

				// handle input
				if(!atInstructions) {
					setupStrings.add(line);
				} else {
					// on form "move X from Y to Z"
					String[] commandTokens = line.split(" ");
					int numMoves	= Integer.parseInt(commandTokens[1]);
					int fromIndex	= Integer.parseInt(commandTokens[3]);
					int toIndex		= Integer.parseInt(commandTokens[5]);
					MoveCommand command = new MoveCommand(fromIndex, toIndex, numMoves);
					commands.add(command);
				}
			}

			// setup stacks
			String indexString		= setupStrings.get(setupStrings.size() - 1);
			String[] indices		= indexString.trim().split("\\s+");
			Stack<Character>[]	stacks	= new Stack[indices.length];
			for(int i = 0; i < stacks.length; i++) {
				stacks[i] = new Stack<Character>();
			}

			// fill stacks
			for(int i = setupStrings.size() - 2; i >= 0; i--) {
				String cratesString	= setupStrings.get(i);
				for(int k = 0; k < cratesString.length(); k += 4) {
					if(!(cratesString.charAt(k + 1) == ' ')) {
						// if item is present at level for stack
						stacks[k / 4].push(cratesString.charAt(k + 1));
					}
				}
			}

			// carry out commands
			for(MoveCommand command : commands) {
				int from	= command.from;
				int to		= command.to;
				int moves	= command.moves;

				if(advanced) {
					Stack<Character> tempStack = new Stack<>();
					for(int i = 0; i < moves; i++) {
						char crate = stacks[from -1].pop();
						tempStack.push(crate);
					}
					while(!tempStack.isEmpty()) {
						char crate = tempStack.pop();
						stacks[to - 1].push(crate);
					}
				} else {
					for(int i = 0; i < moves; i++) {
						char crate = stacks[from -1].pop();
						stacks[to - 1].push(crate);
					}
				}
			}

			// make output of top crates
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < stacks.length; i++) {
				sb.append(stacks[i].peek());
			}
			System.out.println(sb);
		} catch(IOException e) {
			// ignore
		}
	}
}

class MoveCommand {
	public int from;
	public int to;
	public int moves;

	public MoveCommand(int from, int to, int moves) {
		this.from	= from;
		this.to		= to;
		this.moves	= moves;
	}
}