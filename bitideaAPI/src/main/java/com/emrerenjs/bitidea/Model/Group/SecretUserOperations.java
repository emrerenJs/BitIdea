package com.emrerenjs.bitidea.Model.Group;

import com.emrerenjs.bitidea.Model.HttpRequestModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SecretUserOperations implements HttpRequestModel{
	private String groupName;
	private String username;
	private String token;
	@Override
	public boolean isFieldsNull() {
		// TODO Auto-generated method stub
		return this.groupName == null || this.groupName.trim().equals("")
			|| this.username == null || this.username.trim().equals("")
			|| this.token == null || this.token.trim().equals("");
	}
	
	
	
}
