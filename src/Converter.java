import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

public class Converter {
	public void readURL(String link) throws IOException{
		URL url = new URL(link);					//create a pointer to file on database
		Scanner s = new Scanner(url.openStream());	//create a scanner with url
		PrintWriter writer = new PrintWriter("ouput.ARFF", "UTF-8");
		while(s.hasNext()){
			writer.println(s.next());	//prints each line of data file to output file
		}
		writer.close();
		s.close();
	}
}
