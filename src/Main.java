import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Converter converter = new Converter();
		Scanner in = new Scanner(System.in);

		int selection = 0;
		boolean valid = true;
		do {	
			System.out.println("Do you want to convert data from a file(Enter '1') or from a URL(Enter '2')?");
			try {
				selection = in.nextInt();
			}
			catch (Exception e) {
				System.out.printf("Invalid input.\n\n");
				in.nextLine();
				valid = false;
				continue;
			}
			switch (selection) {
				case 1: {
					System.out.println("Enter the name of the file in the project folder");
					converter.getDataFile(in.next());
					break;
				}
				case 2: {
					boolean valid1 = true;
					do {
						try {
							System.out.println("Enter the URL to the data file.");
							converter.readURL(in.next());
						} catch (IOException e) {
							System.out.printf("That did not work. Check URL and try again.\n\n");
							in.nextLine();
							valid1 = false;
						}
					} while (valid1 == false);
					break;
				}
				default: {
					System.out.printf("Invalid number.\n\n");
					valid = false;
				}
			}
		} while (valid == false);
		in.close();
	}
}
