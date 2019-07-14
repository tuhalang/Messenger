package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;
import login.client;
import model.Message;
import model.User;
import service.ConnectToServer;

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
	private client c = null;

	private User friend = null;
	private ConnectToServer connect = new ConnectToServer();
	FileReader fr = null;
	thread t=new thread();

	public HomeController() {
		super();
	}

	public HomeController(User u, client c) {
		super();
		this.u = u;
		this.c = c;
	}

	public void showListUser(List<User> listF) {
		for (User user : listF) {
			if (u != user)
				insertUser(user);
		}
	}

	public void insertUser(User friend) {
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
		listMessage.getItems().clear();
		// set tên cho label myName
		// List<Message> listM = u.getListMessage(friend);
		List<Message> listM = new ArrayList<Message>();
		myName.setText(u.getUsername());
		// set tên cho label dialogist
		dialogist.setText(friend.getUsername());

		Button btnLoad = new Button("Add Message");
		BorderPane bp = new BorderPane(btnLoad);
		listMessage.getItems().add(0, bp);

		int len = listM.size();
		while (len++ < MAX_MESSAGES) {
			listM.add(0, null);
		}
		int check = 1;
		for (Message m : listM) {
			insertMessage(check++, m);
		}
	}

	// Cái tên nói lên tất cả
	public void insertMessage(int location, Message m) {
		BorderPane bp = new BorderPane();
		if (m == null) {
			Label l = new Label("");
			l.setPadding(new Insets(5, 10,5, 10));
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
		List<User> listF = connect.findAllUser();
		showListUser(listF);
		
		if (!listF.isEmpty()) {
			friend=listF.get(0);
			showListMessage(friend);
		}
		// liên tục nhận tin nhắn về
		t.start();
		// sư kiên khi chọn items của listFriend
		listFriend.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Label l = (Label) listFriend.getSelectionModel().getSelectedItem().getChildren().get(1);
				User check = connect.searchByName(l.getText());
				if (check != null) {
					friend = check;
					showListMessage(friend);
				}
			}
		});
		friend=new User("123","234");
		insertMessage(0, new Message(0,0,"123",""));
		// sự kiện nhấn enter thì kết thúc tin nhắn
		enterMessage.setOnKeyPressed(event -> {
			KeyCode kc = event.getCode();
			if (kc == KeyCode.ENTER && friend != null && enterMessage.getText()!="") {
				Message m = new Message(u.getUserId(), friend.getUserId(), enterMessage.getText(), "");
				connect.sendMessage(m);//truyền đến sever
				u.addMessageToDatabase(m);// Lưu dữ liệu vào database
				insertMessage(listMessage.getItems().size(), m);
				enterMessage.setText("");
			}
		});
		showListMessage(new User());
	}
	class thread extends Thread{
		public void run() {
			while(true) {
				//TODO
				System.out.println(1);
				try {
					fr =new FileReader("tranferMessage");
					FileWriter fw=new FileWriter("tranferMessage",false);
					BufferedReader br=new BufferedReader(fr);
					String s=null;
					while((s=br.readLine())!=null) {
						ObjectMapper mapper = new ObjectMapper();
						try {
						    Message m = mapper.readValue(s, Message.class);
						    insertMessage(listMessage.getItems().size(), m);
						} catch (IOException e) {
						    e.printStackTrace();
						}
					}
					fw.write("");
					fw.close();
					br.close();
					fr.close();
					sleep(600);
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	public void setStage(Stage stage) {
		stage.setOnCloseRequest(e->{
			t.stop();
		});
		
	}

}