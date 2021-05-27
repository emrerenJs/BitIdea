package com.emrerenjs.bitidea.DataAccess.Abstract;

import com.emrerenjs.bitidea.Entity.MongoDB.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostDAL extends MongoRepository<Post,String> {
    List<Post> findByUsername(String username);
}
