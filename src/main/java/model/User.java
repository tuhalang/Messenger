package model;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bson.BSONObject;

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

	Logger logger = Logging.getLogger();

	public User() {
		super();
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(long userId, String username, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
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

	public List<Message> getListMessage(User friend,long numberOfMessage) {
		List<Message> listM = new ArrayList<Message>();
		long size=getSizeListMessage(this.getUserId(), friend.getUserId());
		try {
			MongoClient mongo = connectMongodb.getMongoClient_1();
			DB db = (DB) mongo.getDB("demo");
			DBCollection dept = db.getCollection("message");
			List<DBObject> case1=new ArrayList<DBObject>();
			case1.add(new BasicDBObject("sourceId", this.getUserId()));
			case1.add(new BasicDBObject("targetId", friend.getUserId()));
			List<DBObject> case2=new ArrayList<DBObject>();
			case2.add(new BasicDBObject("targetId", this.getUserId()));
			case2.add(new BasicDBObject("sourceId", friend.getUserId()));
			List<DBObject> ORcase=new ArrayList<DBObject>();
			ORcase.add(new BasicDBObject("$and",case1));
			ORcase.add(new BasicDBObject("$and",case2));
			List<DBObject> criteria = new ArrayList<DBObject>();
			criteria.add(new BasicDBObject("$or",ORcase));
			criteria.add((DBObject) new BasicDBObject("id", new BasicDBObject("$lte", size-numberOfMessage)));
			criteria.add((DBObject) new BasicDBObject("id", new BasicDBObject("$gte", size-numberOfMessage-19)));
			DBCursor cursor = dept.find(new BasicDBObject("$and", criteria));
			while (cursor.hasNext()) {
				DBObject rs = cursor.next();
				Message m = new Message(Long.parseLong(rs.get("sourceId").toString()),Long.parseLong( rs.get("targetId").toString()), (String) rs.get("content"),
						(String) rs.get("image"));
				listM.add(m);
			}
		} catch (UnknownHostException e) {
			logger.severe("User:" + e.getMessage());
		}
		return listM;
	}

	public void addMessageToDatabase(Message m) {
		try {
			long size = getSizeListMessage(m.getSourceId(),m.getTargetId());
			MongoClient mongo = connectMongodb.getMongoClient_1();
			DB db = (DB) mongo.getDB("demo");
			DBCollection dept = db.getCollection("message");
			BasicDBObject document = new BasicDBObject();
			document.put("id", size+1);
			document.put("sourceId", m.getSourceId());
			document.put("targetId", m.getTargetId());
			document.put("content", m.getContent());
			document.put("image", m.getImage());
			dept.insert(document);
		} catch (UnknownHostException e) {
			logger.severe("User:" + e.getMessage());
		}
	}

	public long getSizeListMessage(long l,long m) {
		try {
			MongoClient mongo = connectMongodb.getMongoClient_1();
			DB db = (DB) mongo.getDB("demo");
			DBCollection dept = db.getCollection("message");
			List<DBObject> criteria = new ArrayList<DBObject>();
			criteria.add(new BasicDBObject("sourceId", l));
			criteria.add(new BasicDBObject("targetId", m));
			DBCursor cursor = dept.find(new BasicDBObject("$and", criteria));
			return cursor.size();
		} catch (UnknownHostException e) {
			logger.severe("User:" + e.getMessage());
			return 0;
		}
	}
}