import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class hangManButtonController {
	
	@FXML
	private RadioButton sequential;
	@FXML
	private RadioButton parallel;
	@FXML
	private Button submit;
	@FXML
	private RadioButton level1;
	@FXML
	private RadioButton level2;
	@FXML
	private RadioButton level3;
	@FXML
	private RadioButton level4;
	@FXML
	private Button Confirm;
	/*@FXML
	private TextArea textArea;*/
	@FXML
	private TextArea save;
	@FXML
	private Button changeWord;
	@FXML
	private ImageView start_image;
	@FXML
	private ImageView win_image;
	@FXML
	private ImageView loss_image;
	@FXML
	private ImageView first_guess_wrong_image;
	@FXML
	private ImageView second_guess_wrong_image;
	@FXML
	private ImageView third_guess_wrong_image;
	@FXML
	private ImageView fourth_guess_wrong_image;
	@FXML
	private ImageView fifth_guess_wrong_image;
	
	
	@FXML
	private Button play;
	
	@FXML	
	private Button load;
	@FXML
	private Button savegame;
	
	@FXML
	public void play() throws IOException, ClassNotFoundException{
		Stage stage; 
		stage=(Stage) play.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
        stage.setScene(scene);
        stage.setTitle("HangMan");
        stage.setResizable(false);
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "resource" })
	@FXML
	public void changeWord() throws ClassNotFoundException, IOException{
		String currentDir=System.getProperty("user.dir");
    	String paths=currentDir+"//byteFile.data";
    	serilizeList l = new serilizeList();
    	File readword = new File(paths);
    	FileInputStream a = new FileInputStream(readword);
    	ObjectInputStream b = new ObjectInputStream(a);
    	l=(serilizeList) b.readObject();
    	List<String> ab= l.list1;
    	List<String> cd =l.list2;
    	List<String> ef =l.list3;
    	List<String> gh =l.list4;
    	int aba = ab.get(0).length();
    	int cdc = cd.get(0).length();
    	int efe = ef.get(0).length();
    	int ghg = gh.get(0).length();
    	
    	if(level1.isSelected()&&changeWord.isPressed()&&aba<cdc&&aba<efe&&aba<ghg){
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(ab.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<ab.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
    		savegame.setDisable(false);
    		}else
    	if(level1.isSelected()&&changeWord.isPressed()&&cdc<aba&&cdc<efe&&cdc<ghg){
            	Random random = new Random();
        		int ac =random.nextInt(50);
        		File store = new File(currentDir,"palyword.data");
        		store.delete();
        		if(!store.exists()){
        			store.createNewFile();
        		}
        		OutputStream outStream = new FileOutputStream(store);
    			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
        		moos.writeObject(cd.get(ac));
        		int n = 0;
        		String temp="";
        		while(n<cd.get(ac).length()){
        			temp = temp+"*";
        			n++;
        		}
        		save.setText(temp);
        		savegame.setDisable(false);
        		}else
    	if(level1.isSelected()&&changeWord.isPressed()&&efe<aba&&efe<cdc&&efe<ghg){
            	Random random = new Random();
        		int ac =random.nextInt(50);
        		File store = new File(currentDir,"palyword.data");
        		store.delete();
        		if(!store.exists()){
        			store.createNewFile();
        		}
        		OutputStream outStream = new FileOutputStream(store);
    			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
        		moos.writeObject(ef.get(ac));
        		int n = 0;
        		String temp="";
        		while(n<ef.get(ac).length()){
        			temp = temp+"*";
        			n++;
        		}
        		save.setText(temp);
        		savegame.setDisable(false);
        		}else
    	if(level1.isSelected()&&changeWord.isPressed()&&ghg<efe&&ghg<aba&&ghg<cdc){
            	Random random = new Random();
        		int ac =random.nextInt(50);
        		File store = new File(currentDir,"palyword.data");
        		store.delete();
        		if(!store.exists()){
        			store.createNewFile();
        		}
        		OutputStream outStream = new FileOutputStream(store);
    			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
        		moos.writeObject(gh.get(ac));
        		int n = 0;
        		String temp="";
        		while(n<gh.get(ac).length()){
        			temp = temp+"*";
        			n++;
        		}
        		save.setText(temp);
        		savegame.setDisable(false);
        		}else
      if(level2.isSelected()&&changeWord.isPressed()&&((aba>cdc&&aba<efe&&aba<ghg)||(aba>efe&&aba<cdc&&aba<ghg)||(aba>ghg&&aba<efe&&aba<cdc))){
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(ab.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<ab.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(cd.get(ac));
    		savegame.setDisable(false);
    		}else
      if(level2.isSelected() && changeWord.isPressed()&&((cdc>aba&&cdc<efe&&cdc<ghg)||(cdc>efe&&cdc<aba&&cdc<ghg)||(cdc>ghg&&cdc<efe&&cdc<aba))){
          	Random random = new Random();
      		int ac =random.nextInt(50);
      		File store = new File(currentDir,"palyword.data");
      		store.delete();
      		if(!store.exists()){
      			store.createNewFile();
      		}
      		OutputStream outStream = new FileOutputStream(store);
  			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
      		moos.writeObject(cd.get(ac));
      		int n = 0;
      		String temp="";
      		while(n<cd.get(ac).length()){
      			temp = temp+"*";
      			n++;
      		}
      		save.setText(temp);
      		savegame.setDisable(false);
      		}else
    	 if(level2.isSelected()&&changeWord.isPressed()&&((efe>aba&&efe<cdc&&efe<ghg)||(efe>cdc&&efe<aba&&efe<ghg)||(efe>ghg&&efe<aba&&efe<cdc))){
              	Random random = new Random();
          		int ac =random.nextInt(50);
          		File store = new File(currentDir,"palyword.data");
          		store.delete();
          		if(!store.exists()){
          			store.createNewFile();
          		}
          		OutputStream outStream = new FileOutputStream(store);
      			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
          		moos.writeObject(ef.get(ac));
          		
          		int n = 0;
          		String temp="";
          		while(n<ef.get(ac).length()){
          			temp = temp+"*";
          			n++;
          		}
          		save.setText(temp);
          		savegame.setDisable(false);
          		}else
    	  if(level2.isSelected()&&changeWord.isPressed()&&((ghg>aba&&ghg<cdc&&ghg<efe)||(ghg>cdc&&ghg<aba&&ghg<efe)||(ghg>efe&&ghg<aba&&ghg<cdc))){
              	Random random = new Random();
          		int ac =random.nextInt(50);
          		File store = new File(currentDir,"palyword.data");
          		store.delete();
          		if(!store.exists()){
          			store.createNewFile();
          		}
          		OutputStream outStream = new FileOutputStream(store);
      			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
          		moos.writeObject(gh.get(ac));
          		
          		int n = 0;
          		String temp="";
          		while(n<gh.get(ac).length()){
          			temp = temp+"*";
          			n++;
          		}
          		save.setText(temp);
          		savegame.setDisable(false);
          		}else
		if(level3.isSelected()&&changeWord.isPressed()&&((aba>cdc&&aba>efe&&aba<ghg)||(aba>efe&&aba>ghg&&aba<cdc)||(aba>ghg&&aba>cdc&&aba<efe))){
			
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(ab.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<ab.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(ef.get(ac));
    		savegame.setDisable(false);
    		}else
		if(level3.isSelected()&&changeWord.isPressed()&&((cdc>aba&&cdc>efe&&cdc<ghg)||(cdc>efe&&cdc>ghg&&cdc<aba)||(cdc>ghg&&aba>aba&&cdc<efe))){
			
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(cd.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<cd.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(ef.get(ac));
    		savegame.setDisable(false);
    		}else
		if(level3.isSelected()&&changeWord.isPressed()&&((efe>aba&&efe>cdc&&efe<ghg)||(efe>cdc&&efe>ghg&&efe<aba)||(efe>ghg&&efe>aba&&efe<cdc))){
			
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(ef.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<ef.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(ef.get(ac));
    		savegame.setDisable(false);
    		}else
		if(level3.isSelected()&&changeWord.isPressed()&&((ghg>aba&&ghg>efe&&ghg<cdc)||(ghg>efe&&ghg>cdc&&ghg<aba)||(ghg>cdc&&ghg>aba&&ghg<efe))){
			
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(gh.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<gh.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(ef.get(ac));
    		savegame.setDisable(false);
    		}else
		if(level4.isSelected()&&changeWord.isPressed()&&aba>cdc&&aba>efe&&aba>ghg){
			
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(ab.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<ab.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(gh.get(ac));
    		savegame.setDisable(false);
    		}else
		if(level4.isSelected()&&changeWord.isPressed()&&cdc>aba&&cdc>efe&&cdc>ghg){
			
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(cd.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<cd.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(gh.get(ac));
    		savegame.setDisable(false);
    		}else
		if(level4.isSelected()&&changeWord.isPressed()&&efe>cdc&&efe>aba&&efe>ghg){
			
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(ef.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<ef.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(gh.get(ac));
    		savegame.setDisable(false);
    		}else
		if(level4.isSelected()&&changeWord.isPressed()&ghg>cdc&&ghg>efe&&ghg>aba){
			
        	Random random = new Random();
    		int ac =random.nextInt(50);
    		File store = new File(currentDir,"palyword.data");
    		store.delete();
    		if(!store.exists()){
    			store.createNewFile();
    		}
    		OutputStream outStream = new FileOutputStream(store);
			MyObjectOutputStream moos = MyObjectOutputStream.newInstance(store, outStream);
    		moos.writeObject(gh.get(ac));
    		int n = 0;
    		String temp="";
    		while(n<gh.get(ac).length()){
    			temp = temp+"*";
    			n++;
    		}
    		save.setText(temp);
        	//save.setText(gh.get(ac));
    		savegame.setDisable(false);
    		}
		buttonA.setDisable(false);
		buttonB.setDisable(false);
		buttonC.setDisable(false);
		buttonD.setDisable(false);
		buttonE.setDisable(false);
		buttonF.setDisable(false);
		buttonG.setDisable(false);
		buttonH.setDisable(false);
		buttonI.setDisable(false);
		buttonJ.setDisable(false);
		buttonK.setDisable(false);
		buttonL.setDisable(false);
		buttonM.setDisable(false);
		buttonN.setDisable(false);
		buttonO.setDisable(false);
		buttonP.setDisable(false);
		buttonQ.setDisable(false);
		buttonR.setDisable(false);
		buttonS.setDisable(false);
		buttonT.setDisable(false);
		buttonU.setDisable(false);
		buttonV.setDisable(false);
		buttonW.setDisable(false);
		buttonX.setDisable(false);
		buttonY.setDisable(false);
		buttonZ.setDisable(false);
		container_start.setVisible(true);
		container_first_guess_wrong.setVisible(false);
		container_second_guess_wrong.setVisible(false);
		container_third_guess_wrong.setVisible(false);
		container_fourth_guess_wrong.setVisible(false);
		container_fifth_guess_wrong.setVisible(false);
		container_win.setVisible(false);
		container_lose.setVisible(false);
		Gamerun=0;
     }
	

	/*@FXML
	private Label lb;
	@FXML
	private Button play2;
	@FXML
	private BorderPane pane;*/

	/*@SuppressWarnings("resource")
	@FXML
	public void loading() throws Exception{

		File read = new File("result.txt");
		FileReader re = new FileReader(read);
		BufferedReader r = new BufferedReader(re);
		boolean check= true;
		while(check){
			if(r.readLine()==null){
				//lb1.setVisible(false);
				lb.setText("Loading done");
				play2.setVisible(true);
				check=false;
			}
		}
	}*/
	/*@FXML
	public void play2() throws IOException{
		Stage stage; 
		stage=(Stage) play2.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("hangman.fxml"));
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Wordhandler");
        stage.setResizable(false);
	}*/
	
	@SuppressWarnings("unused")
	@FXML
	public void submitEvent() throws IOException, InterruptedException{
		boolean check=true;
        if(parallel.isSelected()&&submit.isPressed()){
    		Stage stage;
    		stage =(Stage) submit.getScene().getWindow();
    		Label label = new Label("Loading The Words...");
    		label.setFont(new Font(15));
    		BorderPane pane = new BorderPane();
    		pane.setPrefWidth(300);
    		pane.setPrefHeight(100);
    		pane.setCenter(label);
    		Button play = new Button("PLAY");
    		play.setVisible(false);
    		pane.setBottom(play);
    		BorderPane.setAlignment(play, Pos.BASELINE_CENTER);
    		Scene scene = new Scene(pane);
    		stage.setScene(scene);
    		stage.setTitle("Loading");
            stage.setResizable(false);
            stage.show();
            play.setOnMousePressed((event) ->{
            	Stage stage2; 
    			stage2=(Stage) play.getScene().getWindow();
    			try{
    			Parent root = FXMLLoader.load(getClass().getResource("hangman.fxml"));
    			Scene scene2 = new Scene(root);
    	        stage2.setScene(scene2);
    	        stage2.setTitle("Wordhandler");
    	        stage2.setResizable(false);
    	        stage2.show();
    			}catch(Exception e){
    				e.printStackTrace();
    			}
            });
        	/*Stage stage2;
    		stage2 =(Stage) submit.getScene().getWindow();
    		Parent root2 = FXMLLoader.load(getClass().getResource("LoadingYourWords.fxml"));
    		Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.setTitle("Loading");
            stage2.setResizable(false);
            stage2.show();*/
            String currentDir=System.getProperty("user.dir");
    		String[] file = {"file1.txt",
    				         "file2.txt",
    				         "file3.txt",
    				         "file4.txt"};
    		ExecutorService exe = Executors.newFixedThreadPool(4);
    		long startTime=System.currentTimeMillis();
            exe.execute(new List1()); 
            exe.execute(new List2());
            exe.execute(new List3());
            exe.execute(new List4());
            exe.shutdown();
           while (true) {  
            if (exe.isTerminated()) {
            	long endTime=System.currentTimeMillis();
            	String time =String.valueOf(endTime-startTime);
                label.setText("Loading Done. "+" Used "+time+"millionseconds");
                label.setFont(new Font(12));
                play.setVisible(true);
                check=false;
                break;
            }  
           }
        }else
        	if(sequential.isSelected()&&submit.isPressed()){
        		Stage stage;
        		stage =(Stage) submit.getScene().getWindow();
        		Label label = new Label("Loading The Words...");
        		label.setFont(new Font(15));
        		BorderPane pane = new BorderPane();
        		pane.setPrefWidth(300);
        		pane.setPrefHeight(100);
        		pane.setCenter(label);
        		Button play = new Button("PLAY");
        		play.setVisible(false);
        		pane.setBottom(play);
        		BorderPane.setAlignment(play, Pos.BASELINE_CENTER);
        		Scene scene = new Scene(pane);
        		stage.setScene(scene);
        		stage.setTitle("Loading");
                stage.setResizable(false);
                stage.show();
                play.setOnMousePressed((event) ->{
                	Stage stage2; 
        			stage2=(Stage) play.getScene().getWindow();
        			try{
        			Parent root = FXMLLoader.load(getClass().getResource("hangman.fxml"));
        			Scene scene2 = new Scene(root);
        	        stage2.setScene(scene2);
        	        stage2.setTitle("Wordhandler");
        	        stage2.setResizable(false);
        	        stage2.show();
        			}catch(Exception e){
        				e.printStackTrace();
        			}
                });
        	/*	
        	Stage stage2;
        	stage2 =(Stage) submit.getScene().getWindow();
    		Parent root2 = FXMLLoader.load(getClass().getResource("LoadingYourWords.fxml"));
    		Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.setTitle("Loading");
            stage2.setResizable(false);
            stage2.show();*/
                String currentDir=System.getProperty("user.dir");
    			String[] file = {"file1.txt",
    			         "file2.txt",
    			         "file3.txt",
    			         "file4.txt"};
    			ExecutorService exe = Executors.newFixedThreadPool(200);
    			long startTime=System.currentTimeMillis();
    			storeWordSequential a = new storeWordSequential("file1.txt","file2.txt","file3.txt","file4.txt");
    			exe.execute(a); 
    			exe.shutdown();
        	    while (true) {  
                    if (exe.isTerminated()) {  
                    	long endTime=System.currentTimeMillis();
                    	String time =String.valueOf(endTime-startTime);
                        label.setText("Loading Done. "+" Used "+time+"millionseconds");
                        label.setFont(new Font(12));
                         play.setVisible(true);
                        check=false;
                        break;
                    }  
                    Thread.sleep(200);  
                 }

        }else
    		if(submit.isPressed()){
    			Stage stage; 
    			stage=(Stage) submit.getScene().getWindow();
    			Parent root = FXMLLoader.load(getClass().getResource("hangman.fxml"));
    			Scene scene = new Scene(root);
    	        stage.setScene(scene);
    	        stage.setTitle("Wordhandler");
    	        stage.setResizable(false);
    	        stage.show();
    			}
        }
	
	@FXML
	private Button buttonA;
	@FXML
	private Button buttonB;
	@FXML
	private Button buttonC;
	@FXML
	private Button buttonD;
	@FXML
	private Button buttonE;
	@FXML
	private Button buttonF;
	@FXML
	private Button buttonG;
	@FXML
	private Button buttonH;
	@FXML
	private Button buttonI;
	@FXML
	private Button buttonJ;
	@FXML
	private Button buttonK;
	@FXML
	private Button buttonL;
	@FXML
	private Button buttonM;
	@FXML
	private Button buttonN;
	@FXML
	private Button buttonO;
	@FXML
	private Button buttonP;
	@FXML
	private Button buttonQ;
	@FXML
	private Button buttonR;
	@FXML
	private Button buttonS;
	@FXML
	private Button buttonT;
	@FXML
	private Button buttonU;
	@FXML
	private Button buttonV;
	@FXML
	private Button buttonW;
	@FXML
	private Button buttonX;
	@FXML
	private Button buttonY;
	@FXML
	private Button buttonZ;
	
	@FXML
	private Button mainpage;
	
	@FXML
	private void mainpage() throws IOException{
		Stage stage; 
		stage=(Stage) mainpage.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Wordhandler");
        stage.setResizable(false);
        stage.show();
	}
	
	
	
	
	
	
	private int Gamerun=0;
	@FXML
	public void savegame() throws IOException{
		Savegame savegame = new Savegame();
		int round = Gamerun;
		savegame.gamerun=round;
		switch(Gamerun){
		case 0:
			container_start.setVisible(true);
			boolean start_image = container_start.isVisible();
			savegame.start_image=start_image;
			boolean first_guess_wrong_image=container_first_guess_wrong.isVisible();
			savegame.first_guess_wrong_image=first_guess_wrong_image;
			boolean second_guess_wrong_image=container_second_guess_wrong.isVisible();
			savegame.second_guess_wrong_image=second_guess_wrong_image;
			boolean third_guess_wrong_image=container_third_guess_wrong.isVisible();
			savegame.third_guess_wrong_image=third_guess_wrong_image;
			boolean fourth_guess_wrong_image=container_fourth_guess_wrong.isVisible();
			savegame.fourth_guess_wrong_image=fourth_guess_wrong_image;
			boolean fifth_guess_wrong_image=container_fifth_guess_wrong.isVisible();
			savegame.fifth_guess_wrong_image=fifth_guess_wrong_image;
			boolean loss_image=container_lose.isVisible();
			savegame.loss_image=loss_image;
			boolean win_image=container_win.isVisible();
			savegame.win_image=win_image;
			break;
		case 1:
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(true);
			start_image = container_start.isVisible();
			savegame.start_image=start_image;
			first_guess_wrong_image=container_first_guess_wrong.isVisible();
			savegame.first_guess_wrong_image=first_guess_wrong_image;
			second_guess_wrong_image=container_second_guess_wrong.isVisible();
			savegame.second_guess_wrong_image=second_guess_wrong_image;
			third_guess_wrong_image=container_third_guess_wrong.isVisible();
			savegame.third_guess_wrong_image=third_guess_wrong_image;
			fourth_guess_wrong_image=container_fourth_guess_wrong.isVisible();
			savegame.fourth_guess_wrong_image=fourth_guess_wrong_image;
			fifth_guess_wrong_image=container_fifth_guess_wrong.isVisible();
			savegame.fifth_guess_wrong_image=fifth_guess_wrong_image;
			loss_image=container_lose.isVisible();
			savegame.loss_image=loss_image;
			win_image=container_win.isVisible();
			savegame.win_image=win_image;
			break;
		case 2:
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(true);
			start_image = container_start.isVisible();
			savegame.start_image=start_image;
			first_guess_wrong_image=container_first_guess_wrong.isVisible();
			savegame.first_guess_wrong_image=first_guess_wrong_image;
			second_guess_wrong_image=container_second_guess_wrong.isVisible();
			savegame.second_guess_wrong_image=second_guess_wrong_image;
			third_guess_wrong_image=container_third_guess_wrong.isVisible();
			savegame.third_guess_wrong_image=third_guess_wrong_image;
			fourth_guess_wrong_image=container_fourth_guess_wrong.isVisible();
			savegame.fourth_guess_wrong_image=fourth_guess_wrong_image;
			fifth_guess_wrong_image=container_fifth_guess_wrong.isVisible();
			savegame.fifth_guess_wrong_image=fifth_guess_wrong_image;
			loss_image=container_lose.isVisible();
			savegame.loss_image=loss_image;
			win_image=container_win.isVisible();
			savegame.win_image=win_image;
			break;
		case 3:
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(true);
			start_image = container_start.isVisible();
			savegame.start_image=start_image;
			first_guess_wrong_image=container_first_guess_wrong.isVisible();
			savegame.first_guess_wrong_image=first_guess_wrong_image;
			second_guess_wrong_image=container_second_guess_wrong.isVisible();
			savegame.second_guess_wrong_image=second_guess_wrong_image;
			third_guess_wrong_image=container_third_guess_wrong.isVisible();
			savegame.third_guess_wrong_image=third_guess_wrong_image;
			fourth_guess_wrong_image=container_fourth_guess_wrong.isVisible();
			savegame.fourth_guess_wrong_image=fourth_guess_wrong_image;
			fifth_guess_wrong_image=container_fifth_guess_wrong.isVisible();
			savegame.fifth_guess_wrong_image=fifth_guess_wrong_image;
			loss_image=container_lose.isVisible();
			savegame.loss_image=loss_image;
			win_image=container_win.isVisible();
			savegame.win_image=win_image;
			break;
		case 4:
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(true);
			start_image = container_start.isVisible();
			savegame.start_image=start_image;
			first_guess_wrong_image=container_first_guess_wrong.isVisible();
			savegame.first_guess_wrong_image=first_guess_wrong_image;
			second_guess_wrong_image=container_second_guess_wrong.isVisible();
			savegame.second_guess_wrong_image=second_guess_wrong_image;
			third_guess_wrong_image=container_third_guess_wrong.isVisible();
			savegame.third_guess_wrong_image=third_guess_wrong_image;
			fourth_guess_wrong_image=container_fourth_guess_wrong.isVisible();
			savegame.fourth_guess_wrong_image=fourth_guess_wrong_image;
			fifth_guess_wrong_image=container_fifth_guess_wrong.isVisible();
			savegame.fifth_guess_wrong_image=fifth_guess_wrong_image;
			loss_image=container_lose.isVisible();
			savegame.loss_image=loss_image;
			win_image=container_win.isVisible();
			savegame.win_image=win_image;
			break;
		case 5:
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(true);
			start_image = container_start.isVisible();
			savegame.start_image=start_image;
			first_guess_wrong_image=container_first_guess_wrong.isVisible();
			savegame.first_guess_wrong_image=first_guess_wrong_image;
			second_guess_wrong_image=container_second_guess_wrong.isVisible();
			savegame.second_guess_wrong_image=second_guess_wrong_image;
			third_guess_wrong_image=container_third_guess_wrong.isVisible();
			savegame.third_guess_wrong_image=third_guess_wrong_image;
			fourth_guess_wrong_image=container_fourth_guess_wrong.isVisible();
			savegame.fourth_guess_wrong_image=fourth_guess_wrong_image;
			fifth_guess_wrong_image=container_fifth_guess_wrong.isVisible();
			savegame.fifth_guess_wrong_image=fifth_guess_wrong_image;
			loss_image=container_lose.isVisible();
			savegame.loss_image=loss_image;
			win_image=container_win.isVisible();
			savegame.win_image=win_image;
			break;
		case 6:
			container_fifth_guess_wrong.setVisible(false);
			container_lose.setVisible(true);
			start_image = container_start.isVisible();
			savegame.start_image=start_image;
			first_guess_wrong_image=container_first_guess_wrong.isVisible();
			savegame.first_guess_wrong_image=first_guess_wrong_image;
			second_guess_wrong_image=container_second_guess_wrong.isVisible();
			savegame.second_guess_wrong_image=second_guess_wrong_image;
			third_guess_wrong_image=container_third_guess_wrong.isVisible();
			savegame.third_guess_wrong_image=third_guess_wrong_image;
			fourth_guess_wrong_image=container_fourth_guess_wrong.isVisible();
			savegame.fourth_guess_wrong_image=fourth_guess_wrong_image;
			fifth_guess_wrong_image=container_fifth_guess_wrong.isVisible();
			savegame.fifth_guess_wrong_image=fifth_guess_wrong_image;
			loss_image=container_lose.isVisible();
			savegame.loss_image=loss_image;
			win_image=container_win.isVisible();
			savegame.win_image=win_image;
			break;
		
		}
		String str = save.getText();
		savegame.save=str;
		if(level1.isSelected()){
			boolean lv = level1.isSelected();
			savegame.level1=lv;
		}else
		if(level2.isSelected()){
			boolean lv = level2.isSelected();
			savegame.level2=lv;
		}else
		if(level3.isSelected()){
			boolean lv = level3.isSelected();
			savegame.level3=lv;
		}else
		if(level4.isSelected()){
			boolean lv = level4.isSelected();
			savegame.level4=lv;
		}
		String a=String.valueOf(buttonA.isDisable());
		savegame.buttonA=a;
		boolean b=buttonB.isDisable();
		savegame.buttonB=b;
		boolean c=buttonC.isDisable();
		savegame.buttonC=c;
		boolean d=buttonD.isDisable();
		savegame.buttonD=d;
		boolean e=buttonE.isDisable();
		savegame.buttonE=e;
		boolean f=buttonF.isDisable();
		savegame.buttonF=f;
		boolean g=buttonG.isDisable();
		savegame.buttonG=g;
		boolean h=buttonH.isDisable();
		savegame.buttonH=h;
		boolean i=buttonI.isDisable();
		savegame.buttonI=i;
		boolean j=buttonJ.isDisable();
		savegame.buttonJ=j;
		boolean k=buttonK.isDisable();
		savegame.buttonK=k;
		boolean l=buttonL.isDisable();
		savegame.buttonL=l;
		boolean m=buttonM.isDisable();
		savegame.buttonM=m;
		boolean n=buttonN.isDisable();
		savegame.buttonN=n;
		boolean o=buttonO.isDisable();
		savegame.buttonO=o;
		boolean p=buttonP.isDisable();
		savegame.buttonP=p;
		boolean q=buttonQ.isDisable();
		savegame.buttonQ=q;
		boolean r=buttonR.isDisable();
		savegame.buttonR=r;
		boolean s=buttonS.isDisable();
		savegame.buttonS=s;
		boolean t=buttonT.isDisable();
		savegame.buttonT=t;
		boolean u=buttonU.isDisable();
		savegame.buttonU=u;
		boolean v=buttonV.isDisable();
		savegame.buttonV=v;
		boolean w=buttonW.isDisable();
		savegame.buttonW=w;
		boolean x=buttonX.isDisable();
		savegame.buttonX=x;
		boolean y=buttonY.isDisable();
		savegame.buttonY=y;
		boolean z=buttonZ.isDisable();
		savegame.buttonZ=z;
		try{
			resource.save(savegame, "SaveGame");
		}catch(Exception E){
			E.printStackTrace();
			E.getMessage();
		}
		if(buttonA.isDisable()){
			System.out.println(savegame.buttonA);
		}else{
			System.out.println("false");
		}
		Stage stage;
		stage = new Stage();
		Label label = new Label("save successfully");
		label.setFont(new Font(15));
		BorderPane pane = new BorderPane();
		pane.setPrefWidth(300);
		pane.setPrefHeight(100);
		pane.setCenter(label);
		Button play = new Button("CONFIRM");
		pane.setBottom(play);
		BorderPane.setAlignment(play, Pos.BASELINE_CENTER);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        play.setOnMousePressed((event) ->{
        	stage.close();
        });
	}
	@FXML
	private Button temp;
	@FXML
	public void continuetoplay() throws IOException{
		Stage stage; 
		stage=(Stage) temp.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("hangman.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
        stage.setTitle("Wordhandler");
        stage.setResizable(false);
	}
	
	@FXML
	public void load() throws IOException{
		savegame.setDisable(false);
		 try{
	        	Savegame savegame=(Savegame) resource.load("SaveGame");
	        	Gamerun=savegame.gamerun;
	    		switch(Gamerun){
	    		case 0:
	    			container_start.setVisible(savegame.start_image);
	    			container_first_guess_wrong.setVisible(savegame.first_guess_wrong_image);
	    			container_second_guess_wrong.setVisible(savegame.second_guess_wrong_image);
	    			container_third_guess_wrong.setVisible(savegame.third_guess_wrong_image);
	    			container_fourth_guess_wrong.setVisible(savegame.fourth_guess_wrong_image);
	    			container_fifth_guess_wrong.setVisible(savegame.fifth_guess_wrong_image);
	    			container_lose.setVisible(savegame.loss_image);
	    			container_win.setVisible(savegame.win_image);
	    			
	    			break;
	    		case 1:
	    			container_start.setVisible(savegame.start_image);
	    			container_first_guess_wrong.setVisible(savegame.first_guess_wrong_image);
	    			container_second_guess_wrong.setVisible(savegame.second_guess_wrong_image);
	    			container_third_guess_wrong.setVisible(savegame.third_guess_wrong_image);
	    			container_fourth_guess_wrong.setVisible(savegame.fourth_guess_wrong_image);
	    			container_fifth_guess_wrong.setVisible(savegame.fifth_guess_wrong_image);
	    			container_lose.setVisible(savegame.loss_image);
	    			container_win.setVisible(savegame.win_image);
	    			break;
	    		case 2:
	    			container_start.setVisible(savegame.start_image);
	    			container_first_guess_wrong.setVisible(savegame.first_guess_wrong_image);
	    			container_second_guess_wrong.setVisible(savegame.second_guess_wrong_image);
	    			container_third_guess_wrong.setVisible(savegame.third_guess_wrong_image);
	    			container_fourth_guess_wrong.setVisible(savegame.fourth_guess_wrong_image);
	    			container_fifth_guess_wrong.setVisible(savegame.fifth_guess_wrong_image);
	    			container_lose.setVisible(savegame.loss_image);
	    			container_win.setVisible(savegame.win_image);
	    			break;
	    		case 3:
	    			container_start.setVisible(savegame.start_image);
	    			container_first_guess_wrong.setVisible(savegame.first_guess_wrong_image);
	    			container_second_guess_wrong.setVisible(savegame.second_guess_wrong_image);
	    			container_third_guess_wrong.setVisible(savegame.third_guess_wrong_image);
	    			container_fourth_guess_wrong.setVisible(savegame.fourth_guess_wrong_image);
	    			container_fifth_guess_wrong.setVisible(savegame.fifth_guess_wrong_image);
	    			container_lose.setVisible(savegame.loss_image);
	    			container_win.setVisible(savegame.win_image);
	    			break;
	    		case 4:
	    			container_start.setVisible(savegame.start_image);
	    			container_first_guess_wrong.setVisible(savegame.first_guess_wrong_image);
	    			container_second_guess_wrong.setVisible(savegame.second_guess_wrong_image);
	    			container_third_guess_wrong.setVisible(savegame.third_guess_wrong_image);
	    			container_fourth_guess_wrong.setVisible(savegame.fourth_guess_wrong_image);
	    			container_fifth_guess_wrong.setVisible(savegame.fifth_guess_wrong_image);
	    			container_lose.setVisible(savegame.loss_image);
	    			container_win.setVisible(savegame.win_image);
	    			break;
	    		case 5:
	    			container_start.setVisible(savegame.start_image);
	    			container_first_guess_wrong.setVisible(savegame.first_guess_wrong_image);
	    			container_second_guess_wrong.setVisible(savegame.second_guess_wrong_image);
	    			container_third_guess_wrong.setVisible(savegame.third_guess_wrong_image);
	    			container_fourth_guess_wrong.setVisible(savegame.fourth_guess_wrong_image);
	    			container_fifth_guess_wrong.setVisible(savegame.fifth_guess_wrong_image);
	    			container_lose.setVisible(savegame.loss_image);
	    			container_win.setVisible(savegame.win_image);
	    			break;
	    		case 6:
	    			container_start.setVisible(savegame.start_image);
	    			container_first_guess_wrong.setVisible(savegame.first_guess_wrong_image);
	    			container_second_guess_wrong.setVisible(savegame.second_guess_wrong_image);
	    			container_third_guess_wrong.setVisible(savegame.third_guess_wrong_image);
	    			container_fourth_guess_wrong.setVisible(savegame.fourth_guess_wrong_image);
	    			container_fifth_guess_wrong.setVisible(savegame.fifth_guess_wrong_image);
	    			container_lose.setVisible(savegame.loss_image);
	    			container_win.setVisible(savegame.win_image);
	    			break;
	    		}
	        	save.setText(savegame.save);
	        	level1.setSelected(savegame.level1);
	        	level2.setSelected(savegame.level2);
	        	level3.setSelected(savegame.level3);
	        	level4.setSelected(savegame.level4);
	        	boolean a = Boolean.parseBoolean(savegame.buttonA);
	        	buttonA.setDisable(a);
	        	buttonB.setDisable(savegame.buttonB);
	        	buttonC.setDisable(savegame.buttonC);
	        	buttonD.setDisable(savegame.buttonD);
	        	buttonE.setDisable(savegame.buttonE);
	        	buttonF.setDisable(savegame.buttonF);
	        	buttonG.setDisable(savegame.buttonG);
	        	buttonH.setDisable(savegame.buttonH);
	        	buttonI.setDisable(savegame.buttonI);
	        	buttonJ.setDisable(savegame.buttonJ);
	        	buttonK.setDisable(savegame.buttonK);
	        	buttonL.setDisable(savegame.buttonL);
	        	buttonM.setDisable(savegame.buttonM);
	        	buttonN.setDisable(savegame.buttonN);
	        	buttonO.setDisable(savegame.buttonO);
	        	buttonP.setDisable(savegame.buttonP);
	        	buttonQ.setDisable(savegame.buttonQ);
	        	buttonR.setDisable(savegame.buttonR);
	        	buttonS.setDisable(savegame.buttonS);
	        	buttonT.setDisable(savegame.buttonT);
	        	buttonU.setDisable(savegame.buttonU);
	        	buttonV.setDisable(savegame.buttonV);
	        	buttonW.setDisable(savegame.buttonW);
	        	buttonX.setDisable(savegame.buttonX);
	        	buttonY.setDisable(savegame.buttonY);
	        	buttonZ.setDisable(savegame.buttonZ);
	        	
	        }catch(Exception e){
				e.printStackTrace();
				e.getMessage();
			}
	       
	}
	
	
	
	
	
	
	
	
	@FXML
	private AnchorPane container_start;
	@FXML
	private AnchorPane container_first_guess_wrong;
	@FXML
	private AnchorPane container_second_guess_wrong;
	@FXML
	private AnchorPane container_third_guess_wrong;
	@FXML
	private AnchorPane container_fourth_guess_wrong;
	@FXML
	private AnchorPane container_fifth_guess_wrong;
	@FXML
	private AnchorPane container_lose;
	@FXML
	private AnchorPane container_win;
	
	@SuppressWarnings("resource")
	@FXML
	private void buttonAEvent() throws IOException, ClassNotFoundException{
		buttonA.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()==true){
			if('a'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'a');
				text = build.toString();
				save.setText(build.toString());
				n++;
			}else{
				n++;
			}
		}
		
		if(Gamerun<6 && text.contains("*")==false ){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
		}
		
		if(buttonA.isPressed()&& str.contains("a")==false){
			
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonBEvent() throws IOException, ClassNotFoundException{
		buttonB.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('b'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'b');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonB.isPressed()&&str.contains("b")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}

	@SuppressWarnings("resource")
	@FXML
	private void buttonCEvent() throws IOException, ClassNotFoundException{
		buttonC.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('c'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'c');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonC.isPressed()&&str.contains("c")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonDEvent() throws IOException, ClassNotFoundException{
		buttonD.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('d'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'd');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonD.isPressed()&&str.contains("d")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonEEvent() throws IOException, ClassNotFoundException{
		buttonE.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('e'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'e');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonE.isPressed()&&str.contains("e")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonFEvent() throws IOException, ClassNotFoundException{
		buttonF.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('f'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'f');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonF.isPressed()&&str.contains("f")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonGEvent() throws ClassNotFoundException, IOException{
		buttonG.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('g'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'g');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonG.isPressed()&&str.contains("g")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonHEvent() throws IOException, ClassNotFoundException{
		buttonH.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('h'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'h');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonH.isPressed()&&str.contains("h")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonIEvent() throws ClassNotFoundException, IOException{
		buttonI.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('i'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'i');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonI.isPressed()&&str.contains("i")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonJEvent() throws ClassNotFoundException, IOException{
		buttonJ.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('j'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'j');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			};
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonJ.isPressed()&&str.contains("j")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonKEvent() throws ClassNotFoundException, IOException{
		buttonK.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('k'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'k');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonK.isPressed()&&str.contains("k")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonLEvent() throws ClassNotFoundException, IOException{
		buttonL.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('l'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'l');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonL.isPressed()&&str.contains("l")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonMEvent() throws ClassNotFoundException, IOException{
		buttonM.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('m'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'm');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonM.isPressed()&&str.contains("m")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonNEvent() throws ClassNotFoundException, IOException{
		buttonN.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('n'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'n');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonN.isPressed()&&str.contains("n")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonOEvent() throws ClassNotFoundException, IOException{
		buttonO.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('o'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'o');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonO.isPressed()&&str.contains("o")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonPEvent() throws ClassNotFoundException, IOException{
		buttonP.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('p'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'p');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonP.isPressed()&&str.contains("p")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonQEvent() throws ClassNotFoundException, IOException{
		buttonQ.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('q'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'q');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonQ.isPressed()&&str.contains("q")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonREvent() throws ClassNotFoundException, IOException{
		buttonR.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('r'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'r');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonR.isPressed()&&str.contains("r")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonSEvent() throws ClassNotFoundException, IOException{
		buttonS.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('s'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 's');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonS.isPressed()&&str.contains("s")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonTEvent() throws ClassNotFoundException, IOException{
		buttonT.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('t'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 't');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonT.isPressed()&&str.contains("t")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonUEvent() throws ClassNotFoundException, IOException{
		buttonU.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('u'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'u');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonU.isPressed()&&str.contains("u")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonVEvent() throws ClassNotFoundException, IOException{
		buttonV.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('v'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'v');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonV.isPressed()&&str.contains("v")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonWEvent() throws ClassNotFoundException, IOException{
		buttonW.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('w'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'w');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonW.isPressed()&&str.contains("w")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonXEvent() throws ClassNotFoundException, IOException{
		buttonX.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('x'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'x');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonX.isPressed()&&str.contains("x")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonYEvent() throws ClassNotFoundException, IOException{
		buttonY.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('y'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'y');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonY.isPressed()&&str.contains("y")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
	@SuppressWarnings("resource")
	@FXML
	private void buttonZEvent() throws ClassNotFoundException, IOException{
		buttonZ.setDisable(true);
		String currentDir=System.getProperty("user.dir");
		File playword = new File(currentDir,"palyword.data");
		FileInputStream stream = new FileInputStream(playword);
		ObjectInputStream oj = new ObjectInputStream(stream);
		String str=oj.readObject().toString();
		StringBuffer buffer = new StringBuffer(str);
		String text = save.getText();
		int n=0;
		while(n<buffer.length()){
			if('z'==buffer.charAt(n)){
				StringBuilder build = new StringBuilder(text);
				build.setCharAt(n, 'z');
				text = build.toString();
				save.setText(text);
				
				n++;
			}else{
				n++;
			}
			
		}
		if(Gamerun<6 && text.contains("*")==false){
			container_start.setVisible(false);
			container_first_guess_wrong.setVisible(false);
			container_second_guess_wrong.setVisible(false);
			container_third_guess_wrong.setVisible(false);
			container_fourth_guess_wrong.setVisible(false);
			container_fifth_guess_wrong.setVisible(false);
			container_win.setVisible(true);
			container_lose.setVisible(false);
			buttonA.setDisable(true);
			buttonB.setDisable(true);
			buttonC.setDisable(true);
			buttonD.setDisable(true);
			buttonE.setDisable(true);
			buttonF.setDisable(true);
			buttonG.setDisable(true);
			buttonH.setDisable(true);
			buttonI.setDisable(true);
			buttonJ.setDisable(true);
			buttonK.setDisable(true);
			buttonL.setDisable(true);
			buttonM.setDisable(true);
			buttonN.setDisable(true);
			buttonO.setDisable(true);
			buttonP.setDisable(true);
			buttonQ.setDisable(true);
			buttonR.setDisable(true);
			buttonS.setDisable(true);
			buttonT.setDisable(true);
			buttonU.setDisable(true);
			buttonV.setDisable(true);
			buttonW.setDisable(true);
			buttonX.setDisable(true);
			buttonY.setDisable(true);
			buttonZ.setDisable(true);
			}
		if(buttonZ.isPressed()&&str.contains("z")==false){
			Gamerun++;
			
			switch(Gamerun){
			case 0:
				container_start.setVisible(true);
				break;
			case 1:
				container_start.setVisible(false);
				container_first_guess_wrong.setVisible(true);
				break;
			case 2:
				container_first_guess_wrong.setVisible(false);
				container_second_guess_wrong.setVisible(true);
				break;
			case 3:
				container_second_guess_wrong.setVisible(false);
				container_third_guess_wrong.setVisible(true);
				break;
			case 4:
				container_third_guess_wrong.setVisible(false);
				container_fourth_guess_wrong.setVisible(true);
				break;
			case 5:
				container_fourth_guess_wrong.setVisible(false);
				container_fifth_guess_wrong.setVisible(true);
				break;
			case 6:
				container_fifth_guess_wrong.setVisible(false);
				container_lose.setVisible(true);
				buttonA.setDisable(true);
				buttonB.setDisable(true);
				buttonC.setDisable(true);
				buttonD.setDisable(true);
				buttonE.setDisable(true);
				buttonF.setDisable(true);
				buttonG.setDisable(true);
				buttonH.setDisable(true);
				buttonI.setDisable(true);
				buttonJ.setDisable(true);
				buttonK.setDisable(true);
				buttonL.setDisable(true);
				buttonM.setDisable(true);
				buttonN.setDisable(true);
				buttonO.setDisable(true);
				buttonP.setDisable(true);
				buttonQ.setDisable(true);
				buttonR.setDisable(true);
				buttonS.setDisable(true);
				buttonT.setDisable(true);
				buttonU.setDisable(true);
				buttonV.setDisable(true);
				buttonW.setDisable(true);
				buttonX.setDisable(true);
				buttonY.setDisable(true);
				buttonZ.setDisable(true);
				break;
			}	
		}
	}
}
