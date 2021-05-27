package com.emrerenjs.bitidea.Model.General;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailModel {
    private String to;
    private String body;
    private String topic;
}
