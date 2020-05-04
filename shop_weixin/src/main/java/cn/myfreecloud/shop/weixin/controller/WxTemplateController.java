package cn.myfreecloud.shop.weixin.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zhangyang
 * @date: 2020/5/4 15:17
 * @description:
 */
@RequestMapping("/wx/template/{appid}")
public class WxTemplateController {
    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/sendTemplate")
    public String createTemplate(@RequestBody WxMpTemplateMessage wxMpTemplateMessage) throws WxErrorException {
        WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();
        return templateMsgService.sendTemplateMsg(wxMpTemplateMessage);
    }
}
