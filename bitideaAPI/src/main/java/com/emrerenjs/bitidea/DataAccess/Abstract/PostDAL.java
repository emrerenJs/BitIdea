package com.emrerenjs.bitidea.DataAccess.Abstract;

import com.emrerenjs.bitidea.Entity.MongoDB.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostDAL extends MongoRepository<Post,String> {
    List<Post> findByUsername(String username);
    @Query(value = "{ $or: [{'header' : {$regex : ?0, $options: 'i'}}, {'content' : {$regex : ?0, $options: 'i'} }]}")
    List<Post> searchByKey(String key);
}
