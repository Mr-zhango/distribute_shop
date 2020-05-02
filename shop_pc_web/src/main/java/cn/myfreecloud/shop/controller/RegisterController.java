package cn.myfreecloud.shop.controller;

import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.Constants;
import cn.myfreecloud.shop.entity.UserEntity;
import cn.myfreecloud.shop.fegin.MemberServiceFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhangyang
 * @date: 2020/5/2 17:12
 * @description:
 */
@Controller
public class RegisterController {

    private static final String REGISTER = "register";

    private static final String LOGIN = "login";

    @Autowired
    private MemberServiceFegin memberServiceFegin;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String registerGetToPage(){
        return REGISTER;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String registerPost(UserEntity userEntity, HttpServletRequest request){

        // 参数校验
        BaseResponse baseResponse = memberServiceFegin.registerMember(userEntity);

        if(!baseResponse.getResultCode().equals(Constants.HTTP_RES_CODE_200)){
            request.setAttribute("error","注册失败");
            return REGISTER;
        }

        return LOGIN;
    }
}
