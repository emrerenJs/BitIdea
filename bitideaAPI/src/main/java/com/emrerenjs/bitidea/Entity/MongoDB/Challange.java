package com.emrerenjs.bitidea.Entity.MongoDB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Challange")
public class Challange implements Serializable {
    @Id
    private String id;
    private String challangeHeader;
    private String challangeBody;
    private String challangeOwnerUsername;
    private String groupName;
    private String testScenariosString;
    private List<ChallangeAnswer> answers;

}
