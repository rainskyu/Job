import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class List4 extends Thread implements Runnable{
	private List<String> list4;
	private List<String> array4;
	
	public List4(){}
	
	
	@SuppressWarnings("resource")
	public void list4() throws IOException{
		Random random = new Random();
		BufferedReader reader = new BufferedReader(new FileReader(new File("file4.txt")));
		String str;
		String words4;
		list4 = new ArrayList<String>();
		array4 = new ArrayList<String>();
		while((str = reader.readLine()) != null){
			list4.add(str);
		}
		for(int i=0;i<50;i++){
			int a=random.nextInt(list4.size());
			array4.add(list4.get(a));
			words4=array4.toString();
			System.out.println(words4);
		}
		serilizeList s = new serilizeList();
		s.list4=array4;
		File byteFile = new File("byteFile.data");
		FileOutputStream stream = new FileOutputStream(byteFile,true);
		ObjectOutputStream object = new ObjectOutputStream(stream);
		object.writeObject(s);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			list4();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
