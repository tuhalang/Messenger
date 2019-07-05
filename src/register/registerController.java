package register;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
	
	boolean check=false;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
//	public void register() {
//		if (tfEmail.getText().length()>9 && tfUsername.getText().length()>9 && pfRepassword.getText().equals(pfPassword.getText()) 
//				&& pfPassword.getText().length()>5) {
//			connect con=new connect();
//			try {
//				Connection c=con.getMySQLConnection();
//				Statement stmt=c.createStatement();
//				ResultSet res=stmt.executeQuery("SELECT * FROM user WHERE username='"+sha.getSHA256(tfUsername.getText())+"'AND pass='"+sha.getSHA256(pfPassword.getText())+"';");
//				if (!res.next()) {
//					stmt.executeUpdate("INSERT INTO user(username,pass,email) VALUES ('"+sha.getSHA256(tfUsername.getText())+"','"+sha.getSHA256(pfPassword.getText())+"','"+tfEmail.getText()+"');");
//					stmt.executeUpdate("CREATE TABLE friend_"+tfUsername.getText()+"("
//							+"name varchar(100) PRIMARY KEY,"
//							+"status boolean);");
//					check=true;
//					
//				}else System.out.println(234);
//			} catch (ClassNotFoundException | SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		if (check) {
//			Alert alert = new Alert(AlertType.INFORMATION);
//	        alert.setTitle("Check registration");
//	 
//	        // Header Text: null
//	        alert.setHeaderText(null);
//	        alert.setContentText("Create account successfully!");
//	 
//	        alert.showAndWait();
//	        
//			Stage stage=(Stage) btnRegister.getScene().getWindow();
//			stage.close();
//			user u=new user(tfUsername.getText(),pfPassword.getText());
//			application a=new application(u);
//		}else {
//			Alert alert = new Alert(AlertType.INFORMATION);
//	        alert.setTitle("Check registration");
//	 
//	        // Header Text: null
//	        alert.setHeaderText(null);
//	        alert.setContentText("Create account fail!");
//	 
//	        alert.showAndWait();
//		}
//	}
}
