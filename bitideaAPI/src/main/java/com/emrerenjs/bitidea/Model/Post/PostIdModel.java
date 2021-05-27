package com.emrerenjs.bitidea.Model.Post;

import com.emrerenjs.bitidea.Model.HttpRequestModel;

import java.io.Serializable;

public class PostIdModel implements Serializable, HttpRequestModel {
    private String postId;

    public PostIdModel() {
    }

    public PostIdModel(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }


    @Override
    public boolean isFieldsNull() {
        return this.postId == null || this.postId.trim().equals("");
    }
}
