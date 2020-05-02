package cn.myfreecloud.shop.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 统一发送消息的接口
 * 发送邮件
 * 发送短信
 * 推送微信
 *
 */
public interface MessageAdapter {

    public void sendMsg(JSONObject body);
}
