package cn.myfreecloud.shop.controller;

import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.Constants;
import cn.myfreecloud.shop.CookieUtil;
import cn.myfreecloud.shop.entity.UserEntity;
import cn.myfreecloud.shop.fegin.MemberServiceFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

/**
 * @author: zhangyang
 * @date: 2020/5/2 18:16
 * @description:
 */
@Controller
public class LoginController {

    private static final String LOGIN = "login";

    private static final String INDEX = "redirect:/";

    @Autowired
    private MemberServiceFegin memberServiceFegin;


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginGetToPage(){
        return LOGIN;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response){
        // 参数校验
        BaseResponse baseResponse = memberServiceFegin.login(userEntity);

        if(!baseResponse.getResultCode().equals(Constants.HTTP_RES_CODE_200)){
            request.setAttribute("error",baseResponse.getResultMsg());
            return LOGIN;
        }

        LinkedHashMap linkedHashMap = (LinkedHashMap)baseResponse.getData();

        String memberToken = (String) linkedHashMap.get("memberToken");

        if(StringUtils.isEmpty(memberToken)){
            request.setAttribute("error","回话失效");
        }

        // 90天的cookie有效期
        CookieUtil.addCookie(response,Constants.COOKIE_MEMBER_TOKEN,memberToken,60 * 60 * 24 * 90);
        return INDEX;
    }


}
