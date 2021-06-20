package com.emrerenjs.bitidea.Business.Abstract;

import java.util.List;

import com.emrerenjs.bitidea.Entity.MySQL.CodeGroup;
import com.emrerenjs.bitidea.Entity.MySQL.GroupAnnouncement;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Model.General.SearchKeyModel;
import com.emrerenjs.bitidea.Model.Group.*;

public interface GroupService {
	
	void createGroup(GroupOperationsModel groupOperationsModel);
	void removeGroup(GroupOperationsModel groupOperationsModel);
	
	void acceptUser(UserOperationsModel userOperationsModel);
	void denyUser(UserOperationsModel userOperationsModel);
	void inviteUser(UserOperationsModel userOperationsModel);
	void kickUser(UserOperationsModel userOperationsModel);

	
	void joinGroup(GroupOperationsModel groupOperationsModel);
	void acceptInvite(GroupOperationsModel groupOperationsModel);
	void denyInvite(GroupOperationsModel groupOperationsModel);
	void leaveGroup(GroupOperationsModel groupOperationsModel);
	
	List<Profile> getGroupMembers(GroupOperationsModel groupOperationsModel);
	List<Profile> getGroupJoinRequests(GroupOperationsModel groupOperationsModel);
	List<CodeGroup> getGroupInvites(String username);

	void addAnnouncement(AnnouncementModel announcementModel);
	void addCommentToAnnouncement(AnnouncementCommentModel announcementCommentModel);
	List<GroupAnnouncement> getAnnouncements(GroupOperationsModel groupOperationsModel);

    CodeGroupResponseModel getGroupByGroupName(GroupOperationsModel groupOperationsModel);

    List<CodeGroup> getUserGroups();

    List<CodeGroup> getGroupsByKey(SearchKeyModel searchKeyModel);
}
