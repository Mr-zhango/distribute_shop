package cn.myfreecloud.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: zhangyang
 * @date: 2020/4/29 22:43
 * @description:
 */
@Component
public class BaseApiService {

    @Autowired
    protected BaseRedisService baseRedisService;

    // 方法的重载
    public BaseResponse setResultSuccess(Object data) {
        return new BaseResponse(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_MSG, data);
    }

    // 方法的重载
    public BaseResponse setResultSuccess() {
        return new BaseResponse(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_MSG, null);
    }


    public BaseResponse setResultError(String msg) {
        return new BaseResponse(Constants.HTTP_RES_CODE_500, msg, null);
    }


    // 通用封装
    public BaseResponse setResult(Integer resultCode, String resultMsg, Object data) {
        return new BaseResponse(resultCode, resultMsg, data);
    }

}
