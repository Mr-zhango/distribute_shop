package cn.myfreecloud.shop.service;

import cn.myfreecloud.shop.adapter.MessageAdapter;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class MailService implements MessageAdapter {

    @Value("${msg.subject}")
    private String subject;

    @Value("${msg.text}")
    private String text;

    @Autowired
    private JavaMailSender mailSender; // 自动注入的Bean

    @Value("${spring.mail.username}")
    private String fromUserMainAddress;

    @Override
    public void sendMsg(JSONObject body) {
        String mail = body.getString("mail");
        if (StringUtils.isEmpty(mail)) {
            return;
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 谁发送的
        simpleMailMessage.setFrom(fromUserMainAddress);
        // 发给谁
        simpleMailMessage.setTo(mail);
        // 标题
        simpleMailMessage.setSubject(subject);
        // 内容
        simpleMailMessage.setText(text.replace("{}", mail));
        // mailSender.send(simpleMailMessage);
        log.info("给:{}发送邮件成功",mail);
    }

}
