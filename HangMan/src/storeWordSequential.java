import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class storeWordSequential implements Runnable{

	private String paths1;
	private String paths2;
	private String paths3;
	private String paths4;
	int n;
	private String words1;
	private String words2;
	private String words3;
	private String words4;
	private List<String> list1;
	private List<String> list2;
	private List<String> list3;
	private List<String> list4;
	private List<String> array1;
	private List<String> array2;
	private List<String> array3;
	private List<String> array4;
	
	
	public storeWordSequential(String paths1,String paths2,String paths3,String paths4){
		this.paths1=paths1;
		this.paths2=paths2;
		this.paths3=paths3;
		this.paths4=paths4;
		
	}
	
	public storeWordSequential(){
		
	}
	
	@SuppressWarnings("resource")
	public void inputWords() throws IOException{
		Random random = new Random();
		BufferedReader reader1 = new BufferedReader(new FileReader(paths1));
		BufferedReader reader2 = new BufferedReader(new FileReader(paths2));
		BufferedReader reader3 = new BufferedReader(new FileReader(paths3));
		BufferedReader reader4 = new BufferedReader(new FileReader(paths4));
		serilizeList s = new serilizeList();
		list1 = new ArrayList<String>();
		list2 = new ArrayList<String>();
		list3 = new ArrayList<String>();
		list4 = new ArrayList<String>();
		array1 = new ArrayList<String>();
		array2 = new ArrayList<String>();
		array3 = new ArrayList<String>();
		array4 = new ArrayList<String>();
		String str1;
		String str2;
		String str3;
		String str4;
		
		while((str1 = reader1.readLine()) != null){
			list1.add(str1);
		}
		reader1.close();
		while((str2 = reader2.readLine()) != null){
			list2.add(str2);
		}
		reader2.close();
		while((str3 = reader3.readLine()) != null){
			list3.add(str3);
		}
		reader3.close();
		while((str4 = reader4.readLine()) != null){
			list4.add(str4);
		}
		reader4.close();
		for(int i=0;i<50;i++){
			int a=random.nextInt(list1.size());
			array1.add(list1.get(a));
			words1=array1.toString();
			System.out.println(words1);
		}
		for(int i=0;i<50;i++){
			int a=random.nextInt(list2.size());
			array2.add(list2.get(a));
			words2=array2.toString();
			System.out.println(words2);
		}
		for(int i=0;i<50;i++){
			int a=random.nextInt(list3.size());
			array3.add(list3.get(a));
			words3=array3.toString();
			System.out.println(words3);
		}
		for(int i=0;i<50;i++){
			int a=random.nextInt(list4.size());
			array4.add(list4.get(a));
			words4=array4.toString();
			System.out.println(words4);
		}
		s.list1=array1;
		s.list2=array2;
		s.list3=array3;
		s.list4=array4;
		File byteFile = new File("byteFile.data");
		FileOutputStream stream = new FileOutputStream(byteFile);
		ObjectOutputStream object = new ObjectOutputStream(stream);
		object.writeObject(s);
		object.close();
		stream.close();
	}
	
	public void run(){
		try{
		inputWords();
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
