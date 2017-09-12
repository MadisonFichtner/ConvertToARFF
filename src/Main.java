import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Converter converter = new Converter();
		Scanner in = new Scanner(System.in);

		System.out.println("Do you want to convert data from a file(Enter '1') or from a URL(Enter '2')?");
		int selection = in.nextInt();
		switch (selection) {
			case 1: {
				System.out.println("Enter the name of the file in the project folder");
				converter.getDataFile(in.next());
			}
			case 2: {
				try {
					System.out.println("Enter the URL to the data file.");
					converter.readURL(in.next());
				} catch (IOException e) {
					System.out.println("That did not work. Check URL and try again.");
				}
			}
			default: System.out.println("Incorrect input.");
		}
		in.close();
	}
}
