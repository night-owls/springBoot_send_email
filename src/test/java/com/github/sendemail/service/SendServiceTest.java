package com.github.sendemail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendServiceTest {

    @Resource
    MailService mailService;

    @Resource
    TemplateEngine templateEngine;

    @Test
    public void sayHello() {
        mailService.sayHello();
    }

    @Test
    public void sendTest(){
        mailService.sendSimpleMail("bossluzhaohui@163.com","这是第一封邮件",
                "搭建好了框架，这是封测试邮件");
    }

    @Test
    public void sendHtmlTest() throws MessagingException {
        String concent = "<html>\n"+
                "<body>\n"+
                "<h3> hello world , 这是一封html邮件！</h3>"+
                "</body>\n"+"</html>";
        mailService.sendHtmlMail("bossluzhaohui@163.com","这是第一封Html邮件",
                concent);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath = "/ForJavaDevelopment/tools/JetbrainsCrack-2.9-release-enc.jar";
        mailService.sendAttachmentsMail("bossluzhaohui@163.com","这是第一封带附件的邮件",
                "邮件内容",filePath);
    }

    @Test
    public void sendInlineResourceMail() throws MessagingException {
        String imgPath = "/ForJavaDevelopment/tools/fengjiang.PNG";
        String rscId = "neo001";

        String imgPath2 = "/ForJavaDevelopment/tools/fengjing2.PNG";
        String rscId2 = "neo002";

        String content =  "<html>\n"+
                "<body>\n"+
                "这是一个图片邮件：<img src=\'cid:"+rscId+"\'></img>"+
                "这是一个图片邮件：<img src=\'cid:"+rscId2+"\'></img>"+
                "</body>\n"+"</html>";

        ArrayList<String> imgPaths = new ArrayList<>();
        imgPaths.add(imgPath);
        imgPaths.add(imgPath2);

        ArrayList<String> rscIds = new ArrayList<>();
        rscIds.add(rscId);
        rscIds.add(rscId2);

        mailService.sendInlinResourceMail("bossluzhaohui@163.com","这是第一封带附件的邮件",
                content,imgPaths,rscIds);
    }

    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context content = new Context();
        content.setVariable("id","006");

        String emailContent = templateEngine.process("emailTemplate",content);

        mailService.sendHtmlMail("bossluzhaohui@163.com","这是一个模板邮件",emailContent);
    }
}