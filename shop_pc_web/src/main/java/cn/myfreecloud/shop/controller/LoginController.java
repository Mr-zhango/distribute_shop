package cn.myfreecloud.shop.controller;

import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.Constants;
import cn.myfreecloud.shop.CookieUtil;
import cn.myfreecloud.shop.entity.UserEntity;
import cn.myfreecloud.shop.fegin.MemberServiceFegin;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    private static final String ERROR = "error";

    private static final String RELATION = "qqrelation";


    @Autowired
    private MemberServiceFegin memberServiceFegin;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGetToPage() {
        return LOGIN;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        // 参数校验
        BaseResponse baseResponse = memberServiceFegin.login(userEntity);

        if (!baseResponse.getResultCode().equals(Constants.HTTP_RES_CODE_200)) {
            request.setAttribute("error", baseResponse.getResultMsg());
            return LOGIN;
        }

        LinkedHashMap linkedHashMap = (LinkedHashMap) baseResponse.getData();

        String memberToken = (String) linkedHashMap.get(Constants.COOKIE_MEMBER_TOKEN);

        if (StringUtils.isEmpty(memberToken)) {
            request.setAttribute("error", "回话失效");
        }

        // 90天的cookie有效期
        CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken, 60 * 60 * 24 * 90);
        return INDEX;
    }


    /**
     * 跳转到QQ授权地址
     *
     * @param request
     * @return
     * @throws QQConnectException
     */
    @RequestMapping("/locaQQLogin")
    public String locaQQLogin(HttpServletRequest request) throws QQConnectException {
        // 生成授权连接
        String authorizeURL = new Oauth().getAuthorizeURL(request);
        return "redirect:" + authorizeURL;
    }

    // qq 登录回调函数
    @RequestMapping("/qqLoginCallback")
    public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
            throws QQConnectException {
        // 1.获取qq授权码
        AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
        if (accessTokenObj == null) {
            request.setAttribute("error", "qq授权失败!");
            return ERROR;
        }
        // 2.使用授权码code获取accessToken
        String accessToken = accessTokenObj.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            request.setAttribute("error", "使用授权码code获取accessToke失败!");
            return ERROR;
        }
        // 3.使用 accessToken 获取 openid
        OpenID openIdObj = new OpenID(accessToken);
        String userOpenID = openIdObj.getUserOpenID();

        BaseResponse openIdUser = memberServiceFegin.getUserByOpenid(userOpenID);
        // 用戶沒有关联QQ账号
        if (openIdUser.getResultCode().equals(Constants.HTTP_RES_CODE_201)) {
            // 跳转到管理账号
            httpSession.setAttribute("qqOpenid", userOpenID);
            return RELATION;
        }
        // 如果用户关联账号 直接登录
        LinkedHashMap dataMap = (LinkedHashMap) openIdUser.getData();
        String memberToken = (String)dataMap.get(Constants.COOKIE_MEMBER_TOKEN);
        setCookie(memberToken, response);
        return INDEX;

    }


    // 登录请求具体提交实现
    @RequestMapping(value = "/qqRelation", method = RequestMethod.POST)
    public String qqRelation(UserEntity userEntity, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        // 1.获取openid
        String qqOpenid=(String) httpSession.getAttribute("qqOpenid");

        if(StringUtils.isEmpty(qqOpenid)){
            request.setAttribute("error", "没有获取到openid");
            return "error";
        }

        // 2.调用登录接口，获取token信息
        userEntity.setOpenid(qqOpenid);

        BaseResponse loginBase = memberServiceFegin.qqLogin(userEntity);
        if (!loginBase.getResultCode().equals(Constants.HTTP_RES_CODE_200)) {
            request.setAttribute("error", "账号或者密码错误!");
            return LOGIN;
        }

        LinkedHashMap loginData = (LinkedHashMap) loginBase.getData();

        String memberToken = (String) loginData.get(Constants.COOKIE_MEMBER_TOKEN);
        if (StringUtils.isEmpty(memberToken)) {
            request.setAttribute("error", "会话已经失效!");
            return LOGIN;
        }
        // 3.将token信息存放在cookie里面
        setCookie(memberToken, response);
        return INDEX;
    }


    public void setCookie(String memberToken,HttpServletResponse response){
        CookieUtil.addCookie(response,Constants.COOKIE_MEMBER_TOKEN,memberToken,60 * 60 * 24 * 90);
    }

}
