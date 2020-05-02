package cn.myfreecloud.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: zhangyang
 * @date: 2020/5/2 17:01
 * @description:
 */
@Controller
public class IndexController {
    private static final String INDEX = "index";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return INDEX;
    }
}
