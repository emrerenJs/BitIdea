package com.emrerenjs.bitidea.Business.Abstract;

import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Model.Challange.ChallangeAnswerModel;

public interface ChallangeCheckerService {
    boolean checkAnswer(Challange challange, ChallangeAnswerModel challangeAnswerModel,String username);
}
