import java.util.Scanner;


/* Machine Learning: Soft Computing Project 1
 * 9/18/2017
 * 
 * The main class controls the flow of the software,
 * which generally looks like:
 * 1) Creates an instance of the converter class
 * 2) Gets input from user either the name of a data file
 * 	  or of a URL and passes that into the converter functions,
 *    which take care of converting and outputting the file.
 *    
 * Authors: Bryan Plant, Madison Fichtner, Nate Tranel
 */
public class Main {
	public static void main(String[] args){
		Converter converter = new Converter();							//create a new instance of the converter
		Scanner in = new Scanner(System.in);							//create a new instance of the scanner

		int selection = 0;												//variable for user choice
		boolean valid = true;											//whether or not user choice is valid, assumes it is for simplicity
		do {
			System.out.println("Do you want to convert data from a file(Enter '1') or from a URL(Enter '2')?");
			try {
				selection = in.nextInt();								//if possible, user input is either 1 or 2
			}
			catch (Exception e) {										//if incorrect input,
				System.out.printf("Invalid input.\n\n");				//tell user,
				in.nextLine();											//clear scanner buffer
				valid = false;											//set valid input flag to false
				continue;												//loop again
			}
			switch (selection) {										//switch on selection choice
				case 1: {
					converter.getDataFile();
					break;
				}
				case 2: {
					boolean valid1 = true;								//if second input (URL) is valid or not, assumes true for simplicity
					do {
						try {
							System.out.println("Enter the URL to the data file.");
							converter.readURL(in.next());				//hopefully input is a correct URL, otherwise
						} catch (Exception e) {
							System.out.printf("That did not work. Check URL and try again.\n\n");
							in.nextLine();								//clear buffer
							valid1 = false;								//indicate bad input and loop again
						}
					} while (valid1 == false);
					break;
				}
				default: {
					System.out.printf("Invalid number.\n\n");			//if user enters integer but not 1 or 2
					valid = false;										//flag it as bad input
				}
			}
		} while (valid == false);										//keep looping until input is valid
		in.close();														//close scanner
	}
}
