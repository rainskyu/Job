import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class start_interface extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        Scene scene = new Scene((Parent) loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("StartPage");
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
