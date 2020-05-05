package cn.myfreecloud.shop.controller;

import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.Constants;
import cn.myfreecloud.shop.fegin.PayServiceFegin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

/**
 * @author: zhangyang
 * @date: 2020/5/5 16:04
 * @description:
 */

@Slf4j
@Controller
public class PayController {

    @Autowired
    private PayServiceFegin payServiceFegin;

    // 使用token 进行支付
    @RequestMapping("/aliPay")
    public void aliPay(String payToken, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        // 写出到客户端的对象
        PrintWriter writer = response.getWriter();
        // 1.参数验证
        if (StringUtils.isEmpty(payToken)) {
            return;
        }
        // 2.调用支付服务接口 获取支付宝html元素
        BaseResponse payTokenResult = payServiceFegin.findPayToken(payToken);
        if (!payTokenResult.getResultCode().equals(Constants.HTTP_RES_CODE_200)) {
            String msg = payTokenResult.getResultMsg();
            writer.println(msg);
            return;
        }
        // 3.返回可以执行的html元素给客户端
        LinkedHashMap data = (LinkedHashMap) payTokenResult.getData();
        String payHtml = (String) data.get("payHtml");
        log.info("####PayController###payHtml:{}", payHtml);
        //4. 页面上进行渲染
        writer.println(payHtml);
        writer.close();
    }
}
