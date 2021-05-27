package com.emrerenjs.bitidea.Model.Challange;

import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import lombok.Data;

@Data
public class ChallangeAnswerModel {
    private String challangeID;
    private String challangeAnswer;
    private String programmingLanguage;
}
