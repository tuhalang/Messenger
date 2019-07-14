package model;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import Logging.Logging;
import service.connectMongodb;

public class User implements Serializable {

	private static final long serialVersionUID = -3265501766355628814L;

	private long userId;
	private String username;
	private String password;
	private short sex;
	private boolean enabled;

	private connectMongodb connect = new connectMongodb();
	Logger logger = Logging.getLogger();

	public User() {
		super();
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(long userId, String username, String password, short sex, boolean enabled) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.enabled = enabled;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public short getSex() {
		return sex;
	}

	public void setSex(short sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "{" + "\"userId\":" + userId + "," + "\"username\":\"" + username + "\"," + "\"password\":\"" + password
				+ "\"," + "\"enabled\":" + enabled + "" + "}";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof User && this.userId == ((User) obj).userId;
	}

	@Override
	public int hashCode() {
		return (int) userId;
	}

	public List<Message> getListMessage(User friend) {
		List<Message> listM = new ArrayList<Message>();
		try {
			MongoClient mongo = connectMongodb.getMongoClient_1();
			DB db = (DB) mongo.getDB("demo");
			DBCollection dept = db.getCollection("message");
			BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();
			whereBuilder.append("sourceId", this.getUserId());
			whereBuilder.append("targetId", friend.getUserId());
			DBObject where = whereBuilder.get();
			DBCursor cursor = dept.find(where);
			while (cursor.hasNext()) {
				DBObject rs = cursor.next();
				Message m = new Message(this.getUserId(), friend.getUserId(), (String) rs.get("content"),
						(String) rs.get("image"));
				listM.add(m);
			}
		} catch (UnknownHostException e) {
			logger.severe("User:" + e.getMessage());
		}
		return listM;
	}

	public void addMessageToDatabase(Message m) {
		MongoClient mongo;
		try {
			mongo = connectMongodb.getMongoClient_1();
			DB db = (DB) mongo.getDB("demo");
			DBCollection dept = db.getCollection("message");
			BasicDBObject document = new BasicDBObject();
			document.put("sourceId", m.getSourceId());
			document.put("targetId", m.getTargetId());
			document.put("content", m.getContent());
			document.put("image", m.getImage());
			dept.insert(document);
		} catch (UnknownHostException e) {
			logger.severe("User:" + e.getMessage());
		}
	}
}