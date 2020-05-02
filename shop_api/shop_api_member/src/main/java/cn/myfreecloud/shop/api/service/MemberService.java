package cn.myfreecloud.shop.api.service;

import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.entity.UserEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zhangyang
 * @date: 2020/4/30 23:22
 * @description:
 */
@RequestMapping("/member")
public interface MemberService {

    // 查询用户 by id
    @RequestMapping(value = "/findUserById" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    BaseResponse findUserById(Long userId);

    // 注册会员
    @RequestMapping(value = "/registerMember" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    BaseResponse registerMember(@RequestBody UserEntity userEntity);

    // 用户登录
    @RequestMapping(value = "/login" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    BaseResponse login(@RequestBody UserEntity userEntity);


    // 查询用户信息 by token
    @RequestMapping(value = "/getUserByToken" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    BaseResponse getUserByToken(String token);
}
