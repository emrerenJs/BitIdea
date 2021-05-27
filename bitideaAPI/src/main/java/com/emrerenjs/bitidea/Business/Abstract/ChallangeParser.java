package com.emrerenjs.bitidea.Business.Abstract;

import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Model.Challange.TestScenarioModel;

public interface ChallangeParser {
    TestScenarioModel[] getTestScenarios(Challange challange);
}
