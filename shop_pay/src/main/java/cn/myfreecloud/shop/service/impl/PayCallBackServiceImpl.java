package cn.myfreecloud.shop.service.impl;

import cn.myfreecloud.shop.BaseApiService;
import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.api.service.PayCallBackService;
import cn.myfreecloud.shop.config.AlipayConfig;
import cn.myfreecloud.shop.entity.PaymentInfo;
import cn.myfreecloud.shop.mapper.PaymentInfoMapper;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: zhangyang
 * @date: 2020/5/5 18:55
 * @description:
 */
@Slf4j
@RestController
public class PayCallBackServiceImpl extends BaseApiService implements PayCallBackService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    // 同步回调
    public BaseResponse synCallBack(@RequestParam Map<String, String> params) {

        // 获取支付宝GET过来反馈信息
        try {

            log.info("####支付宝同步回调synCallBack开始 #### params:",params);

            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
                    AlipayConfig.charset, AlipayConfig.sign_type);

            // ——请在这里编写您的程序（以下代码仅作参考）——
            if (!signVerified) {
                return setResultError("验签失败!");
            }

            // 商户订单号
            String out_trade_no = params.get("out_trade_no");
            // 支付宝交易号
            String trade_no = params.get("trade_no");
            // 付款金额
            String total_amount = params.get("total_amount");

            // 返回参数
            JSONObject data = new JSONObject();
            data.put("out_trade_no", out_trade_no);
            data.put("trade_no", trade_no);
            data.put("total_amount", total_amount);
            return setResultSuccess(data);

        } catch (Exception e) {
            log.info("######PayCallBackServiceImpl##ERROR:#####", e);
            return setResultError("系统错误!");
        }finally{
            log.info("支付宝同步回调synCallBack结束####params:",params);
        }

    }

    // 异步回调
    public String asynCallBack(@RequestParam Map<String, String> params) {
        // 获取支付宝GET过来反馈信息
        try {
            log.info("####异步回调开始####params:",params);

            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
                    AlipayConfig.charset, AlipayConfig.sign_type);
            // ——请在这里编写您的程序（以下代码仅作参考）——
            if (!signVerified) {
                return "fail";
            }
            // 商户订单号
            String outTradeNo = params.get("out_trade_no");
            PaymentInfo paymentInfo = paymentInfoMapper.getByOrderIdPayInfo(outTradeNo);
            if(paymentInfo==null){
                return "fail";
            }
            Integer state = paymentInfo.getState();
            if (state.equals("1")) {
                return "success";
            }
            // 支付宝交易号
            String trade_no = params.get("trade_no");
            // 交易状态
            String trade_status = params.get("trade_status");
            if (trade_status.equals("TRADE_SUCCESS")) {
                paymentInfo.setPayMessage(params.toString());
                paymentInfo.setPlatformorderId(trade_no);
                paymentInfo.setState(1);
                paymentInfoMapper.updatePayInfo(paymentInfo);
            }
            return "success";
        } catch (Exception e) {
            log.info("######PayCallBackServiceImpl##ERROR:#####", e);
            return "fail";
        }finally{
            log.info("####异步回调结束####params:",params);
        }

    }
}
