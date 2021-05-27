package com.emrerenjs.bitidea.Business.Abstract;

import com.emrerenjs.bitidea.Model.General.MailModel;

public interface MailService {
    void sendMail(MailModel mailModel);
}
