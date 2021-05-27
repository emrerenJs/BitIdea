package com.emrerenjs.bitidea.Entity.MongoDB;

import org.springframework.data.annotation.Id;

public class PostComments {	
	
	@Id
	private String id;
	private String senderUsername;
	private String senderPhoto;
	private String comment;
	private String date;
		
	public PostComments(String id, String senderUsername, String senderPhoto, String comment, String date) {
		super();
		this.id = id;
		this.senderUsername = senderUsername;
		this.senderPhoto = senderPhoto;
		this.comment = comment;
		this.date = date;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSenderUsername() {
		return senderUsername;
	}
	
	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}
	
	public String getSenderPhoto() {
		return senderPhoto;
	}
	
	public void setSenderPhoto(String senderPhoto) {
		this.senderPhoto = senderPhoto;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
}
