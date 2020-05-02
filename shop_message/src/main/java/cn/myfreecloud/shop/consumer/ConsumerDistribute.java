package cn.myfreecloud.shop.consumer;

import cn.myfreecloud.shop.Constants;
import cn.myfreecloud.shop.adapter.MessageAdapter;
import cn.myfreecloud.shop.service.MailService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class ConsumerDistribute {

    @Autowired
    private MailService mailService;

    private MessageAdapter messageAdapter;

    // 监听那个队列
    @JmsListener(destination = "messages_queue")
    public void distribute(String json) {
        log.info("####ConsumerDistribute###distribute() 消息服务平台接受 json参数:" + json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        JSONObject jsonObecjt = new JSONObject().parseObject(json);
        // 消息头,标明消息类型
        JSONObject header = jsonObecjt.getJSONObject("header");
        // 接口类型(短信,邮件,微信推送)
        String interfaceType = header.getString("interfaceType");

        if (StringUtils.isEmpty(interfaceType)) {
            return;
        }

        // 接口类型是 邮件
        if (interfaceType.equals(Constants.SMS_MAIL)) {
            messageAdapter = mailService;
        }
        // 没找到接口
        if (messageAdapter == null) {
            return;
        }
        JSONObject body = jsonObecjt.getJSONObject("content");
        messageAdapter.sendMsg(body);

    }

}
