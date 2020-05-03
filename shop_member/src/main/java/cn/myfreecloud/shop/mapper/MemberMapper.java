package cn.myfreecloud.shop.mapper;

import cn.myfreecloud.shop.entity.UserEntity;
import org.apache.ibatis.annotations.*;

/**
 * @author: zhangyang
 * @date: 2020/4/30 23:26
 * @description:
 */
@Mapper
public interface MemberMapper {

    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where id = #{userId}")
    UserEntity findByID(@Param("userId") Long userId);

    @Insert("INSERT  INTO `mb_user`  (username,password,phone,email,created,updated) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated});")
    Integer insertUser(UserEntity userEntity);

    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where username = #{username} and password = #{password}")
    UserEntity login(@Param("username") String username,@Param("password") String password);

    @Select("select  id,username,password,phone,email,created,updated,openid from mb_user where openid = #{openid}")
    UserEntity getUserByOpenid(@Param("openid") String openid);

    @Update("update mb_user set openid = #{openid}  where id = #{userId} ")
    Integer updateUserByOpenid(@Param("openid") String openid,@Param("userId") Integer userId);
}
