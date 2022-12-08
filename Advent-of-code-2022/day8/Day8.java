import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Day8 {
	private static int calcScenicScore(int[][] grid, int x, int y) {
		int scenicHeight = grid[x][y];

		int north = 0;
		for(int k = 0; k < grid[0].length; k++) {
			if((y - k) == 0) {
				north = k;
				break;
			} else if(k > 0 && grid[x][y - k] >= scenicHeight) {
				north = k;
				break;
			}
		}
		int east = 0;
		for(int k = 0; k < grid.length; k++) {
			if((x + k) == (grid.length - 1)) {
				east = k;
				break;
			} else if(k > 0 && grid[x + k][y] >= scenicHeight) {
				east = k;
				break;
			}
		}
		int south = 0;
		for(int k = 0; k < grid[0].length; k++) {
			if((y + k) == (grid[0].length - 1)) {
				south = k;
				break;
			} else if(k > 0 && grid[x][y + k] >= scenicHeight) {
				south = k;
				break;
			}
		}
		int west = 0;
		for(int k = 0; k < grid.length; k++) {
			if((x - k) == 0) {
				west = k;
				break;
			} else if(k > 0 && grid[x - k][y] >= scenicHeight) {
				west = k;
				break;
			}
		}
		return north * east * south * west;
	}

	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			ArrayList<String> lines = new ArrayList<>();
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				lines.add(line);
			}

			// build grid
			int width = lines.get(0).length();
			int height = lines.size();
			int[][] grid = new int[width][height];
			System.out.println(width + " x " + height);

			for(int y = 0; y < height; y++) {
				String line = lines.get(y);
				for(int x = 0; x < width; x++) {
					grid[x][y] = Integer.parseInt(line.substring(x, x+1));
				}
			}

			// check visibility
			/*
				bitwise check-value
				0 = not visible
				1 = visible west
				2 = visible east
				4 = visible north
				8 = visible south
			*/ 
			int WEST = 1;
			int EAST = 2;
			int NORTH = 4;
			int SOUTH = 8;
			int[][] visibility = new int[width][height];

			int numVisible = 0;
			int westMax = 0;
			int eastMax = 0;
			int[] northMax = new int[width];
			int[] southMax = new int[width];

			int maxScenicScore = 0;
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					// check from west
					if(x == 0) {
						// if first column
						if(visibility[x][y] == 0) {
							numVisible++;
						}
						westMax = grid[x][y];
						visibility[x][y] += WEST;
					} else if(westMax < grid[x][y]) {
						// if current tree is the tallest this far in the row
						if(visibility[x][y] == 0) {
							numVisible++;
						}
						westMax = grid[x][y];
						visibility[x][y] += WEST;
					}

					// check from east
					int oppX = (width - 1) - x;
					if(oppX == (width - 1)) {
						// if first column
						if(visibility[oppX][y] == 0) {
							numVisible++;
						}
						eastMax = grid[oppX][y];
						visibility[oppX][y] += EAST;
					} else if(eastMax < grid[oppX][y]) {
						// if current tree is the tallest this far in the row
						if(visibility[oppX][y] == 0) {
							numVisible++;
						}
						eastMax = grid[oppX][y];
						visibility[oppX][y] += EAST;
					}

					// check from north
					if(y == 0) {
						// if first row
						if(visibility[x][y] == 0) {
							numVisible++;
						}
						northMax[x] = grid[x][y];
						visibility[x][y] += NORTH;
					} else if(northMax[x] < grid[x][y]) {
						// if previous row is visible from north + previous tree is shorter
						if(visibility[x][y] == 0) {
							numVisible++;
						}
						northMax[x] = grid[x][y];
						visibility[x][y] += NORTH;
					}

					// check from south
					int oppY = (height - 1) - y;
					if(oppY == (height - 1)) {
						// if first column
						if(visibility[x][oppY] == 0) {
							numVisible++;
						}
						southMax[x] = grid[x][oppY];
						visibility[x][oppY] += SOUTH;
					} else if(southMax[x] < grid[x][oppY]) {
						// if previous column is visible from south + previous tree is shorter
						if(visibility[x][oppY] == 0) {
							numVisible++;
						}
						southMax[x] = grid[x][oppY];
						visibility[x][oppY] += SOUTH;
					}

					// calc scenic score
					int scenicScore = Day8.calcScenicScore(grid, x, y);
					if(scenicScore > maxScenicScore) {
						maxScenicScore = scenicScore;
					}
				}
			}
			System.out.println("Visible: " + numVisible);
			System.out.println("Max scenic: " + maxScenicScore);
		} catch(IOException e) {
			// ignore
		}
	}
}