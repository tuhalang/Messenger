package login;

import java.io.IOException;
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
import register.registerController;
import service.ConnectToServer;

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
	private ConnectToServer connect=new ConnectToServer();
	private client c=null;


	public client getC() {
		return c;
	}

	public void setC(client c) {
		this.c = c;
	}

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
		buttonLogin.setOnMouseClicked(event->{
			login();
		});
		register.setOnMouseClicked(event -> {
			Stage stage = new Stage();
			try {
				registerController r=new registerController(c);
				FXMLLoader loader= new FXMLLoader(getClass().getResource("/register/register.fxml"));
				loader.setController(r);
				Pane root = loader.load();
				Scene scene = new Scene(root, 345, 460);
				stage.setScene(scene);
				stage.setOnCloseRequest(e->{
					try {
						c.closeClient();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
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
		User u = connect.login(new User(tfUsername.getText(), pfPassword.getText()));
		if (u!=null) {
			c.setU(u);
			try {
				Stage stage=new Stage();
				HomeController controller = new HomeController(u,c);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Home.fxml"));
				loader.setController(controller);
				HBox layout = loader.load();
				Scene scene = new Scene(layout, 767, 449);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setOnCloseRequest(e->{
					try {
						c.closeClient();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
				controller.setStage(stage);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Stage stage = (Stage) buttonLogin.getScene().getWindow();
			stage.close();
		}
	}
}