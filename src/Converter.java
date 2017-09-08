import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class Converter {
	File file;
	public void readURL(String link) throws IOException{
		
		file = new File("output.csv");
		
		dataToCsv(link);
		csvToArff();
	}
	
	public void dataToCsv(String link) throws IOException{
		URL url = new URL(link);					//create a pointer to file on database
		Scanner s = new Scanner(url.openStream());	//create a scanner with url
		Scanner in = new Scanner(System.in);
		PrintWriter writer = new PrintWriter(file);
		
		System.out.println("What are the attributes?");
		writer.println(in.next());

		while(s.hasNext()){
			writer.println(s.next());	//prints each line of data file to output file
		}
		writer.close();
		s.close();
		in.close();
	}
	
	public void csvToArff(){
        String f2 = "output.arff";
        
        try{
        	
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        try {

            // load the CSV file (input file)
            CSVLoader loader = new CSVLoader();
            loader.setSource(file);


            Instances data = loader.getDataSet();
            System.out.println(data);

            // save as an  ARFF (output file)
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File(f2));
            //saver.setDestination(new File(f2));
            saver.writeBatch();
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
}
