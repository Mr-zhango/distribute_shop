package cn.myfreecloud.shop.service.impl;


import cn.myfreecloud.shop.BaseResponse;
import cn.myfreecloud.shop.Constants;
import cn.myfreecloud.shop.api.service.PayOrderService;
import cn.myfreecloud.shop.entity.PaymentInfo;
import cn.myfreecloud.shop.fegin.OrderServiceFegin;
import cn.myfreecloud.shop.mapper.PaymentInfoMapper;
import com.codingapi.tx.annotation.TxTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyang
 * @date: 2020/5/7 9:08
 * @description:
 */


@RestController
public class PayOrderServiceImpl implements PayOrderService {
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;
    @Autowired
    private OrderServiceFegin orderServiceFegin;

    @Override
    @SuppressWarnings("unused")
    @TxTransaction(isStart = true) // 标识为事务的发起方
    @Transactional
    public String payOrder(@RequestParam("orderId") String orderId, @RequestParam("temp") int temp) {
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderIdPayInfo(orderId);
        if (paymentInfo == null) {
            return Constants.PAY_FAIL;
        }

        // 支付宝重试机制
        Integer state = paymentInfo.getState();
        if (state == 1) {
            return Constants.PAY_SUCCESS;
        }

        // 支付宝交易号
        String tradeNo = "644064779";
        paymentInfo.setState(1);// 标识为已经支付
        paymentInfo.setPayMessage("test");
        paymentInfo.setPlatformorderId(tradeNo);
        // 手动 begin begin
        Integer updateResult = paymentInfoMapper.updatePayInfo(paymentInfo);
        if (updateResult <= 0) {
            return Constants.PAY_FAIL;
        }

        // 调用订单接口通知 支付状态
        BaseResponse orderResult = orderServiceFegin.updateOrderIdInfo(1l, tradeNo, orderId);
        if (!orderResult.getResultCode().equals(Constants.HTTP_RES_CODE_200)) {
            // 回滚 手动回滚 rollback

            return Constants.PAY_FAIL;
        } // 2PC 3PC TCC MQ
        int i = 1 / temp;

        return Constants.PAY_SUCCESS;
    }
}
