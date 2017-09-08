import java.io.IOException;

public class Main {
	public static void main(String[] args){
		Converter converter = new Converter();
		try {
			converter.readURL("http://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data");
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*String f1 = "C:/Users/Bryan/Downloads/iris.csv";
        String f2 = "C:/Users/Bryan/Downloads/iris.arff";
        
        try{
        // load CSV
        CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(f1));
	    Instances data = loader.getDataSet();
	   
	    // save ARFF
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(f2));
	    //saver.setDestination(new File(f2));
	    saver.writeBatch();
        }
        catch(Exception e){}*/
	}
}
