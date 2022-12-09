import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Day9 {
	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			ArrayList<Point> snake = new ArrayList<>();
			int numPoints = 10;
			for(int i = 0; i < numPoints; i++) {
				snake.add(new Point(0, 0));
			}
			ArrayList<Point> tailPositions = new ArrayList<>();
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				char direction	= line.charAt(0);
				int steps		= Integer.parseInt(line.substring(2));
				for(int i = 0; i < steps; i++) {
					Point head = snake.get(0);

					// move head
					if(direction == 'U') {
						head.y++;
					} else if(direction == 'R') {
						head.x++;
					} else if(direction == 'D') {
						head.y--;
					} else if(direction == 'L') {
						head.x--;
					}

					// move tail
					for(int k = 1; k < snake.size(); k++) {
						snake.get(k).moveAdjacent(snake.get(k - 1));
					}

					// store tail-position
					Point tail = snake.get(numPoints - 1);
					tailPositions.add(new Point(tail.x, tail.y));
				}
			}

			Set<Point> uniqueTailPositions = new HashSet<>(tailPositions);
			System.out.println(uniqueTailPositions.size() + " " + tailPositions.size());
		} catch(IOException e) {
			// ignore
		}
	}
}

class Point {
	public int x;
	public int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		Point p = (Point)o;
		return p.x == x && p.y == y;
	}

	@Override
	public int hashCode() {
		return this.x + 1000 * this.y;
	}

	public void moveAdjacent(Point p) {
		if(Math.abs(p.x - x) <= 1 && Math.abs(p.y - y) <= 1) {
			// movement not necessary
			return;
		}

		if(p.x == x) {
			// vertical movement
			if(p.y > y) {
				y++;
			} else {
				y--;
			}
		} else if(p.y == y) {
			// horisontal movement
			if(p.x > x) {
				x++;
			} else {
				x--;
			}
		} else {
			// check for diagonal movement = move along both axis
			if(p.y > y) {
				y++;
			} else {
				y--;
			}
			if(p.x > x) {
				x++;
			} else {
				x--;
			}
		}
	}
}