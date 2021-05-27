package com.emrerenjs.bitidea.Model.Post;

import java.io.Serializable;

public class CreatePostModel implements Serializable {

    private String contentHeader;
    private String contentBody;

    public CreatePostModel() {
    }

    public CreatePostModel(String contentHeader, String contentBody) {
        this.contentHeader = contentHeader;
        this.contentBody = contentBody;
    }

    public String getContentHeader() {
        return contentHeader;
    }

    public void setContentHeader(String contentHeader) {
        this.contentHeader = contentHeader;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }
}
