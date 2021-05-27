package com.emrerenjs.bitidea.Model.Challange;

import lombok.Data;

@Data
public class CreateChallangeModel {
    private String challangeHeader;
    private String challangeBody;
    private String testScenariosString;
    private String groupName;
}
