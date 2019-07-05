package login;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class logInController implements Initializable{
	@FXML
	private TextField tfUsername=new TextField();
	@FXML
	private PasswordField pfPassword=new PasswordField();
	@FXML
	private Button buttonLogin=new Button();
	@FXML
	private Label register=new Label();
	
	int check=0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tfUsername.setOnKeyPressed(event->{
			if (tfUsername.getText().length()>5) check=1;
		});
		pfPassword.setOnKeyPressed(event->{
			KeyCode kc=event.getCode();
			if (kc==KeyCode.ENTER) {
				//login();
			}
			if (pfPassword.getText().length()>5 && check==1) check=2;
		});
		buttonLogin.setOnMouseClicked(event->{
			//login();
		});
		register.setOnMouseClicked(event->{
			Stage primaryStage=new Stage();
			try {
				Pane root =FXMLLoader.load(getClass().getResource("register.fxml"));
				Scene scene = new Scene(root,292,417);
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
			Stage stage=(Stage) register.getScene().getWindow();
			stage.close();
		});
	}
//	public void login() {
//		if (check==2) {
//			connect c=new connect();
//			try {
//				Connection conn=c.getMySQLConnection();
//				Statement stmt=conn.createStatement();
//				ResultSet res=stmt.executeQuery("SELECT * FROM user WHERE username='"+sha.getSHA256(tfUsername.getText())+"'AND pass='"+sha.getSHA256(pfPassword.getText())+"';");
//				if (res.next()) {
//					user u=new user();
//					u.setName(tfUsername.getText());
//					u.setPassword(pfPassword.getText());
//					application app=new application(u);
//				}
//			} catch (ClassNotFoundException | SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Stage stage=(Stage) register.getScene().getWindow();
//			stage.close();
//		}
//	}
}
