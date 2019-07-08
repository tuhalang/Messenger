package model;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import service.connectMongodb;

public class User implements Serializable{
	
	private static final long serialVersionUID = -3265501766355628814L;
	
	private long userId;
	private String username;
	private String password;
	private short sex;
	private boolean enabled;
	
	
	private connectMongodb connect=new connectMongodb();
	
	
	
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
		return "{"
				+"\"userId\":"+userId+","
				+"\"username\":\""+username+"\","
				+"\"password\":\""+password+"\","
				+"\"enabled\":"+enabled+""
				+ "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof User && this.userId == ((User) obj).userId;
	}
	
	@Override
	public int hashCode() {
		return (int)userId;
	}
	
	public ArrayList<User> getListFriend(){
		ArrayList<User> u=new ArrayList<User>();
		try {
			MongoClient mongo=connectMongodb.getMongoClient_1();
			DB db=(DB) mongo.getDB("demo");
			DBCollection dept=db.getCollection("friend");
			BasicDBObjectBuilder whereBuilder=BasicDBObjectBuilder.start();
			whereBuilder.append("sourceId", this.getUserId());
			DBObject where=whereBuilder.get();
			DBCursor cursor=dept.find(where);
			while(cursor.hasNext()) {
				dept=db.getCollection("user");
				BasicDBObjectBuilder wbuilder=BasicDBObjectBuilder.start();
				wbuilder.append("user",cursor.next().get("userId"));
				where=wbuilder.get();
				DBCursor cursor2=dept.find(where);
				if (cursor2.hasNext()) {
					DBObject rs=cursor2.next();
					User user=new User((String)rs.get("username"),(String)rs.get("password"));
					u.add(user);
				}
				cursor.next();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}
	public boolean checkExist() {
		try {
			MongoClient mongo=connectMongodb.getMongoClient_1();
			DB db=(DB) mongo.getDB("demo");
			DBCollection dept=db.getCollection("user");
			BasicDBObjectBuilder whereBuilder=BasicDBObjectBuilder.start();
			whereBuilder.append("username", this.getUsername());
			DBObject where=whereBuilder.get();
			DBCursor cursor=dept.find(where);
			if (cursor.hasNext()) return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public ArrayList<Message> getListMessage(User friend){
		ArrayList<Message> listM=new ArrayList<Message>();
		try {
			MongoClient mongo=connectMongodb.getMongoClient_1();
			DB db=(DB) mongo.getDB("demo");
			DBCollection dept=db.getCollection("message");
			BasicDBObjectBuilder whereBuilder=BasicDBObjectBuilder.start();
			whereBuilder.append("sourceId", this.getUserId());
			whereBuilder.append("targetId", friend.getUserId());
			DBObject where=whereBuilder.get();
			DBCursor cursor=dept.find(where);
			while(cursor.hasNext()) {
				DBObject rs=cursor.next();
				Message m=new Message((int)rs.get("messageId"), (int)rs.get("sourceId"),(int)rs.get("targetId"),(String)rs.get("content"),(String)rs.get("image"));
				listM.add(m);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return listM;
	}
	public void addFriend(User friend) {
		MongoClient mongo;
		try {
			mongo = connectMongodb.getMongoClient_1();
			DB db=(DB) mongo.getDB("demo");
			DBCollection dept=db.getCollection("friend");
			BasicDBObject document = new BasicDBObject();
			document.put("sourceId", this.getUserId());
			document.put("targetId", friend.getUserId());
			dept.insert(document);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}