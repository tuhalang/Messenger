package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class HomeController implements Initializable {
	@FXML
	private ListView<HBox> listFriend = new ListView<HBox>();
	@FXML
	private ListView<BorderPane> listMessage = new ListView<BorderPane>();
	@FXML
	private TextField enterMessage = new TextField();
	@FXML
	private Label dialogist=new Label();

	private static final int MAX_MESSAGES = 20;
	private static final int DISPLAYED_MESSAGES = 8;
	private static final int CELL_HEIGHT = 24;

	public HomeController() {
		super();
	}

	public void showListFriend() {
		// lấy dữ liệu từ database

		String[] listf = { "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3" };
		List<HBox> list = new ArrayList<>();
		for (String s : listf) {
			Label l = new Label(s);
			l.setPadding(new Insets(5, 10, 5, 10));
			Circle mycircle = new Circle();
			mycircle.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/account.jpg"))));
			mycircle.setRadius(15);
			HBox p = new HBox();
			p.getChildren().addAll(mycircle, l);
			list.add(p);
		}
		ObservableList<HBox> myObservableList = FXCollections.observableList(list);
		listFriend.setItems(myObservableList);

	}

	public void insertFriend(String name) {
		Label l = new Label(name);
		l.setPadding(new Insets(5, 10, 5, 10));
		Circle mycircle = new Circle();
		mycircle.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/account.jpg"))));
		mycircle.setRadius(15);
		HBox p = new HBox();
		p.getChildren().addAll(mycircle, l);
		listFriend.getItems().add(p);
	}

	public void showListMessage(String nameFriend) {
		//dialogist.setText(m.getTargetId());
		// lấy tin nhắn từ server
		ArrayList<String> message = new ArrayList<String>();
		for (int i = 0; i < 10; ++i)// thư nghiệm
			message.add(
					"I have had this similar need in a simple dialog box. The root of the dialog box is a 2x2 GridPane. Each row contains an HBox that spans both columns");
		List<BorderPane> list = new ArrayList<BorderPane>();
		int len = message.size();
		while (len++ < MAX_MESSAGES) {
			message.add(0, "");
		}
		for (String s : message) {
			Label l = new Label(s);
			boolean it_me = true;//xác định có phải mình hay k
			l.setPadding(new Insets(5, 10, 5, 10));
			l.setMaxWidth(300);
			l.setWrapText(true);
			Circle mycircle;
			BorderPane bp = new BorderPane();
			if (!it_me) {
				mycircle = new Circle();
				mycircle.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/account.jpg"))));
				mycircle.setRadius(10);
				HBox hb = new HBox();
				if (s != "")
					hb.getChildren().addAll(mycircle, l);
				else
					hb.getChildren().add(l);
				bp.setLeft(hb);
			} else {
				if (s != "") {
					l.setStyle("-fx-background-radius:15;-fx-background-color:deepskyblue;");
					l.setAlignment(Pos.CENTER_RIGHT);
				}
				bp.setRight(l);
			}
			list.add(bp);
		}
		ObservableList<BorderPane> myObservableList = FXCollections.observableList(list);
		listMessage.setItems(myObservableList);
		listMessage.scrollTo(len);
	}

	// Cái tên nói lên tất cả
	public void insertMessage(String message) {
		if (message.equals("")) return;
		Label l = new Label(message);
		l.setMaxWidth(300);
		l.setWrapText(true);
		l.setPadding(new Insets(5, 10, 5, 10));
		boolean it_me = false;
		Circle mycircle;
		BorderPane bp = new BorderPane();
		if (!it_me) {
			mycircle = new Circle();
			mycircle.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/account.jpg"))));
			mycircle.setRadius(10);
			HBox hb = new HBox();
			hb.getChildren().addAll(mycircle, l);
			bp.setLeft(hb);
		} else {
			l.setStyle("-fx-background-radius:15;  -fx-background-color:deepskyblue;");
			l.setAlignment(Pos.CENTER_RIGHT);
			bp.setRight(l);
		}
		listMessage.getItems().add(bp);
		listMessage.scrollTo(bp);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		showListFriend();// hiện ra list Friend
		// sư kiên khi chọn friend
		listFriend.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				showListMessage("abc");
			}

		});
		showListMessage("abc");
		// sự kiện nhấn enter thì kết thúc tin nhắn
		enterMessage.setOnKeyPressed(event -> {
			KeyCode kc = event.getCode();
			if (kc == KeyCode.ENTER) {
				insertMessage(enterMessage.getText());
				enterMessage.setText("");
				
				
				//Lưu dữ liệu và database
			}
		});
		listMessage.setOnScrollStarted(new EventHandler<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent arg0) {
				// TODO Auto-generated method stub
			}

			
		});
	}
}