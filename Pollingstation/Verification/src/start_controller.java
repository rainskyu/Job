import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class start_controller {

	@FXML
	private Button confirm;
	
	@FXML
	private TextField electionid;
	
	public TextField getElectionid() {
		return electionid;
	}

	public void setElectionid(TextField electionid) {
		this.electionid = electionid;
	}

	@FXML
	private TextField path;
	
	@FXML
	private Label pathalert;
	
	@FXML
	private Button search;
	
	@FXML
	public void confirmbutton() throws ClassNotFoundException, SQLException, IOException {
		pathalert.setVisible(false);
		if(path.getText().isEmpty()) {
			pathalert.setVisible(true);
		}else {
			Stage stage;
			stage=(Stage) confirm.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			Scene scene = new Scene((Parent) loader.load());
			stage.setScene(scene);
			stage.setWidth(1080);
			stage.setHeight(610);
	        stage.setTitle("Verification");
	        stage.setResizable(false);
	        verification_controller controller = (verification_controller) loader.getController();
			controller.setPath(path.getText());
			controller.setJSON();
		}
		
	}
	
	@FXML
	public void searchbutton() {
		Stage stage;
		stage=(Stage) search.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		fileChooser.setTitle("Open Resource File");
		File file=fileChooser.showOpenDialog(stage);
	     if (file != null) {
             path.setText(file.getPath());
         }
		
	}
	
	private static void configureFileChooser(final FileChooser fileChooser) {      
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );                 
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JSON", "*.json")
        );
	}

	
}
