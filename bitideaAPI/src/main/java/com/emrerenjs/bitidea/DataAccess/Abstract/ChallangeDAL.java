package com.emrerenjs.bitidea.DataAccess.Abstract;

import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChallangeDAL extends MongoRepository<Challange,String> {

    List<Challange> findByGroupName(String codeGroupName);

    @Query(value = "{ $or: [{'challangeHeader' : {$regex : ?0, $options: 'i'}}, {'content' : {challangeBody : ?0, $options: 'i'} }]}")
    List<Challange> searchByKey(String key);
}

