package cn.myfreecloud.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: zhangyang
 * @date: 2020/4/29 22:31
 * @description:
 */
@SpringBootApplication
@EnableEurekaClient
public class MemberServer {
    public static void main(String[] args) {
        SpringApplication.run(MemberServer.class, args);
    }

}
