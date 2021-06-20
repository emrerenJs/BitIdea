package com.emrerenjs.bitidea.Business.Abstract;

import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Model.Challange.ChallangeAnswerModel;
import com.emrerenjs.bitidea.Model.Challange.ChallangeResponeModel;
import com.emrerenjs.bitidea.Model.General.SearchKeyModel;

import java.util.List;
import java.util.Optional;

public interface ChallangeService {

    void createChallange(Challange challange);
    Challange getChallangeById(String id);

    ChallangeResponeModel getChallangeByIdToResponse(String id);

    void submitAnswer(ChallangeAnswerModel challangeAnswerModel);

    List<Challange> getChallangesByKey(SearchKeyModel searchKeyModel);
}
