import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;


public class resource{
	

	public static void save(Serializable data, String filename) throws Exception {
		try(ObjectOutputStream oj = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))){
			oj.writeObject(data);
		}
	}
	
	public static Object load(String filename) throws Exception{
		try(ObjectInputStream oj = new ObjectInputStream(Files.newInputStream(Paths.get(filename)))){
			return oj.readObject();
		}
	}

}
