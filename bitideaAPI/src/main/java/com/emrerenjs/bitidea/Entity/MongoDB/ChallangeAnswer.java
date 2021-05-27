package com.emrerenjs.bitidea.Entity.MongoDB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChallangeAnswer {
    public static final String LANG_C = "C";
    public static final String LANG_JAVA = "Java";
    public static final String LANG_PYTHON = "Python";
    @Id
    private String id;
    private String codeOwnerUsername;
    private String code;
    private Date created_at;
    private String programmingLanguage;
}
