import java.io.IOException;

public class Main {
	public static void main(String[] args){
		Converter converter = new Converter();
		try {
			converter.readURL("http://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
