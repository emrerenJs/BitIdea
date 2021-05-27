package com.emrerenjs.bitidea.Model.Post;

import com.emrerenjs.bitidea.Model.HttpRequestModel;

import lombok.Data;

@Data
public class CreateCommentModel implements HttpRequestModel{
	private String postId;
	private String comment;

	@Override
	public boolean isFieldsNull() {
		return this.comment == null || this.comment.trim().equals("") || this.postId == null || this.postId.trim().equals("");
	}
	
}
