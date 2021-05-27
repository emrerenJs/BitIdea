package com.emrerenjs.bitidea.Business.Concrete;

import com.emrerenjs.bitidea.Business.Abstract.MailService;
import com.emrerenjs.bitidea.Model.General.MailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailManager implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailModel mailModel){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("bitideaapp@gmail.com");
        simpleMailMessage.setTo(mailModel.getTo());
        simpleMailMessage.setSubject(mailModel.getTopic());
        simpleMailMessage.setText(mailModel.getBody());
        javaMailSender.send(simpleMailMessage);
    }

}
