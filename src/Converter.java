import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class Converter {

	File dataFile;

	public void getDataFile(String filename){
		try {
			dataFile = new File(filename);
			dataToCsv(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readURL(String link) throws IOException{
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

	public void dataToCsv(File data) throws IOException{
		File csvFile = new File("output.csv");
		PrintWriter writer = new PrintWriter(csvFile);
		BufferedReader reader = new BufferedReader(new FileReader(data));

		Scanner in = new Scanner(System.in);

		System.out.println("How many attributes? (Not including class)");
		int numAttributes = in.nextInt();
		for(int i=0; i<numAttributes; i++){
			System.out.println("What is attribute " + (i+1) + "? (No spaces)");
			writer.print(in.next() + ",");
		}
		writer.println("Class");

		String line;
		while((line = reader.readLine()) != null){
			writer.println(line);
		}

		writer.close();
		in.close();


		csvToArff(csvFile);
	}

	public void csvToArff(File csvFile){
        File arffFile = new File("output.arff");

        try{

        }
        catch(Exception e){
        	e.printStackTrace();
        }

        try {

            // load the CSV file (input file)
            CSVLoader loader = new CSVLoader();
            loader.setSource(csvFile);


            Instances data = loader.getDataSet();
            System.out.println(data);

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
