package com.emrerenjs.bitidea.Model.Error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseModel {
	private int status;
    private String header;
    private String body;
}
