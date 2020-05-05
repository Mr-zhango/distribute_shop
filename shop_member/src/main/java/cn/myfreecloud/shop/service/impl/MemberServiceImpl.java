package cn.myfreecloud.shop.service.impl;

import cn.myfreecloud.shop.*;
import cn.myfreecloud.shop.api.service.MemberService;
import cn.myfreecloud.shop.entity.UserEntity;
import cn.myfreecloud.shop.mapper.MemberMapper;
import cn.myfreecloud.shop.mq.RegisterMailboxProducer;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author: zhangyang
 * @date: 2020/4/30 23:23
 * @description:
 */
@Slf4j
@RequestMapping("/member")
@RestController
public class MemberServiceImpl extends BaseApiService implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Value("${messages.queue}")
    private String MESSAGESQUEUE;

    @Override
    public BaseResponse findUserById(@RequestParam("userId") Long userId) {
        UserEntity userEntity = memberMapper.findByID(userId);
        if (userEntity == null) {
            return setResultError("未查到用户");
        }

        return setResultSuccess(userEntity);
    }


    @Override
    public BaseResponse registerMember(@RequestBody UserEntity user) {
        String passWord = user.getPassword();
        String newPassWord = MD5Util.MD5(passWord);
        user.setPassword(newPassWord);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        Integer insertUser = memberMapper.insertUser(user);
        if (insertUser <= 0) {
            return setResultError("注册失败!");
        }
        // 采用MQ异步发送邮件
        String email = user.getEmail();
        String messAageJson = message(email);
        log.info("####会员服务推送消息到消息服务平台:{},messAageJson:{}", email, messAageJson);
        //sendMsg(messAageJson);
        return setResultSuccess();
    }

    @Override
    public BaseResponse login(@RequestBody UserEntity user) {
        // 参数校验
        // 校验账户密码是否正确
        // 如果正确生成令牌
        // 放在 redis 中 key为token value为userId
        // 返回 token

        String username = user.getUsername();
        if (StringUtils.isEmpty(username)) {
            return setResultError("用户名不能为空");
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            return setResultError("密码不能为空");
        }
        String password = MD5Util.MD5(user.getPassword());
        user.setPassword(password);


        UserEntity userEntity = memberMapper.login(username, password);

        return setLogin(userEntity);
    }

    private BaseResponse setLogin(UserEntity userEntity) {
        if (userEntity == null) {
            return setResultError("账号或者密码错误,登录失败");
        }

        // 生成token
        String memberToken = TokenUtils.getMemberToken();

        log.info("用户token存放在redis中了,key为:{},value:{}", memberToken, userEntity.getId());

        baseRedisService.setString(memberToken, userEntity.getId().toString(), Constants.TOKEN_MEMBER_TIME);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.COOKIE_MEMBER_TOKEN, memberToken);
        return setResultSuccess(jsonObject);
    }

    @Override
    public BaseResponse getUserByToken(@RequestParam("token") String token) {
        if (token == null) {
            return setResultError("token不能为空");
        }
        String string = baseRedisService.getString(token);

        if (StringUtils.isEmpty(string)) {
            return setResultError("token无效或者已经过期");
        }
        long userId = Long.parseLong(string);

        UserEntity userEntity = memberMapper.findByID(userId);
        if (StringUtils.isEmpty(userEntity)) {
            return setResultError("未查到该用户信息:{}" + userId);
        }
        return setResultSuccess(userEntity);
    }

    @Override
    public BaseResponse getUserByOpenid(@RequestParam("openid") String openid) {

        // 验证参数
        UserEntity userEntity = memberMapper.getUserByOpenid(openid);

        if (StringUtils.isEmpty(userEntity)) {
            return setResultError(Constants.HTTP_RES_CODE_201, "该用openid还未关联用户");
        }

        // 已经 经过授权, 自动登录
        return setLogin(userEntity);
    }

    @Override
    public BaseResponse qqLogin(@RequestBody UserEntity userEntity) {
        String openid = userEntity.getOpenid();
        if (StringUtils.isEmpty(openid)) {
            return setResultError("openid不能为空");
        }


        // 先进行登录
        BaseResponse baseResponse = login(userEntity);


        // 错误处理
        if (!Constants.HTTP_RES_CODE_200.equals(baseResponse.getResultCode())) {
            return setLogin(userEntity);
        }

        JSONObject jsonObject = (JSONObject) baseResponse.getData();
        String memberToken = jsonObject.getString(Constants.COOKIE_MEMBER_TOKEN);
        BaseResponse userByToken = getUserByToken(memberToken);



        // 错误处理
        if (!Constants.HTTP_RES_CODE_200.equals(userByToken.getResultCode())) {
            return setResultError("QQ账号关联失败");
        }

        UserEntity userByTokenData = (UserEntity) userByToken.getData();

        Integer userId = userByTokenData.getId();
        // 登录成功
        Integer integer = memberMapper.updateUserByOpenid(openid, userId);

        if (integer <= 0) {
            return setResultError("QQ账号关联失败");
        }
        return setLogin(userEntity);
    }

    private String message(String mail) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", Constants.SMS_MAIL);

        JSONObject content = new JSONObject();
        content.put("mail", mail);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }

    // 发送mq消息
    private void sendMsg(String json) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
        registerMailboxProducer.sendMsg(activeMQQueue, json);
    }

}
