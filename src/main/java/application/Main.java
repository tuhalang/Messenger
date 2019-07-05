package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			HBox root = (HBox)FXMLLoader.load(getClass().getResource("Home.fxml"));
//			Scene scene = new Scene(root,767,449);
			VBox root =(VBox)FXMLLoader.load(getClass().getResource("/login/logIn.fxml"));
			Scene scene=new Scene(root,257,250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
