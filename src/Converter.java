import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * The Converter class receives data in the form of a filename or
 * web URL
 * Converts this data into a file with a header which is then converted
 * to an ARFF file to be read by WEKA
 */
public class Converter {

	Scanner in = new Scanner(System.in);

	/*
	 * Prompts user for filename then passes the file to dataToCsv
	 */
	public void getDataFile(){
		File dataFile = null;
		BufferedReader reader = null;
		boolean valid = true;																					//flag for input validity
		do {
			try {
				System.out.print("Please enter the name of a file located in the project folder: ");
				String filename = in.next();	
				dataFile = new File(filename);
				reader = new BufferedReader(new FileReader(dataFile));									
				valid = true;																					//assume file process works, but if not
			}
			catch (Exception e) {																				//catch the exception thrown, print error message,
				System.out.printf("That file was not found. Check and try again.\n\n");
				in.nextLine();																					//clear buffer
				valid = false;																					//flag input for repeated process -> prompt user again
			}
		} while (valid == false);
		dataToCsv(reader);																						//send data file to the next method
	}

	/*
	 * Receives a web URL to a data file, writes the data to a file
	 * and passes the file to dataToCsv
	 * @param link A string containing the URL of data file
	 */
	public void readURL(String link) throws IOException {
		URL url = new URL(link);					//create a pointer to file on database
		Scanner s = new Scanner(url.openStream());	//create a scanner with url
		File data = new File("temp.data");
		PrintWriter writer = new PrintWriter(data);

		while(s.hasNext()){
			writer.println(s.next());				//prints each line of data file to output file
		}
		s.close();
		writer.close();
		BufferedReader reader = new BufferedReader(new FileReader(data));
		dataToCsv(reader);
	}

	/*
	 * Converts data file to a csv file with a header containing attribute information
	 * @param data The file containing the data to work with
	 */
	private void dataToCsv(BufferedReader reader) {
		File csvFile = new File("output.csv");											//create output file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(csvFile);											//create writer for printing to file
		}
		catch (Exception e) {
			System.out.println("Something went wrong generating output file.");
		}

		int numAttributes = 0;
		boolean valid = true;															//flag for input validity
		do {																			//prompt user for number of attributes
			try {
				System.out.println("How many attributes? (Not including class)");
				numAttributes = in.nextInt();
				valid = true;															//assume input is valid, but if not
			}
			catch (InputMismatchException e) {
				System.out.printf("That's not a number. Try again.\n\n");
				in.nextLine();															//clear buffer, flag and prompt again
				valid = false;
			}
		} while (valid == false);

		//prompt user for attribute names and write to file
		for(int i=0; i<numAttributes; i++){
			System.out.println("What is attribute " + (i+1) + "?");
			writer.print(in.next() + ",");
		}
		writer.println("Class");														//end with "Class" for ARFF file

		String line;
		//write data to file
		try {
			while((line = reader.readLine()) != null){
				writer.println(line);
			}
		} catch (Exception e) {
			System.out.println("Cannot read line. Something went wrong.");
		}

		writer.close();
		try {
			reader.close();
		} catch (Exception e) {
			System.out.println("Something went wrong trying to close the reader.");
		}
		
		csvToArff(csvFile, numAttributes);												//then convert CSV file to ARFF file
	}

	/*
	 * Converts file with header to arff file with appropriate header
	 * @param csvFile The csv file to be converted
	 * @param numAttributes The number of attributes in the header of the file
	 */
	private void csvToArff(File csvFile, int numAttributes){
        File arffFile = new File("output.arff");
        Scanner s = null;
        PrintWriter writer = null;

        try {
	        s = new Scanner(csvFile);
			writer = new PrintWriter(arffFile);
        }
        catch(Exception e){
        	System.out.println("Something went wrong.");
        }

        //prompt user for relation and write to header
        System.out.println("What is the relation?");
		writer.print("@RELATION ");
		writer.println(in.next());
		writer.println();

		//read header line of csv file and print attribute information to header of arff file
        s.useDelimiter(",");
        for(int i = 0; i < numAttributes; i++){
        	String type = getAttributeType(i);
        	writer.println("@ATTRIBUTE " + s.next() + " " + type);
        }

        //prints out class attribute
        int numClasses = 0;
        boolean valid = true;
        do {
	        try {
		        System.out.println("How many classes are there?");
				numClasses = in.nextInt();
				valid = true;
	        }
	        catch (Exception e) {
	        	System.out.println("That is not a number.\n");
	        	in.nextLine();
	        	valid = false;
	        }
        } while (valid == false);
		writer.print("@ATTRIBUTE CLASS {");
		for(int i=0; i<numClasses; i++){
			System.out.println("What is class " + (i+1) + "?");
			writer.print(in.next());
			if(i != numClasses-1)
				writer.print(",");
		}

		writer.println("}\n");

        s.useDelimiter(" ");
        s.skip(",Class");

		writer.print("@DATA");
		while(s.hasNext()){
			writer.println(s.next());	//prints each line of data file to output file
		}

		writer.close();
		s.close();
		in.close();

	}

	/*
	 * Prompts the user for the type of an attribute
	 * @param number The attribute number
	 * @return type The string describing the attribute type
	 */
	String getAttributeType(int number){
		int selection = 0;
		boolean valid = true;
		do {
			try {
				System.out.println("What type is attribute " + (number+1) + "?");
				System.out.println("\t1) NUMERIC");
				System.out.println("\t2) INTEGER");
				System.out.println("\t3) REAL");
				System.out.println("\t4) STRING");
				System.out.println("\t5) DATE");
				selection = in.nextInt();
				valid = true;
			}
			catch (Exception e) {
				System.out.println("That is not a number.\n");
				in.nextLine();
				valid = false;
			}
		} while (valid == false);

		String type = "NUMERIC";
		switch(selection){
		case 1:
			type = "NUMERIC";
			break;
		case 2:
			type = "INTEGER";
			break;
		case 3:
			type = "REAL";
			break;
		case 4:
			type = "STRING";
			break;
		case 5:
			type = "DATE";
			break;
		default:
			System.out.printf("Out of range. Defaulting to 1.\n\n");
		}
		return type;
	}
}
