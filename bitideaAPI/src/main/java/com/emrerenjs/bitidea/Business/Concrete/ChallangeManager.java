package com.emrerenjs.bitidea.Business.Concrete;

import com.emrerenjs.bitidea.Business.Abstract.ChallangeCheckerService;
import com.emrerenjs.bitidea.Business.Abstract.ChallangeParser;
import com.emrerenjs.bitidea.Business.Abstract.ChallangeService;
import com.emrerenjs.bitidea.Business.Abstract.ProfileService;
import com.emrerenjs.bitidea.Business.Concrete.ChallangeChecker.CCheckerManager;
import com.emrerenjs.bitidea.Business.Concrete.ChallangeChecker.JavaCheckerManager;
import com.emrerenjs.bitidea.DataAccess.Abstract.ChallangeDAL;
import com.emrerenjs.bitidea.DataAccess.Abstract.CodeGroupDAL;
import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Entity.MongoDB.ChallangeAnswer;
import com.emrerenjs.bitidea.Entity.MySQL.CodeGroup;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Entity.MySQL.ProfilesCodeGroups;
import com.emrerenjs.bitidea.Error.RecordNotFoundException;
import com.emrerenjs.bitidea.Error.WrongChallangeAnswerException;
import com.emrerenjs.bitidea.Model.Challange.*;
import com.emrerenjs.bitidea.Model.General.SearchKeyModel;
import com.emrerenjs.bitidea.Model.Security.UserDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ChallangeManager implements ChallangeService {

    @Autowired
    ChallangeDAL challangeDAL;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ChallangeParser challangeParser;

    private Profile getActiveProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
        String username = userDetailsModel.getUsername();
        return profileService.getProfile(username);
    }

    @Override
    public void createChallange(Challange challange) {
        Profile profile = getActiveProfile();
        challange.setChallangeOwnerUsername(profile.getUsername());
        challange.setAnswers(new ArrayList<>());
        challange.setGroupName(challange.getGroupName());
        challangeDAL.insert(challange);
    }

    @Override
    public Challange getChallangeById(String id) {
        Optional<Challange> challangeOp = challangeDAL.findById(id);
        if(challangeOp.isPresent()){
            Challange challange = challangeOp.get();
            if(challange.getGroupName().equals("")){
                return challange;
            }else{
                Profile profile = this.getActiveProfile();
                Optional<ProfilesCodeGroups> challangeGroup = profile.getGroups().stream().filter(g -> g.getCodeGroup().getName().equals(challange.getGroupName())).findFirst();
                if(challangeGroup.isPresent()){
                    return challange;
                }else{
                    throw new RecordNotFoundException("Aradığınız kayıt bulunamadı!");
                }
            }
        }else{
            throw new RecordNotFoundException("Aradığınız kayıt bulunamadı!");
        }
    }

    @Override
    public ChallangeResponeModel getChallangeByIdToResponse(String id){
        Challange challange = this.getChallangeById(id);
        TestScenarioModel[] testScenarioModels = challangeParser.getTestScenarios(challange);
        ChallangeResponeModel challangeResponeModel = new ChallangeResponeModel();
        challangeResponeModel.setId(challange.getId());
        challangeResponeModel.setHeader(challange.getChallangeHeader());
        challangeResponeModel.setBody(challange.getChallangeBody());

        ChallangeParameterType output = new ChallangeParameterType();
        output.setDimension(testScenarioModels[0].getOutput().getDimension());
        output.setType(testScenarioModels[0].getOutput().getType());
        challangeResponeModel.setOutputType(output);

        IOModel[] scenarioInputs = testScenarioModels[0].getInputs();
        ChallangeParameterType[] inputs = new ChallangeParameterType[scenarioInputs.length];
        for(int i = 0; i < inputs.length; i++){
            inputs[i] = new ChallangeParameterType();
            inputs[i].setDimension(scenarioInputs[i].getDimension());
            inputs[i].setType(scenarioInputs[i].getType());
        }
        challangeResponeModel.setParameterTypes(inputs);
        return challangeResponeModel;
    }

    @Override
    public void submitAnswer(ChallangeAnswerModel challangeAnswerModel) {
        Optional<Challange> challange = challangeDAL.findById(challangeAnswerModel.getChallangeID());
        if(challange.isPresent()){
            ChallangeCheckerService challangeCheckerService;
            if(challangeAnswerModel.getProgrammingLanguage().equals(ChallangeAnswer.LANG_JAVA)){
                challangeCheckerService = new JavaCheckerManager();
            }else{
                challangeCheckerService = new CCheckerManager();
            }
            Profile profile = this.getActiveProfile();
            if(!challangeCheckerService.checkAnswer(challange.get(),challangeAnswerModel,profile.getUsername())){
                throw new WrongChallangeAnswerException("Programınız testleri geçemedi..");
            }else{
                //save answer to db
            }
        }else{
            throw new RecordNotFoundException("Aradığınız challange bulunamadı!");
        }
    }

    @Override
    public List<Challange> getChallangesByKey(SearchKeyModel searchKeyModel) {
        List<Challange> challanges = challangeDAL.searchByKey(searchKeyModel.getKey().toLowerCase());
        return challanges;
    }
}
