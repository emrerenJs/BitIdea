package com.emrerenjs.bitidea.Business.Concrete.ChallangeChecker;

import com.emrerenjs.bitidea.Business.Abstract.ChallangeCheckerService;
import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Model.Challange.ChallangeAnswerModel;

public class CCheckerManager implements ChallangeCheckerService {
    @Override
    public boolean checkAnswer(Challange challange, ChallangeAnswerModel challangeAnswerModel, String username) {
        return false;
    }
}
