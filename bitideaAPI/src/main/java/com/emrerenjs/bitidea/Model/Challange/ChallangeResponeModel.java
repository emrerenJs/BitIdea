package com.emrerenjs.bitidea.Model.Challange;

import lombok.Data;

@Data
public class ChallangeResponeModel {
    private String id;
    private String header;
    private String body;
    private ChallangeParameterType[] parameterTypes;
    private ChallangeParameterType outputType;
}
