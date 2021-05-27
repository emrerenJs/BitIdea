package com.emrerenjs.bitidea.DataAccess.Abstract;

import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChallangeDAL extends MongoRepository<Challange,String> {

    List<Challange> findByGroupName(String codeGroupName);

}

