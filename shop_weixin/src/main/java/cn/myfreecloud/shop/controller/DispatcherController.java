package cn.myfreecloud.shop.controller;

import cn.myfreecloud.shop.HttpClientUtil;
import cn.myfreecloud.shop.base.TextMessage;
import cn.myfreecloud.shop.weixin.CheckUtil;
import cn.myfreecloud.shop.weixin.XmlUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @author: zhangyang
 * @date: 2020/5/3 23:32
 * @description:
 */
@Slf4j
@RestController
public class DispatcherController {

    private static final String REQESTURL = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";

    @RequestMapping(value = "/dispatcher", method = RequestMethod.GET)
    public String dispatcherGet(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,@RequestParam("echostr") String echostr) {

        // 1.验证参数

        // 2.参数校验成功之后,返回随机数
        boolean b = CheckUtil.checkSignature(signature, timestamp, nonce);

        if (!b) {
            return null;
        }
        // 校验通过,返回随机字符串
        return echostr;
    }

    // 微信动作推送
    @RequestMapping(value = "/dispatcher", method = RequestMethod.POST)
    public void dispatCherGet(HttpServletRequest reqest, HttpServletResponse response) throws Exception {
        reqest.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 1.将xml转换成Map格式
        Map<String, String> resultMap = XmlUtils.parseXml(reqest);
        log.info("###收到微信消息####resultMap:" + resultMap.toString());

        // 2.判断消息类型
        String msgType = resultMap.get("MsgType");

        // 3.如果是文本类型，返回结果给微信服务端
        PrintWriter writer = response.getWriter();
        switch (msgType) {
            case "text":
                // 开发者微信公众号
                String toUserName = resultMap.get("ToUserName");
                // 消息来自公众号
                String fromUserName = resultMap.get("FromUserName");
                // 消息内容
                String content = resultMap.get("Content");

                String resultJson = HttpClientUtil.doGet(REQESTURL + content);

                JSONObject jsonObject = JSONObject.parseObject(resultJson);
                Integer resultCode = jsonObject.getInteger("result");
                String msg = null;
                if (resultCode == 0) {
                    String resultContent = jsonObject.getString("content");
                    msg = setText(resultContent, toUserName,fromUserName);
                }else {
                    msg = setText("我现在有点忙.稍后回复您!", toUserName,fromUserName);
                }
                writer.println(msg);
                break;

            default:
                break;
        }
        writer.close();
    }

    // 回复文本的方法
    public String setText(String content, String fromUserName, String toUserName) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);
        textMessage.setMsgType("text");
        textMessage.setCreateTime(new Date().getTime());
        // 来自哪里
        textMessage.setFromUserName(fromUserName);
        // 发给谁
        textMessage.setToUserName(toUserName);
        // 将实体类转换成xml格式
        String msg = XmlUtils.messageToXml(textMessage);
        return msg;
    }
}
