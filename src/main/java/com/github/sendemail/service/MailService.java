package com.github.sendemail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;

/**
 *
 */
@Service
public class MailService {
    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void sayHello(){
        System.out.println("Hello World");
    }

    public void sendSimpleMail(String to,String subject,String concent){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(concent);
        message.setFrom(from);

        mailSender.send(message);
    }

    public void sendHtmlMail(String to ,String subject,String concent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(concent,true);//这边不设置为true不会以html的形式显示

        mailSender.send(message);
    }

    /**
     * 带附件的邮件
     * @param to
     * @param subject
     * @param concent
     * @param filePath 附件名称，可扩展为数组
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to, String subject,String concent,String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(concent,true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName,file);
            helper.addAttachment(fileName+"test",file);
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件失败：",e);
        }

    }

    /**
     * 带图片的邮件发送
     * @param to
     * @param subject
     * @param concent
     * @param imgPath 可为list
     * @param rscId 可为list
     * @throws MessagingException
     */
    public void sendInlinResourceMail(String to, String subject, String concent, ArrayList<String> imgPaths,
                                      ArrayList<String> rscIds) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(concent,true);

        for(int i =0 ;i < imgPaths.size() ; i++){
            FileSystemResource res = new FileSystemResource(new File(imgPaths.get(i)));
            helper.addInline(rscIds.get(i),res);
        }


        mailSender.send(message);

    }

}
