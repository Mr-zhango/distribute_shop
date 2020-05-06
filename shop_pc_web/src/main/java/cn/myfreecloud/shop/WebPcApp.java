package cn.myfreecloud.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author: zhangyang
 * @date: 2020/5/2 17:00
 * @description:
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class WebPcApp {
    public static void main(String[] args) {
        SpringApplication.run(WebPcApp.class, args);
    }
}
