package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.ObservableList;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Client;
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
	@FXML
	private ImageView icon = new ImageView();
	@FXML
	private ImageView chooseImage=new ImageView();

	private static final int MAX_MESSAGES = 20;
	private static final int DISPLAYED_MESSAGES = 8;
	private static final int CELL_HEIGHT = 24;
	private static final int MAX_HEIGHT_IMAGE = 280;
	private static final int MAX_WIDTH_IMAGE = 250;

	private User u = new User();
	private Client c = null;

	private User friend = null;
	private ConnectToServer connect = new ConnectToServer();
	FileReader fr = null;
	thread t = new thread();
	long numberOfMessage = 0;

	public HomeController() {
		super();
	}

	public HomeController(User u, Client c) {
		super();
		this.u = u;
		this.c = c;
		this.c.setHome(this);
	}

	public void showListUser(List<User> listF) {
		for (User user : listF) {
			if (!u.getUsername().equals(user.getUsername()))
				insertUser(user);
		}
	}

	public void insertUser(User user) {
		Label l = new Label(user.getUsername());
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
		List<Message> listM = u.getListMessage(friend, 0);
		numberOfMessage = listM.size();
		myName.setText(u.getUsername());
		// set tên cho label dialogist
		dialogist.setText(friend.getUsername());

		Button btnLoad = new Button("Add Message");
		btnLoad.setOnAction(e -> {
			addBeforeMessage();
		});
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

	public void addBeforeMessage() {
		if (numberOfMessage % MAX_MESSAGES != 0)
			return;
		List<Message> listM = u.getListMessage(friend, numberOfMessage);
		for (Message m : listM) {
			insertMessage(listMessage.getItems().size() - numberOfMessage, m);
		}
		numberOfMessage += listM.size();
	}

	// Cái tên nói lên tất cả
	public void insertMessage(long n, Message m) {
		BorderPane bp = new BorderPane();
		if (m == null) {
			Label l = new Label("");
			l.setPadding(new Insets(5, 10, 5, 10));
			bp.getChildren().add(l);
		} else if (!m.getIcon().equals("")) {
			if (m.getSourceId() == friend.getUserId()) {
				Circle mycircle = new Circle();
				mycircle.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/account.jpg"))));
				mycircle.setRadius(10);
				Circle icon = new Circle();
				icon.setFill(new ImagePattern(new Image(getClass().getResourceAsStream(m.getIcon()))));
				icon.setRadius(10);
				HBox hb = new HBox();
				hb.getChildren().addAll(mycircle, icon);
				hb.setSpacing(4);
				bp.setLeft(hb);
			} else {
				Circle icon = new Circle();
				icon.setFill(new ImagePattern(new Image(getClass().getResourceAsStream(m.getIcon()))));
				icon.setRadius(10);
				bp.setRight(icon);
			}
		} else {
			Label l = new Label(m.getContent());
			l.setPadding(new Insets(5, 10, 5, 10));
			l.setMaxWidth(300);
			l.setWrapText(true);
			Circle mycircle;
			ImageView image = null;
			if (!m.getImage().equals("")) {
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
				if (!m.getContent().equals(""))
					vb.getChildren().add(l);
				if (image != null) {
					vb.getChildren().add(image);
				}
				bp.setLeft(hb);
			} else {
				VBox vb = new VBox();
				vb.setSpacing(2);
				if (!m.getContent().equals("")) {
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
		ObservableList<BorderPane> items = (ObservableList<BorderPane>) listMessage.getItems();
		items.add((int) n, bp);
		listMessage.setItems(items);
		listMessage.scrollTo(bp);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<User> listF = connect.findAllUser();
		if (!listF.isEmpty()) {
			showListUser(listF);
			for (User user : listF)
				if (!u.getUsername().equals(user.getUsername())) {
					friend = user;
					break;
				}
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
		// sự kiện nhấn enter thì kết thúc tin nhắn
		enterMessage.setOnKeyPressed(event -> {
			KeyCode kc = event.getCode();
			if (kc == KeyCode.ENTER && friend != null && enterMessage.getText() != "") {
				Message m = new Message(u.getUserId(), friend.getUserId(), enterMessage.getText(), "");
				connect.sendMessage(m);// truyền đến sever
				u.addMessageToDatabase(m);// Lưu dữ liệu vào database
				insertMessage(listMessage.getItems().size(), m);
				enterMessage.setText("");
			}
		});
	}

	@FXML
	public void clickIcon() {
		Message m = new Message(u.getUserId(), friend.getUserId(), "", "");
		m.setIcon("/like.png");
		connect.sendMessage(m);
		u.addMessageToDatabase(m);
		insertMessage(listMessage.getItems().size(), m);
	}
	@FXML
	public void chooseImage() {//xử lí sự kiện chèn hình ảnh
		FileChooser fileChooser = new FileChooser();
		File file=fileChooser.showOpenDialog(chooseImage.getScene().getWindow());
		Message m = new Message(u.getUserId(), friend.getUserId(), "", file.getPath());
		connect.sendMessage(m);
		u.addMessageToDatabase(m);
		insertMessage(listMessage.getItems().size(), m);
	}

	class thread extends Thread {
		public void run() {
			while (true) {
				try {
					while (!ConnectToServer.listMess.isEmpty()) {
						insertMessage(listMessage.getItems().size(),ConnectToServer.listMess.poll());
					}
					sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void setStage(Stage stage) {
		stage.setOnCloseRequest(e -> {
			try {
				c.closeClient();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			t.stop();
		});

	}

}