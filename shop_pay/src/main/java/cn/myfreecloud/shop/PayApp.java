package cn.myfreecloud.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: zhangyang
 * @date: 2020/5/5 15:13
 * @description:
 */
@EnableEurekaClient
@SpringBootApplication
public class PayApp {
    public static void main(String[] args) {
        SpringApplication.run(PayApp.class, args);
    }
}
