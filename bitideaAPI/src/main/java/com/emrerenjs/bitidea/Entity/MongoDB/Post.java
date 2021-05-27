package com.emrerenjs.bitidea.Entity.MongoDB;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Post")
public class Post {
    @Id
    private String id;
    private String username;
    private String bigPicture;
    private String header;
    private String content;
    private List<PostComments> postComments;
}
