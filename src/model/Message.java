package model;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1812908819029669642L;
	
	private long messageId;
	private long sourseId;
	private long targetId;
	private String content;
	
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public long getSourseId() {
		return sourseId;
	}
	public void setSourseId(long sourseId) {
		this.sourseId = sourseId;
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
	
	@Override
	public String toString() {
		return "{"
				+"\"sourseId\":"+sourseId+","
				+"\"targetId\":"+targetId+","
				+"\"content\":\""+targetId+"\""
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
