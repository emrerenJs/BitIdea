package com.emrerenjs.bitidea.Model.Group;

import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Entity.MySQL.GroupAnnouncement;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import lombok.Data;

import java.util.List;

@Data
public class CodeGroupResponseModel {

    private String groupName;

    private List<Profile> groupMembers;

    private List<Profile> groupJoinRequests;

    private List<Challange> groupChallanges;

    private List<GroupAnnouncement> groupAnnouncements;

    private Profile groupOwner;

    private int myRole;



}
