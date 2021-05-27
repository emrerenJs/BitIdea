package com.emrerenjs.bitidea.DataAccess.Abstract;

import com.emrerenjs.bitidea.Entity.MySQL.CodeGroup;
import com.emrerenjs.bitidea.Entity.MySQL.ProfilesCodeGroups;

public interface CodeGroupDAL {
	
	void createGroup(CodeGroup codeGroup);
	CodeGroup getGroupByGroupName(String groupName);
	void removeGroup(CodeGroup codeGroup);
	void updateGroup(CodeGroup codeGroup);

    void removeMemberFromGroup(ProfilesCodeGroups profilesCodeGroups);
}
