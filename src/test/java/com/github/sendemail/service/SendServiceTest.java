package com.github.sendemail.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendServiceTest {

    @Resource
    MailService mailService;

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
}