package com.emrerenjs.bitidea.Business.Concrete;

import com.emrerenjs.bitidea.Business.Abstract.ChallangeParser;
import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Model.Challange.TestScenarioModel;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class JSONChallangeParser implements ChallangeParser {
    @Override
    public TestScenarioModel[] getTestScenarios(Challange challange){
        Gson gson = new Gson();
        String testScenariosString = challange.getTestScenariosString();
        TestScenarioModel[] testScenarioModels = gson.fromJson(testScenariosString,TestScenarioModel[].class);
        return testScenarioModels;
    }
}
