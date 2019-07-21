package login;

import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import model.Client;
import model.User;
import register.RegisterController;
import service.impl.Handle;

public class LogInController implements Initializable {
	@FXML
	private TextField tfUsername = new TextField();
	@FXML
	private PasswordField pfPassword = new PasswordField();
	@FXML
	private Button buttonLogin = new Button();
	@FXML
	private Label register = new Label();

	int check = 0;
	private Client client=null;


	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
				RegisterController r=new RegisterController(client);
				FXMLLoader loader= new FXMLLoader(getClass().getResource("/register/register.fxml"));
				loader.setController(r);
				Pane root = loader.load();
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
		User user = new User(tfUsername.getText(), pfPassword.getText());
		client = new Client(user);
		ObjectMapper mapper = new ObjectMapper();	
		try {
			client.send("1", mapper.writeValueAsString(user));
			client.receivePretreatment();
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (client.isValid()) {
			System.out.println("login thanh cong");
			try {
				Stage stage=new Stage();
				HomeController controller = new HomeController(client.getUser(),client);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Home.fxml"));
				loader.setController(controller);
				HBox layout = loader.load();
				Scene scene = new Scene(layout, 767, 449);
				stage.setScene(scene);
				stage.setResizable(false);
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