package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Client;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Client client = null;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("logIn.fxml"));
			LogInController controll = new LogInController();
			controll.setClient(client);
			loader.setController(controll);
			VBox root = (VBox) loader.load();
			Scene scene = new Scene(root, 257, 250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}