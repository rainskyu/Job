import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class List2 extends Thread implements Runnable{
	
	private List<String> list2;
	private List<String> array2;
	
	public List2(){}
	
	
	@SuppressWarnings("resource")
	public void list2() throws IOException{
		Random random = new Random();
		BufferedReader reader = new BufferedReader(new FileReader(new File("file2.txt")));
		String str;
		String words2;
		list2 = new ArrayList<String>();
		array2 = new ArrayList<String>();
		while((str = reader.readLine()) != null){
			list2.add(str);
		}
		for(int i=0;i<50;i++){
			int a=random.nextInt(list2.size());
			array2.add(list2.get(a));
			words2=array2.toString();
			System.out.println(words2);
		}
		serilizeList s = new serilizeList();
		s.list2=array2;
		File byteFile = new File("byteFile.data");
		FileOutputStream stream = new FileOutputStream(byteFile,true);
		ObjectOutputStream object = new ObjectOutputStream(stream);
		object.writeObject(s);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			list2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
