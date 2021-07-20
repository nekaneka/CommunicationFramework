package com.dse.cf.messagesdata;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@SuppressWarnings("serial")
public class Message implements Serializable {

	private String messageId; 
	private String topic; 
	private String senderId; 
	private String receiverId; 
	private Object senderObject; 
	private Timestamp messageCreationTime; 
	
	public Message() {}
	
	public Message(String topic, String sender, String receiver, Object data) {
		this.messageCreationTime = new Timestamp(System.currentTimeMillis());
		this.messageId = UUID.randomUUID().toString(); 
		this.topic = topic.toUpperCase(); 
		this.senderId = sender; 
		this.receiverId = receiver; 
		this.senderObject = data; 
	}

	public String getMessageId() {
		return messageId;
	}

	public String getTopic() {
		return topic;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public Object getSenderObject() {
		return senderObject;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
 
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public void setSenderObject(Object senderObject) {
		this.senderObject = senderObject;
	}
	
	public Timestamp getMessageCreationTime() {
		return messageCreationTime;
	}
	
	
}
