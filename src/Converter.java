import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Converter {

	File dataFile;
	Scanner in = new Scanner(System.in);

	public void getDataFile(){
		boolean valid = true;
		do {
			System.out.print("Please enter the name of a file located in the project folder: ");
			try {
				String filename = in.next();
				dataFile = new File(filename);
				dataToCsv(dataFile);
			} catch (Exception e) {
				System.out.printf("File not found. Please try again.\n\n");
				in.nextLine();
				valid = false;
			}
		} while (valid == false);
	}

	public void readURL(String link) throws IOException {
		URL url = new URL(link);					//create a pointer to file on database
		Scanner s = new Scanner(url.openStream());	//create a scanner with url
		File data = new File("temp.data");
		PrintWriter writer = new PrintWriter(data);

		while(s.hasNext()){
			writer.println(s.next());	//prints each line of data file to output file
		}
		s.close();
		writer.close();

		dataToCsv(data);
	}

	public void dataToCsv(File data) {
		File csvFile = new File("output.csv");
		PrintWriter writer = null;
		BufferedReader reader = null;
		try {
			writer = new PrintWriter(csvFile);
			reader = new BufferedReader(new FileReader(data));
		}
		catch (Exception e) {
			System.out.println("Something went wrong generating output file.");		//fix this --> test 1, filename = asdf
		}

		int numAttributes = 0;
		boolean valid = true;
		do {
			try {
				System.out.println("How many attributes? (Not including class)");
				numAttributes = in.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.printf("That's not a number. Try again.\n\n");
				in.nextLine();
				valid = false;
			}
		} while (valid == false);
		for(int i=0; i<numAttributes; i++){
			System.out.println("What is attribute " + (i+1) + "? (No spaces)");
			writer.print(in.next() + ",");
		}
		writer.println("Class");

		String line;
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


		csvToArff(csvFile, numAttributes);
	}

	public void csvToArff(File csvFile, int numAttributes){
        File arffFile = new File("output.arff");
        Scanner s = null;
        PrintWriter writer = null;

        try{
	        s = new Scanner(csvFile);
			writer = new PrintWriter(arffFile);
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        System.out.println("What is the relation?");
		writer.print("@RELATION ");
		writer.println(in.next());
		writer.println();
        
        s.useDelimiter(",");
        for(int i = 0; i < numAttributes; i++){
        	writer.print("@ATTRIBUTE ");
        	writer.print(s.next() + " ");
			System.out.println("What type is attribute " + (i+1) + "?");
			System.out.println("\t1) NUMERIC");
			System.out.println("\t2) INTEGER");
			System.out.println("\t3) REAL");
			System.out.println("\t4) STRING");
			System.out.println("\t5) DATE");

			in.nextLine();
			int selection = in.nextInt();

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
			}
			writer.println(type);
        }
        System.out.println("How many classes are there?");
		int numClasses = in.nextInt();
		writer.print("@ATTRIBUTE CLASS {");
		for(int i=0; i<numClasses; i++){
			System.out.println("What is class " + (i+1) + "?");
			writer.print(in.next());
			if(i != numClasses -1)
				writer.print(",");
		}
		writer.println("}");
		writer.println();
        
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
}
