package model;

import java.io.Serializable;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import service.connectMongodb;

public class Message implements Serializable{

	private static final long serialVersionUID = 1812908819029669642L;
	
	private long messageId;
	private long sourceId;
	private long targetId;
	private String content;
	private String image;
	
	public Message() {
		super();
	}
	public Message(long messageId, long sourceId, long targetId, String content, String image) {
		super();
		this.messageId = messageId;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.content = content;
		this.image = image;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setsourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "{"
				+"\"sourceId\":"+sourceId+","
				+"\"targetId\":"+targetId+","
				+"\"content\":\""+targetId+"\","
				+"\"image\":\""+image+"\""
				+ "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Message && this.messageId == ((Message) obj).messageId;
	}
	
	@Override
	public int hashCode() {
		return (int)messageId;
	}
	
	public void addMessageToDatabase(Message m) {
		MongoClient mongo;
		try {
			mongo = connectMongodb.getMongoClient_1();
			DB db=(DB) mongo.getDB("demo");
			DBCollection dept=db.getCollection("message");
			BasicDBObject document = new BasicDBObject();
			document.put("sourceId", m.getSourceId());
			document.put("targetId", m.getTargetId());
			dept.insert(document);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}