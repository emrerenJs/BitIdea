package com.emrerenjs.bitidea.Model.Security;

import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Model.General.GeneralResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileCredentialsResponseModel extends GeneralResponse{
	private Profile profile;
	
	public ProfileCredentialsResponseModel(int status,String header, String body, Profile profile) {
		super(status,header,body);
		this.profile = profile;
	}

}
