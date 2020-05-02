package cn.myfreecloud.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangyang
 * @date: 2020/4/29 22:41
 * @description: 统一返回响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    private Integer resultCode;

    private String resultMsg;

    private Object data;
}