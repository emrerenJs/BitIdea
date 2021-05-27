package com.emrerenjs.bitidea.Model.Post;

import java.util.Optional;

import com.emrerenjs.bitidea.Entity.MongoDB.Post;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponseModel {
	private Optional<Post> post;
	private Profile owner;
}
