package com.emrerenjs.bitidea.Model.Group;

import com.emrerenjs.bitidea.Model.HttpRequestModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserOperationsModel implements HttpRequestModel{
	private String groupName;
	private String username;
	@Override
	public boolean isFieldsNull() {
		// TODO Auto-generated method stub
		return this.groupName == null || this.groupName.trim().equals("")
			|| this.username == null || this.username.trim().equals("")	;
	}
	
	
}
