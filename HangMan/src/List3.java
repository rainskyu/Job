import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class List3 extends Thread implements Runnable{
	
	private List<String> list3;
	private List<String> array3;
	
	public List3(){}
	
	
	@SuppressWarnings("resource")
	public void list3() throws IOException{
		Random random = new Random();
		BufferedReader reader = new BufferedReader(new FileReader(new File("file3.txt")));
		String str;
		String words3;
		list3 = new ArrayList<String>();
		array3 = new ArrayList<String>();
		while((str = reader.readLine()) != null){
			list3.add(str);
		}
		for(int i=0;i<50;i++){
			int a=random.nextInt(list3.size());
			array3.add(list3.get(a));
			words3=array3.toString();
			System.out.println(words3);
		}
		serilizeList s = new serilizeList();
		s.list3=array3;
		File byteFile = new File("byteFile.data");
		FileOutputStream stream = new FileOutputStream(byteFile,true);
		ObjectOutputStream object = new ObjectOutputStream(stream);
		object.writeObject(s);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			list3();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
