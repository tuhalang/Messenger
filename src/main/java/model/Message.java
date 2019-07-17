package model;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Date;

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
	private String icon;
	private String content;
	private String image;
	private int seen;
	private Date date;
	public Message() {
		super();
	}
	public Message(long sourceId, long targetId, String content, String image) {
		super();
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.content = content;
		this.image = image;
		this.icon="";
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
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
	
	public int getSeen() {
		return seen;
	}
	public void setSeen(int seen) {
		this.seen = seen;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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

}