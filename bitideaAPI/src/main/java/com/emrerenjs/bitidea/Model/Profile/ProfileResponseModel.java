package com.emrerenjs.bitidea.Model.Profile;

import com.emrerenjs.bitidea.Entity.MongoDB.Post;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseModel {
    private Profile profile;
    private List<Post> profilePosts;
}
