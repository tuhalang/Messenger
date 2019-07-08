package service;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import model.User;

public class checkUser {

	public boolean isValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public boolean checkExistUsername(String username) {
		if (username.length() < 6)
			return false;
		MongoClient mongo;
		try {
			mongo = connectMongodb.getMongoClient_1();
			@SuppressWarnings("deprecation")
			DB db = (DB) mongo.getDB("demo");
			DBCollection dept = db.getCollection("user");
			BasicDBObjectBuilder whereBuilder = BasicDBObjectBuilder.start();
			whereBuilder.append("username", username);
			DBObject where = whereBuilder.get();
			DBCursor cursor = dept.find(where);
			if (cursor.hasNext())
				return false;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
		return true;
	}

	public boolean createAccount(User u) {
		MongoClient mongo;
		try {
			mongo = connectMongodb.getMongoClient_1();
			DB db=mongo.getDB("demo");
			DBCollection dept=db.getCollection("user");
			BasicDBObject document = new BasicDBObject();
			document.put("username", u.getUsername());
			document.put("password", u.getPassword());
			document.put("sex", u.getSex());
			document.put("enabled", 1);
			dept.insert(document);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}