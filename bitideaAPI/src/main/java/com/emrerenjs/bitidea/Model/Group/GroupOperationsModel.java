package com.emrerenjs.bitidea.Model.Group;

import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Model.HttpRequestModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupOperationsModel implements HttpRequestModel{

	private String groupName;
	
	@Override
	public boolean isFieldsNull() {
		return this.groupName == null || this.groupName.trim().equals("");
	}

}
