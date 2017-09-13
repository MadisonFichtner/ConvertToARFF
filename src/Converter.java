import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class Converter {

	File dataFile;

	public void getDataFile(){
		Scanner in = new Scanner(System.in);
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
		in.close();
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

		Scanner in = new Scanner(System.in);
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
		in.close();
		try {
			reader.close();
		} catch (Exception e) {
			System.out.println("Something went wrong trying to close the reader.");
		}


		csvToArff(csvFile);
	}

	public void csvToArff(File csvFile){
        File arffFile = new File("output.arff");

        try {

            // load the CSV file (input file)
            CSVLoader loader = new CSVLoader();
            loader.setSource(csvFile);
            String[] options = new String[2];
            options[0] = "-N";					//forces an attribute to be nominal type
            options[1] = "last";				//forces the last attribute(class) to be nominal
            loader.setOptions(options);

            Instances data = loader.getDataSet();
            System.out.println("Finished writing data to file.");

            // save as an  ARFF (output file)
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(arffFile);
            //saver.setDestination(new File(f2));
            saver.writeBatch();
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
}
