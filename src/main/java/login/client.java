package login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Logger;

import Logging.Logging;
import application.HomeController;
import model.User;
import service.Command;
import service.ConnectToServer;
import service.Execute;
import service.impl.ExeLogin;
import service.impl.ExeMessage;
import service.impl.ExeRegister;
import service.impl.ExeSearch;

public class client {
	private int serverPort;
	private String serverHost;

	private BufferedWriter os = null;
	private BufferedReader is = null;
	private Socket socketOfClient = null;
	private User u = null;
	private thread1 t1 = new thread1();
	private ConnectToServer connectSever = null;
	private HomeController home = null;

	int loginCode = 1;
	int registerCode = 2;
	int messageCode = 3;
	int searchCode = 4;

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public ConnectToServer getConnectSever() {
		return connectSever;
	}

	public void setConnectSever(ConnectToServer connectSever) {
		this.connectSever = connectSever;
	}

	public HomeController getHome() {
		return home;
	}

	public void setHome(HomeController home) {
		this.home = home;
	}

	public client() {
		Logger logger = Logging.getLogger();

		FileReader reader = null;
		Properties p = null;
		try {
			reader = new FileReader("config/application.properties");
			p = new Properties();
			p.load(reader);
			serverHost = p.getProperty("SERVER_HOST").toString();
			serverPort = Integer.parseInt(p.getProperty("SERVER_PORT"));
		} catch (IOException e) {
			logger.severe(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.severe(e.getMessage());
				}
			}
		}
		try {
			// Gửi yêu cầu kết nối tới Server đang lắng nghe
			socketOfClient = new Socket(serverHost, serverPort);

			// Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
			os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
			// Luồng đầu vào tại Client (Nhận dữ liệu từ server).
			is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + serverHost);
			return;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + serverHost);
			return;
		}

		t1.start();
	}

	class thread1 extends Thread {
		public void run() {
			while (true) {
				try {
					String s = is.readLine();
					if (s != null && s.equals("OK")) {
						break;
					}
					if (s != null) {
						int code = Integer.parseInt(s.substring(0, 1));

						Execute exe=null;

						if (code == loginCode) {
							exe=new ExeLogin();
						} else if (code == registerCode) {
							exe=new ExeRegister();
						} else if (code == searchCode) {
							exe=new ExeSearch();
						} else if (code == messageCode) {
							exe=new ExeMessage();
						}
						if (exe!=null) {
							exe.exe(connectSever, s.substring(1));
						}
					}
					sleep(1000);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendLogin(String messageLogin) {
		send(loginCode+messageLogin);
	}
	public void sendRegister(String messageRegister) {
		send(registerCode+messageRegister);
	}
	public void sendSearch(String messageSearch) {
		send(searchCode+messageSearch);
	}
	public void sendMess(String message) {
		send(messageCode+message);
	}
	public void send(String s) {
		try {
			os.write(s);
			System.out.println(s);
			os.newLine();
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	public void closeClient() throws IOException {
		os.write("QUIT");
		os.newLine();
		os.flush();
		t1.stop();
		os.close();
		is.close();
		socketOfClient.close();
	}
}
