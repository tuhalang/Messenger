package model;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Logger;

import Logging.Logging;
import service.impl.Handle;

public class Client {

	private static final Logger logger = Logging.getLogger();
	private int serverPort;
	private String serverHost;
	private Socket socket = null;
	private User user = null;
	private boolean valid = false;

	public Client(User user) {
		readConfigFile();
		this.user = user;

		// connect to server
		BufferedWriter bw = null;
		try {
			socket = new Socket(serverHost, serverPort);

			// gửi username để làm key trên HashTable server
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write(user.getUsername());
			bw.newLine();
			bw.flush();

		} catch (UnknownHostException e) {
			logger.severe("Don't know about host " + serverHost);
		} catch (IOException e) {
			logger.severe("Couldn't get I/O for the connection to " + serverHost);
		} finally {
//			if (outToServer != null) {
//				try {
//					outToServer.close();
//				} catch (IOException e) {
//					logger.severe(e.getMessage());
//				}
//			}
		}
	}

	public void readConfigFile() {
		// Read file properties
		FileReader reader = null;
		Properties p = null;
		try {
			reader = new FileReader("config/application.properties");
			p = new Properties();
			p.load(reader);
			serverHost = p.getProperty("SERVER_HOST");
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
	}

	public void send(String code, String msg) {
		Handle.send(this, code, msg);
	}

	public void receive() {
		Handle.receive(this);
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
