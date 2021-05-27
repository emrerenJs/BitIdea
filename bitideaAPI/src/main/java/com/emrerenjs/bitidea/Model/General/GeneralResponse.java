package com.emrerenjs.bitidea.Model.General;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse {
    private int status;
    private String header;
    private String body;
}
