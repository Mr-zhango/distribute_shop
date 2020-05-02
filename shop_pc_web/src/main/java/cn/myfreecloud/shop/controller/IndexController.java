package cn.myfreecloud.shop.controller;

import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.Constants;
import cn.myfreecloud.shop.CookieUtil;
import cn.myfreecloud.shop.fegin.MemberServiceFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * @author: zhangyang
 * @date: 2020/5/2 17:01
 * @description:
 */
@Controller
public class IndexController {
    private static final String INDEX = "index";

    @Autowired
    private MemberServiceFegin memberServiceFegin;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {

        // 网站首页
        // 从cookie中获取token,根据token获取用户信息

        String token = CookieUtil.getUid(request, Constants.COOKIE_MEMBER_TOKEN);

        if(!StringUtils.isEmpty(token)){
            BaseResponse baseResponse = memberServiceFegin.getUserByToken(token);

            if(baseResponse.getResultCode().equals(Constants.HTTP_RES_CODE_200)){

                LinkedHashMap linkedHashMap = (LinkedHashMap)baseResponse.getData();
                String username = (String)linkedHashMap.get("username");
                request.setAttribute("username",username);
            }
        }


        return INDEX;
    }
}
