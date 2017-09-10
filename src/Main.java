import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Converter converter = new Converter();
		Scanner in = new Scanner(System.in);

		System.out.println("Do you want to convert data from a file(Enter '1') or from a URL(Enter '2')?");
		int selection = in.nextInt();

		if(selection == 1){
			System.out.println("Enter the name of the file int the project folder");
			converter.getDataFile(in.next());
		}
		else{
			try {
				System.out.println("Enter the URL to the data file.");
				converter.readURL(in.next());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		in.close();
	}
}
