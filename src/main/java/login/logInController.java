package login;

import java.net.URL;
import java.util.ResourceBundle;

import application.HomeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;

public class logInController implements Initializable {
	@FXML
	private TextField tfUsername = new TextField();
	@FXML
	private PasswordField pfPassword = new PasswordField();
	@FXML
	private Button buttonLogin = new Button();
	@FXML
	private Label register = new Label();

	int check = 0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//sự kiện nhấn enter trong trường pass
		pfPassword.setOnKeyPressed(event -> {
			KeyCode kc = event.getCode();
			if (kc == KeyCode.ENTER) {
				login();
			}
		});
		register.setOnMouseClicked(event -> {
			Stage stage = new Stage();
			try {
				Pane root = FXMLLoader.load(getClass().getResource("/register/register.fxml"));
				Scene scene = new Scene(root, 345, 460);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Stage s = (Stage) register.getScene().getWindow();
			s.close();
		});
	}

	@FXML
	public void login() {
		User u = new User(tfUsername.getText(), pfPassword.getText());
		if (u.checkExist()) {
			try {
				HomeController controller = new HomeController(u);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Home.fxml"));
				loader.setController(controller);
				HBox layout = loader.load();
				Scene scene = new Scene(layout, 767, 449);
				Stage stage=new Stage();
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Stage stage = (Stage) buttonLogin.getScene().getWindow();
			stage.close();
		}
	}
}