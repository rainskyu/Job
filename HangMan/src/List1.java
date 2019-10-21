import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class List1 extends Thread implements Runnable {
	
	private List<String> list1;
	private List<String> array1;
	
	public List1(){}
	
	
	@SuppressWarnings("resource")
	public void list1() throws IOException{
		Random random = new Random();
		File f = new File("file1.txt");
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String str;
		String words1;
		list1 = new ArrayList<String>();
		array1 = new ArrayList<String>();
		while((str = reader.readLine()) != null){
			list1.add(str);
		}
		for(int i=0;i<50;i++){
			int a=random.nextInt(list1.size());
			array1.add(list1.get(a));
			words1=array1.toString();
			System.out.println(words1);
		}
		serilizeList s = new serilizeList();
		s.list1=array1;
		File byteFile = new File("byteFile.data");
		FileOutputStream stream = new FileOutputStream(byteFile,true);
		ObjectOutputStream object = new ObjectOutputStream(stream);
		object.writeObject(s);
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			list1();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

}
