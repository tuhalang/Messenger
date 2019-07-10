package application;

import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Message;
import model.User;
import service.connectMongodb;

public class HomeController implements Initializable {
	@FXML
	private ListView<HBox> listFriend = new ListView<HBox>();
	@FXML
	private ListView<BorderPane> listMessage = new ListView<BorderPane>();
	@FXML
	private TextField enterMessage = new TextField();
	@FXML
	private TextField findUser = new TextField();
	@FXML
	private Label dialogist = new Label();
	@FXML
	private Label myName = new Label();

	private static final int MAX_MESSAGES = 20;
	private static final int DISPLAYED_MESSAGES = 8;
	private static final int CELL_HEIGHT = 24;
	private static final int MAX_HEIGHT_IMAGE = 280;
	private static final int MAX_WIDTH_IMAGE = 250;

	private User u = new User();
	private User friend = new User();

	public HomeController() {
		super();
	}

	public HomeController(User u) {
		super();
		this.u = u;
	}

	public void showListFriend(ArrayList<User> listF) {
		for (User user : listF) {
			insertFriend(user);
		}
	}

	public void insertFriend(User friend) {
		Label l = new Label(friend.getUsername());
		l.setPadding(new Insets(5, 10, 5, 10));
		Circle mycircle = new Circle();
		mycircle.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/account.jpg"))));
		mycircle.setRadius(15);
		HBox p = new HBox();
		p.getChildren().addAll(mycircle, l);
		listFriend.getItems().add(p);
	}

	public void showListMessage(User friend) {
		// set tên cho label myName
		ArrayList<Message> listM = u.getListMessage(friend);
		myName.setText(u.getUsername());
		// set tên cho label dialogist
		dialogist.setText(friend.getUsername());
		int len = listM.size();
		while (len++ < MAX_MESSAGES) {
			listM.add(0, null);
		}
		for (Message m : listM) {
			insertMessage(m);
		}
	}

	// Cái tên nói lên tất cả
	public void insertMessage(Message m) {
		BorderPane bp = new BorderPane();
		if (m == null) {
			Label l = new Label("");
			l.setPadding(new Insets(5, 10, 5, 10));
			bp.getChildren().add(l);
			listMessage.getItems().add(bp);
		} else {
			Label l = new Label(m.getContent());
			l.setPadding(new Insets(5, 10, 5, 10));
			l.setMaxWidth(300);
			l.setWrapText(true);
			Circle mycircle;
			ImageView image = null;
			if (m.getImage() != "") {
				image = new ImageView(new Image(m.getImage()));
				image.maxHeight(MAX_HEIGHT_IMAGE);
				image.maxWidth(MAX_WIDTH_IMAGE);
				image.setPreserveRatio(true);
				if (image.maxHeight(MAX_HEIGHT_IMAGE) > MAX_HEIGHT_IMAGE)
					image.setFitHeight(MAX_HEIGHT_IMAGE);
				if (image.maxWidth(MAX_WIDTH_IMAGE) > MAX_WIDTH_IMAGE)
					image.setFitWidth(MAX_WIDTH_IMAGE);
			}
			if (m.getSourceId() == friend.getUserId()) {
				l.setStyle("-fx-background-radius:10;-fx-background-color:#F8F8FF;");
				mycircle = new Circle();
				mycircle.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/account.jpg"))));
				mycircle.setRadius(10);
				HBox hb = new HBox();
				VBox vb = new VBox();
				vb.setSpacing(2);
				hb.getChildren().addAll(mycircle, vb);
				hb.setSpacing(4);
				if (m.getContent() != "")
					vb.getChildren().add(l);
				if (image != null) {
					vb.getChildren().add(image);
				}
				bp.setLeft(hb);
			} else {
				VBox vb = new VBox();
				vb.setSpacing(2);
				if (m.getContent() != "") {
					BorderPane p = new BorderPane();
					l.setStyle("-fx-background-radius:10;-fx-background-color:deepskyblue;");
					l.setAlignment(Pos.CENTER_RIGHT);
					p.setRight(l);
					vb.getChildren().add(p);
				}
				if (image != null) {
					vb.getChildren().add(image);
				}
				bp.setRight(vb);
			}
		}
		listMessage.getItems().add(bp);
		listMessage.scrollTo(bp);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ArrayList<User> listF = u.getListFriend();
		showListFriend(listF);
		// sư kiên khi chọn friend
		listFriend.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Label l = (Label) listFriend.getSelectionModel().getSelectedItem().getChildren().get(1);
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Alert");
				alert.setContentText("Bạn muốn kết bạn với người này?");
				Optional<ButtonType> option = alert.showAndWait();
				if (option.get() == ButtonType.OK) {
				}
			}
		});
		// auto complete find Friend
		findUser.setOnKeyReleased(event -> {
			String key = findUser.getText();
			if (key.equals("")) {
				listFriend.getItems().clear();
				showListFriend(listF);
			} else {
				ArrayList<User> listUser = new ArrayList<User>();
				try {
					MongoClient mongo=connectMongodb.getMongoClient_1();
					DB db=(DB) mongo.getDB("demo");
					DBCollection dept=db.getCollection("user");
					BasicDBObjectBuilder whereBuilder=BasicDBObjectBuilder.start();
					whereBuilder.append("username",java.util.regex.Pattern.compile(key));
					DBObject where=whereBuilder.get();
					DBCursor cursor=dept.find(where);
					while(cursor.hasNext()) {
						DBObject rs=cursor.next();
						if (!(rs.get("username").equals(u.getUsername()))) {
							listUser.add(new User((String)rs.get("username"),(String) rs.get("password")));
							System.out.println(rs.get("username"));
						}
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listFriend.getItems().clear();
				for (User u : listUser) {
					insertFriend(u);
				}
			}
		});
		// sự kiện nhấn enter thì kết thúc tin nhắn
		enterMessage.setOnKeyPressed(event -> {
			KeyCode kc = event.getCode();
			if (kc == KeyCode.ENTER) {
				Message m = new Message(0, u.getUserId(), friend.getUserId(), enterMessage.getText(), "");
				m.addMessageToDatabase(m);
				insertMessage(m);
				enterMessage.setText("");

				// Lưu dữ liệu và database
			}
		});
		if (!listF.isEmpty())
			showListMessage(listF.get(0));
	}

}