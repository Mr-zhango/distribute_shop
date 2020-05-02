package cn.myfreecloud.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author: zhangyang
 * @date: 2020/5/2 12:11
 * @description:
 */

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class MessageServer {
    public static void main(String[] args) {
        SpringApplication.run(MessageServer.class, args);
    }
}
