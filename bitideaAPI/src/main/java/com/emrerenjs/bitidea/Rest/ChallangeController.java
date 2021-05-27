package com.emrerenjs.bitidea.Rest;

import com.emrerenjs.bitidea.Business.Abstract.ChallangeService;
import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Model.Challange.ChallangeAnswerModel;
import com.emrerenjs.bitidea.Model.Challange.ChallangeIdModel;
import com.emrerenjs.bitidea.Model.Challange.ChallangeResponeModel;
import com.emrerenjs.bitidea.Model.Challange.CreateChallangeModel;
import com.emrerenjs.bitidea.Model.General.GeneralResponse;
import com.emrerenjs.bitidea.Model.Group.GroupOperationsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/challange")
public class ChallangeController {

    @Autowired
    private ChallangeService challangeService;

    @PostMapping("/createChallange")
    public ResponseEntity<?> createChallange(@RequestBody Challange challange){
        this.challangeService.createChallange(challange);
        return ResponseEntity.ok(new GeneralResponse(200,"ok","ok"));
    }

    @PostMapping("/getChallangeById")
    public ChallangeResponeModel getChallangeById(@RequestBody ChallangeIdModel challangeIdModel){
        ChallangeResponeModel challange = this.challangeService.getChallangeByIdToResponse(challangeIdModel.getChallangeId());
        return challange;
    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<?> submitAnswer(@RequestBody ChallangeAnswerModel challangeAnswerModel){
        this.challangeService.submitAnswer(challangeAnswerModel);
        return ResponseEntity.ok(new GeneralResponse(200,"ok","ok"));
    }

}
