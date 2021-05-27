package com.emrerenjs.bitidea.Model.Group;

import com.emrerenjs.bitidea.Model.HttpRequestModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AnnouncementModel implements HttpRequestModel {

    private String groupName;
    private String announcement;

    @Override
    public boolean isFieldsNull() {
        return this.groupName == null ||
                this.groupName.trim().equals("") ||
                this.announcement == null ||
                this.announcement.trim().equals("");
    }
}
