package login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import model.User;

public class client {
	private BufferedWriter os = null;
	private BufferedReader is = null;
	Socket socketOfClient = null;
	private User u = null;
	thread1 t1 = new thread1();
	FileWriter fw=null;
	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public client() {
		// Địa chỉ máy chủ.
		final String serverHost = "localhost";
		this.u = u;
		try {
			// Gửi yêu cầu kết nối tới Server đang lắng nghe
			// trên máy 'localhost' cổng 7777.
			socketOfClient = new Socket(serverHost, 7777);

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
						fw = new FileWriter("tranferMessage",true);
			            fw.write(s+"\n");
			            fw.close();
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
