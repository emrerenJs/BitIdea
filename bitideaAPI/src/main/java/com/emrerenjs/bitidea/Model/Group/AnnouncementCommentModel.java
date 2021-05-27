package com.emrerenjs.bitidea.Model.Group;

import com.emrerenjs.bitidea.Entity.MySQL.GroupAnnouncement;
import com.emrerenjs.bitidea.Model.HttpRequestModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AnnouncementCommentModel implements HttpRequestModel {

    private int groupAnnouncementId;
    private String groupName;
    private String comment;

    @Override
    public boolean isFieldsNull() {
        return  this.groupName == null ||
                this.groupName.trim().equals("") ||
                this.comment == null ||
                this.comment.trim().equals("");
    }
}
