package register;

import java.net.URL;
import java.util.ResourceBundle;

import application.HomeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.User;
import service.checkUser;

public class registerController implements Initializable{
	@FXML
	private TextField tfEmail=new TextField();
	@FXML 
	private TextField tfUsername =new TextField();
	@FXML
	private PasswordField pfPassword=new PasswordField();
	@FXML
	private PasswordField pfRepassword=new PasswordField();
	@FXML
	private Button btnRegister=new Button();
	@FXML
	private Label labelLogin=new Label();
	@FXML
	private CheckBox male=new CheckBox();
	@FXML
	private CheckBox female=new CheckBox();
	
	private User u=new User();
	private checkUser check=new checkUser();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		male.setSelected(true);
		male.setOnMouseClicked(event->{female.setSelected(false);});
		female.setOnMouseClicked(event->{male.setSelected(false);});
		pfRepassword.setOnKeyReleased(event->{
			KeyCode kc=event.getCode();
			if (pfRepassword.getText().equals(pfPassword.getText())) {
				pfRepassword.setStyle("-fx-border-color:blue");
				if (kc==KeyCode.ENTER) register();
			}else {
				pfRepassword.setStyle("-fx-border-color:red");
			}
		});
	}
	public void register() {
		if (!check.isValidEmail(tfEmail.getText())) {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Warning alert");
	        alert.setContentText("Wrong Email!");
	        alert.showAndWait();
		}else {
			if (!check.checkExistUsername(tfUsername.getText())) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Warning alert");
		        alert.setContentText("Wrong Username!");
		        alert.showAndWait();
			}else {
				u.setUsername(tfUsername.getText());
				if (pfPassword.getText().length()<6) {
					Alert alert = new Alert(AlertType.WARNING);
			        alert.setTitle("Warning alert");
			        alert.setContentText("Password is too short!");
			        alert.showAndWait();
				}else {
					short sex;
					if (male.isSelected()) sex=0; else sex=1;
					u.setSex(sex);
					u.setPassword(pfPassword.getText());
					if (check.createAccount(u)) {
						Alert alert = new Alert(AlertType.WARNING);
				        alert.setTitle("Alert");
				        alert.setContentText("Create successful!");
				        alert.showAndWait();
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
						Stage stage = (Stage) tfUsername.getScene().getWindow();
						stage.close();
					}else {
						Alert alert = new Alert(AlertType.WARNING);
				        alert.setTitle("Alert");
				        alert.setContentText("Create fail!");
				        alert.showAndWait();
					}
				}
			}
		}
	}
	
}