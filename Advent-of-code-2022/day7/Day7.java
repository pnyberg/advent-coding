import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Day7 {
	private static int sizeLimit = 100000;
	private static int maxSpace = 70000000;
	private static int neededSpace = 30000000;

	private static Directory manageCd(Directory currentDir, String[] commandParts) {
		switch(commandParts[2]) {
			case "/":
				// go to root
				currentDir = currentDir.root;
				break;
			case "..":
				// go to parent
				currentDir = currentDir.parent;
			default:
				// go to child
				String newDir = commandParts[2];
				for(Directory childDir : currentDir.children) {
					if(childDir.name.equals(newDir)) {
						currentDir = childDir;
						break;
					}
				}
				break;
		}
		return currentDir;
	}

	private static Directory addDirectory(Directory currentDir, String dirName) {
		boolean found = false;
		for(Directory childDir : currentDir.children) {
			if(childDir.name.equals(dirName)) {
				found = true;
				break;
			}
		}
		if(!found) {
			Directory newDir = new Directory(dirName);
			newDir.root = currentDir.root;
			newDir.parent = currentDir;
			currentDir.children.add(newDir);
		}
		return currentDir;
	}

	private static Directory addFile(Directory currentDir, String fileName, int size) {
		boolean found = false;
		for(File file : currentDir.files) {
			if(file.name.equals(fileName)) {
				found = true;
				break;
			}
		}
		if(!found) {
			File newFile = new File(fileName, size);
			currentDir.files.add(newFile);
		}
		return currentDir;
	}

	private static int sumFirst(Directory currentDir) {
		int size = 0;

		int dirSize = currentDir.size();
		if(dirSize <= sizeLimit) {
			size += dirSize;
		}

		for(Directory subDir : currentDir.children) {
			size += Day7.sumFirst(subDir);
		}
		return size;
	}

	private static Directory findSmallestDeleteDirectory(Directory dir, int requiredSpace) {
		Directory smallestDirectory = dir;
		int size = dir.size();
		for(Directory subDir : dir.children) {
			Directory smallestSubDir = Day7.findSmallestDeleteDirectory(subDir, requiredSpace);
			int subDirSize = smallestSubDir.size();
			if(subDirSize < requiredSpace) {
				continue;
			}
			if(subDirSize < size) {
				size = subDirSize;
				smallestDirectory = smallestSubDir;
			}
		}
		return smallestDirectory;
	}

	public static void main(String[] args) {
		// setup input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			Directory currentDir = new Directory("/");
			currentDir.root = currentDir;
			Directory root = currentDir;
			while(true) {
				String line = in.readLine();
				if(line == null) {
					break;
				}

				if(line.charAt(0) == '$') {
					// command
					String[] commandParts = line.split(" ");
					switch(commandParts[1]) {
						case "ls":
							// do nothing
							break;
						case "cd":
							// move directory
							currentDir = Day7.manageCd(currentDir, commandParts);
						default:
							break;
					}
				} else {
					// info
					String[] infoParts = line.split(" ");
					if(infoParts[0].equals("dir")) {
						currentDir = Day7.addDirectory(currentDir, infoParts[1]);
					} else {
						currentDir = Day7.addFile(currentDir, infoParts[1], Integer.parseInt(infoParts[0]));
					}
				}
			}
			int firstSize = Day7.sumFirst(root);
			System.out.println("First: " + firstSize);

			int totalSize		= root.size();
			int availableSpace	= maxSpace - totalSize;
			int requiredSpace	= neededSpace - availableSpace;

			Directory smallestDeletable = Day7.findSmallestDeleteDirectory(root, requiredSpace);
			System.out.println("Second: " + smallestDeletable.size());
		} catch(IOException e) {
			// ignore
		}
	}
}

class Directory {
	public String name;
	public Directory root;
	public Directory parent;
	public ArrayList<Directory> children;
	public ArrayList<File> files;

	public Directory(String name) {
		this.name = name;

		root		= null;
		parent		= null;
		children	= new ArrayList<>();
		files		= new ArrayList<>();
	}

	public int size() {
		int size = 0;
		for(File file : files) {
			size += file.size;
		}
		for(Directory dir : children) {
			size += dir.size();
		}
		return size;
	}
}

class File {
	public String	name;
	public int		size;

	public File(String name, int size) {
		this.name = name;
		this.size = size;
	}
}