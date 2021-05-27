package com.emrerenjs.bitidea.Rest;

import java.util.List;

import com.emrerenjs.bitidea.Entity.MySQL.CodeGroup;
import com.emrerenjs.bitidea.Entity.MySQL.GroupAnnouncement;
import com.emrerenjs.bitidea.Model.Group.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emrerenjs.bitidea.Business.Abstract.GroupService;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Model.General.GeneralResponse;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	/*GROUP OWNER OPERATIONS*/
	@PostMapping("/createGroup")
	public ResponseEntity<?> createGroup(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		groupService.createGroup(groupOperationsModel);
        return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Grup başarıyla oluşturuldu.."));
	}
	
	@PostMapping("/removeGroup")
	public ResponseEntity<?> removeGroup(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		groupService.removeGroup(groupOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Grup başarıyla silindi.."));
	}
	
	@PostMapping("/inviteUser")
	public ResponseEntity<?> inviteUser(@RequestBody UserOperationsModel userOperationsModel){
		if(userOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Kullanıcı ve grup bilgileri boş gönderilemez!");
		}
		groupService.inviteUser(userOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Kullanıcı başarıyla davet edildi.."));
	}
	
	@PostMapping("/acceptUser")
	public ResponseEntity<?> acceptUser(@RequestBody UserOperationsModel userOperationsModel){
		if(userOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Kullanıcı ve grup bilgileri boş gönderilemez!");
		}
		groupService.acceptUser(userOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Kullanıcı başarıyla gruba alındı.."));
	}

	@PostMapping("/denyUser")
	public ResponseEntity<?> denyUser(@RequestBody UserOperationsModel userOperationsModel){
		if(userOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Kullanıcı ve grup bilgileri boş gönderilemez!");
		}
		groupService.denyUser(userOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Kullanıcı isteği başarıyla reddedildi.."));
	}

	@PostMapping("/kickUser")
	public ResponseEntity<?> kickUser(@RequestBody UserOperationsModel userOperationsModel){
		if(userOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Kullanıcı ve grup bilgileri boş gönderilemez!");
		}
		groupService.kickUser(userOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Kullanıcı başarıyla gruptan atıldı!"));
	}
	
	/*GROUP OWNER OPERATIONS*/
	
	/*USER OPERATIONS*/

	@PostMapping("/joinGroup")
	public ResponseEntity<?> joinGroup(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		groupService.joinGroup(groupOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Gruba katılım isteği başarıyla gönderildi.."));
	}
	
	@PostMapping("/acceptInvite")
	public ResponseEntity<?> acceptInvite(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		groupService.acceptInvite(groupOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Gruba başarıyla katınıldı.."));

	}

	@PostMapping("/denyInvite")
	public ResponseEntity<?> denyInvite(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		groupService.denyInvite(groupOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Grup daveti başarıyla reddedildi.."));

	}
	
	@PostMapping("/leaveGroup")
	public ResponseEntity<?> leaveGroup(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()) {
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		groupService.leaveGroup(groupOperationsModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Gruptan başarıyla çıkıldı"));

	}
	
	/*USER OPERATIONS*/
	
	/*MODEL RETURNS*/
	@PostMapping("/getGroupByGroupName")
	public CodeGroupResponseModel getGroupByGroupName(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()){
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		return groupService.getGroupByGroupName(groupOperationsModel);
	}

	@PostMapping("/getGroupMembers")
	public List<Profile> getGroupMembers(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()){
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		return groupService.getGroupMembers(groupOperationsModel);
	}

	@PostMapping("/getGroupJoinRequests")
	public List<Profile> getGroupJoinRequests(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()){
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		return groupService.getGroupJoinRequests(groupOperationsModel);
	}

	@PostMapping("/getGroupInvites")
	public List<CodeGroup> getGroupInvites(@RequestBody UserOperationsModel userOperationsModel){
		if(userOperationsModel == null  || userOperationsModel.getUsername().trim().equals("")){
			throw new IllegalArgumentException("Kullanıcı bilgileri boş gönderilemez!");
		}
		return groupService.getGroupInvites(userOperationsModel.getUsername());
	}

	@PostMapping("/getUserGroups")
	public List<CodeGroup> getUserGroups(){
		return groupService.getUserGroups();
	}

	/*MODEL RETURNS*/

	/*ANNOUNCEMENTS*/
	@PostMapping("/addAnnouncement")
	public ResponseEntity<?> addAnnouncement(@RequestBody AnnouncementModel announcementModel){
		if(announcementModel.isFieldsNull()){
			throw new IllegalArgumentException("Anons bilgileri boş geçilemez!");
		}
		groupService.addAnnouncement(announcementModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Anons başarıyla yapıldı"));
	}

	@PostMapping("/addCommentToAnnouncement")
	public ResponseEntity<?> addCommentToAnnouncement(@RequestBody AnnouncementCommentModel announcementCommentModel){
		if(announcementCommentModel.isFieldsNull()){
			throw new IllegalArgumentException("Anons bilgileri boş geçilemez!");
		}
		groupService.addCommentToAnnouncement(announcementCommentModel);
		return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","Yorum başarıyla yapıldı"));
	}

	@PostMapping("/getAnnouncements")
	public List<GroupAnnouncement> getAnnouncements(@RequestBody GroupOperationsModel groupOperationsModel){
		if(groupOperationsModel.isFieldsNull()){
			throw new IllegalArgumentException("Grup bilgileri boş gönderilemez!");
		}
		return groupService.getAnnouncements(groupOperationsModel);

	}

	/*ANNOUNCEMENTS*/

}
