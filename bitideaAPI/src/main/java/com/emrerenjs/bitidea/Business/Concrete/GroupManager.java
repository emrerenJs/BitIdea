package com.emrerenjs.bitidea.Business.Concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.emrerenjs.bitidea.DataAccess.Abstract.ChallangeDAL;
import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Entity.MySQL.*;
import com.emrerenjs.bitidea.Error.RecordNotFoundException;
import com.emrerenjs.bitidea.Model.Group.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.emrerenjs.bitidea.Business.Abstract.GroupService;
import com.emrerenjs.bitidea.Business.Abstract.ProfileService;
import com.emrerenjs.bitidea.DataAccess.Abstract.CodeGroupDAL;
import com.emrerenjs.bitidea.Model.Security.UserDetailsModel;

import javax.swing.*;

@Service
public class GroupManager implements GroupService{

	private void todo(){
		/*SORGU OPTIMIZASYONU GEREKLI*/
		/*GUVENLIK ICIN BAZI KONTROLLER GEREKLI*/
	}


	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private CodeGroupDAL codeGroupDAL;

	@Autowired
	ChallangeDAL challangeDAL;


	private Profile getActiveProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
        String username = userDetailsModel.getUsername();
        return profileService.getProfile(username);        
	}
	
	@Override
	/*OK*/
	public void createGroup(GroupOperationsModel groupOperationsModel) {
		CodeGroup group = new CodeGroup();
		Profile profile = this.getActiveProfile();
		group.setGroupOwner(profile);
		group.setName(groupOperationsModel.getGroupName());
		group.getGroupMembers();
		ProfilesCodeGroups profilesCodeGroup = new ProfilesCodeGroups();
		profilesCodeGroup.setCodeGroup(group);
		profilesCodeGroup.setProfile(profile);
		profilesCodeGroup.setMemberType(ProfilesCodeGroups.OWNER_USER);
		group.setGroupMembers(new ArrayList<>());
		group.getGroupMembers().add(profilesCodeGroup);
		codeGroupDAL.createGroup(group);
	}

	@Override
	/*OK*/
	public void removeGroup(GroupOperationsModel groupOperationsModel) {
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		if(codeGroup.getGroupOwner().getUsername().equals(this.getActiveProfile().getUsername())) {
			codeGroupDAL.removeGroup(codeGroup);
		}else {
			throw new BadCredentialsException("Buna yetkiniz yok..");
		}
	}

	@Override
	/*OK*/
	public void acceptUser(UserOperationsModel userOperationsModel) {
		Profile acceptedUser = profileService.getProfile(userOperationsModel.getUsername());
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(userOperationsModel.getGroupName());
		if(codeGroup.getGroupOwner().getUsername().equals(this.getActiveProfile().getUsername())) {
			Optional<ProfilesCodeGroups> first = codeGroup.getGroupMembers()
					.stream()
					.filter(gm ->
							gm.getProfile().getId() == acceptedUser.getId() &&
									gm.getCodeGroup().getId() == codeGroup.getId() &&
									gm.getMemberType() == ProfilesCodeGroups.PARTICIPATING_USERS)
					.findFirst();
			if(first.isPresent()){
				ProfilesCodeGroups profilesCodeGroups = first.get();
				profilesCodeGroups.setMemberType(ProfilesCodeGroups.MEMBER_USERS);
				codeGroupDAL.updateGroup(codeGroup);
			}else{
				throw new RecordNotFoundException("Aradığınız kriterlerde bir kayıt bulunamadı!");
			}
		}else {
			throw new BadCredentialsException("Buna yetkiniz yok..");
		}
	}

	@Override
	/*OK*/
	public void denyUser(UserOperationsModel userOperationsModel) {
		Profile deniedProfile = profileService.getProfile(userOperationsModel.getUsername());
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(userOperationsModel.getGroupName());
		if(codeGroup.getGroupOwner().getUsername().equals(this.getActiveProfile().getUsername())) {
			Optional<ProfilesCodeGroups> first = codeGroup.getGroupMembers()
					.stream()
					.filter(gm ->
							gm.getProfile().getId() == deniedProfile.getId() &&
									gm.getCodeGroup().getId() == codeGroup.getId() &&
									gm.getMemberType() == ProfilesCodeGroups.PARTICIPATING_USERS)
					.findFirst();
			if(first.isPresent()){
				ProfilesCodeGroups profilesCodeGroups = first.get();
				codeGroup.getGroupMembers().remove(profilesCodeGroups);
				codeGroupDAL.removeMemberFromGroup(profilesCodeGroups);
			}else{
				throw new RecordNotFoundException("Aradığınız kriterlerde bir kayıt bulunamadı!");
			}
		}else {
			throw new BadCredentialsException("Buna yetkiniz yok..");
		}
	}

	@Override
	/*OK*/
	//USER DAHA ÖNCE DAVET EDİLDİ Mİ?
	public void inviteUser(UserOperationsModel userOperationsModel) {
		Profile invitedUser = profileService.getProfile(userOperationsModel.getUsername());
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(userOperationsModel.getGroupName());
		if(codeGroup.getGroupOwner().getUsername().equals(this.getActiveProfile().getUsername())) {
			ProfilesCodeGroups profilesCodeGroups = new ProfilesCodeGroups();
			profilesCodeGroups.setCodeGroup(codeGroup);
			profilesCodeGroups.setProfile(invitedUser);
			profilesCodeGroups.setMemberType(ProfilesCodeGroups.INVITED_USERS);
			//add record
			codeGroup.getGroupMembers().add(profilesCodeGroups);
			//save transactions
			codeGroupDAL.updateGroup(codeGroup);

		}else {
			throw new BadCredentialsException("Buna yetkiniz yok..");
		}
	}

	@Override
	/*OK*/
	public void kickUser(UserOperationsModel userOperationsModel) {
		//get user & group
		Profile kickedProfile = profileService.getProfile(userOperationsModel.getUsername());
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(userOperationsModel.getGroupName());

		if(codeGroup.getGroupOwner().getUsername().equals(this.getActiveProfile().getUsername())) {
			Optional<ProfilesCodeGroups> first = codeGroup.getGroupMembers()
					.stream()
					.filter(gm ->
							gm.getProfile().getId() == kickedProfile.getId() &&
									gm.getCodeGroup().getId() == codeGroup.getId() &&
									gm.getMemberType() == ProfilesCodeGroups.MEMBER_USERS)
					.findFirst();
			if(first.isPresent()){
				ProfilesCodeGroups profilesCodeGroups = first.get();
				codeGroup.getGroupMembers().remove(profilesCodeGroups);
				codeGroupDAL.removeMemberFromGroup(profilesCodeGroups);
			}else{
				throw new RecordNotFoundException("Aradığınız kriterlerde bir kayıt bulunamadı!");
			}
		}else {
			throw new BadCredentialsException("Buna yetkiniz yok..");
		}
	}

	@Override
	/*OK*/
	//USER DAHA ÖNCE KATILMA İSTEĞİ GÖNDERDİ Mİ?
	public void joinGroup(GroupOperationsModel groupOperationsModel) {
		//get user & group
		Profile joinedUser = this.getActiveProfile();
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		//create new record
		ProfilesCodeGroups profilesCodeGroups = new ProfilesCodeGroups();
		profilesCodeGroups.setCodeGroup(codeGroup);
		profilesCodeGroups.setProfile(joinedUser);
		profilesCodeGroups.setMemberType(ProfilesCodeGroups.PARTICIPATING_USERS);
		//add record
		codeGroup.getGroupMembers().add(profilesCodeGroups);
		//save transactions
		codeGroupDAL.updateGroup(codeGroup);
	}

	@Override
	/*OK*/
	public void acceptInvite(GroupOperationsModel groupOperationsModel) {
		Profile profile = this.getActiveProfile();
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		Optional<ProfilesCodeGroups> first = codeGroup.getGroupMembers()
				.stream()
				.filter(gm ->
						gm.getMemberType() == ProfilesCodeGroups.INVITED_USERS &&
						gm.getCodeGroup().getId() == codeGroup.getId() &&
						gm.getProfile().getId() == profile.getId())
				.findFirst();
		if(first.isPresent()){
			ProfilesCodeGroups profilesCodeGroups = first.get();
			profilesCodeGroups.setMemberType(ProfilesCodeGroups.MEMBER_USERS);
			codeGroupDAL.updateGroup(codeGroup);
		}else {
			throw new RecordNotFoundException("Aradığınız kriterlerde bir kayıt bulunamadı!");
		}
	}

	@Override
	/*OK*/
	public void denyInvite(GroupOperationsModel groupOperationsModel) {
		Profile leaverProfile = this.getActiveProfile();
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		Optional<ProfilesCodeGroups> first = codeGroup.getGroupMembers()
				.stream()
				.filter(gm ->
						gm.getProfile().getId() == leaverProfile.getId() &&
						gm.getCodeGroup().getId() == codeGroup.getId() &&
						gm.getMemberType() == ProfilesCodeGroups.INVITED_USERS)
				.findFirst();
		if(first.isPresent()){
			ProfilesCodeGroups profilesCodeGroups = first.get();
			codeGroup.getGroupMembers().remove(profilesCodeGroups);
			codeGroupDAL.removeMemberFromGroup(profilesCodeGroups);
		}else{
			throw new RecordNotFoundException("Aradığınız kriterlerde bir kayıt bulunamadı!");
		}
	}

	@Override
	/*OK*/
	public void leaveGroup(GroupOperationsModel groupOperationsModel) {
		Profile leaverProfile = this.getActiveProfile();
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		Optional<ProfilesCodeGroups> first = codeGroup.getGroupMembers()
				.stream()
				.filter(gm ->
						gm.getProfile().getId() == leaverProfile.getId() &&
						gm.getCodeGroup().getId() == codeGroup.getId() &&
						gm.getMemberType() == ProfilesCodeGroups.MEMBER_USERS)
				.findFirst();
		if(first.isPresent()){
			ProfilesCodeGroups profilesCodeGroups = first.get();
			codeGroup.getGroupMembers().remove(profilesCodeGroups);
			codeGroupDAL.removeMemberFromGroup(profilesCodeGroups);
		}else{
			throw new RecordNotFoundException("Aradığınız kriterlerde bir kayıt bulunamadı!");
		}
		
	}

	@Override
	/*OK*/
	public List<Profile> getGroupMembers(GroupOperationsModel groupOperationsModel) {
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		return codeGroup.getGroupMembers()
				.stream()
				.filter(m -> m.getMemberType() == ProfilesCodeGroups.MEMBER_USERS || m.getMemberType() == ProfilesCodeGroups.OWNER_USER)
				.map(p -> p.getProfile()).collect(Collectors.toList());
	}

	@Override
	/*OK*/
	public List<Profile> getGroupJoinRequests(GroupOperationsModel groupOperationsModel) {
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		return codeGroup.getGroupMembers()
				.stream()
				.filter(m -> m.getMemberType() == ProfilesCodeGroups.PARTICIPATING_USERS)
				.map(p -> p.getProfile()).collect(Collectors.toList());
	}

	@Override
	/*OK*/
	public List<CodeGroup> getGroupInvites(String username) {
		Profile profile = this.getActiveProfile();
		List<CodeGroup> codeGroups = profile
				.getGroups()
				.stream()
				.filter(gp-> gp.getMemberType() == ProfilesCodeGroups.INVITED_USERS)
				.map(gp -> gp.getCodeGroup())
				.collect(Collectors.toList());
		return codeGroups;
	}

	@Override
	public List<CodeGroup> getUserGroups() {
		Profile profile = this.getActiveProfile();
		return profile.getGroups().stream().map(ProfilesCodeGroups::getCodeGroup).collect(Collectors.toList());
	}

	@Override
	/*OK*/
	public void addAnnouncement(AnnouncementModel announcementModel) {
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(announcementModel.getGroupName());
		Profile profile = this.getActiveProfile();
		GroupAnnouncement groupAnnouncement = new GroupAnnouncement();
		groupAnnouncement.setAnnouncement(announcementModel.getAnnouncement());
		groupAnnouncement.setCodeGroup(codeGroup);
		groupAnnouncement.setAnnouncer(profile);
		codeGroup.getAnnouncements().add(groupAnnouncement);
		codeGroupDAL.updateGroup(codeGroup);
	}

	@Override
	/*OK*/
	public void addCommentToAnnouncement(AnnouncementCommentModel announcementCommentModel) {
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(announcementCommentModel.getGroupName());
		Profile profile = this.getActiveProfile();
		Optional<GroupAnnouncement> first = codeGroup.getAnnouncements()
						.stream()
						.filter(ga -> announcementCommentModel.getGroupAnnouncementId() == ga.getId())
						.findFirst();
		if(first.isPresent()){
			GroupAnnouncement groupAnnouncement = first.get();
			AnnouncementComment announcementComment = new AnnouncementComment();
			announcementComment.setComment(announcementCommentModel.getComment());
			announcementComment.setCommentOwner(profile);
			announcementComment.setGroupAnnouncement(groupAnnouncement);
			if(groupAnnouncement.getComments() == null){
				groupAnnouncement.setComments(new ArrayList<>());
			}
			groupAnnouncement.getComments().add(announcementComment);
			codeGroupDAL.updateGroup(codeGroup);
		}else{
			throw new RecordNotFoundException("Aradığınız kriterlerde bir kayıt bulunamadı!");
		}
	}

	@Override
	public CodeGroupResponseModel getGroupByGroupName(GroupOperationsModel groupOperationsModel) {
		CodeGroupResponseModel codeGroupResponseModel = new CodeGroupResponseModel();
		Profile profile = this.getActiveProfile();
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		codeGroupResponseModel.setGroupName(codeGroup.getName());
		codeGroupResponseModel.setGroupOwner(codeGroup.getGroupOwner());
		List<GroupAnnouncement> groupAnnouncements = codeGroup.getAnnouncements();
		Collections.reverse(groupAnnouncements);
		codeGroupResponseModel.setGroupAnnouncements(groupAnnouncements);
		codeGroupResponseModel.setMyRole(-1);
		List<ProfilesCodeGroups> profilesCodeGroupsList = codeGroup.getGroupMembers();
		ArrayList<Profile> groupMembers = new ArrayList<>();
		ArrayList<Profile> groupJoinRequests = new ArrayList<>();
		for(ProfilesCodeGroups profilesCodeGroups : profilesCodeGroupsList){
			if(profilesCodeGroups.getMemberType() == ProfilesCodeGroups.MEMBER_USERS){
				groupMembers.add(profilesCodeGroups.getProfile());
			}else if(profilesCodeGroups.getMemberType() == ProfilesCodeGroups.PARTICIPATING_USERS ){
				groupJoinRequests.add(profilesCodeGroups.getProfile());
			}
			if(profile.getId() == profilesCodeGroups.getProfile().getId()){
				codeGroupResponseModel.setMyRole(profilesCodeGroups.getMemberType());
			}
		}
		codeGroupResponseModel.setGroupMembers(groupMembers);
		codeGroupResponseModel.setGroupJoinRequests(groupJoinRequests);

		List<Challange> challanges = challangeDAL.findByGroupName(groupOperationsModel.getGroupName());
		codeGroupResponseModel.setGroupChallanges(challanges);

		return codeGroupResponseModel;
	}

	@Override
	/*OK*/
	public List<GroupAnnouncement> getAnnouncements(GroupOperationsModel groupOperationsModel) {
		CodeGroup codeGroup = codeGroupDAL.getGroupByGroupName(groupOperationsModel.getGroupName());
		return codeGroup.getAnnouncements();
	}
}
