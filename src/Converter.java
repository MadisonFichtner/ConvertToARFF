import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class Converter {
	public void readURL(String link) throws IOException{
		URL url = new URL(link);					//create a pointer to file on database
		Scanner s = new Scanner(url.openStream());	//create a scanner with url
		Scanner in = new Scanner(System.in);
		PrintWriter writer = new PrintWriter("ouput.arff", "UTF-8");

		System.out.println("What is the relation?");
		writer.println("@RELATION " + in.nextLine());
		writer.println();
		System.out.println("How many attributes?");
		int numAttributes = in.nextInt();
		for(int i = 0; i < numAttributes; i++){
			System.out.println("What is attribute " + (i+1) + "?");
			String attribute = in.next();
			System.out.println("What type is the attribute?(Not including class)");
			System.out.println("\t1) NUMERIC");
			System.out.println("\t2) INTEGER");
			System.out.println("\t3) REAL");
			System.out.println("\t4) STRING");
			System.out.println("\t5) DATE");
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
			writer.println("@ATTRIBUTE " + attribute + " "+ type);
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
		writer.println("@DATA");

		while(s.hasNext()){
			writer.println(s.next());	//prints each line of data file to output file
		}
		writer.close();
		s.close();
		in.close();

		/*String f1 = "output.csv";
        String f2 = "C:/Users/Bryan/Downloads/new.arff";

        try{
        // load CSV
        CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(f1));
	    Instances data = loader.getDataSet();

	    // save ARFF
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(f2));
	    saver.writeBatch();
        }
        catch(Exception e){}*/
	}
}
