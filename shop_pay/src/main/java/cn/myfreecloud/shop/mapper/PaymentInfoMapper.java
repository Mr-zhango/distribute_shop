package cn.myfreecloud.shop.mapper;

import cn.myfreecloud.shop.entity.PaymentInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PaymentInfoMapper {

	@Select("select * from payment_info where  id=#{id}")
	PaymentInfo getPaymentInfo(@Param("id") Long id);

	@Insert("insert into payment_info ( id,userid,typeid,orderid,platformorderid,price,source,state,created,updated) value(null,#{userId},#{typeId},#{orderId},#{platformorderId},#{price},#{source},#{state},#{created},#{updated})")
	@Options(useGeneratedKeys = true, keyProperty = "id") // 添加该行，product中的id将被自动添加
	Integer savePaymentType(PaymentInfo paymentInfo);

	@Select("select * from payment_info where  orderId=#{orderId}")
	PaymentInfo getByOrderIdPayInfo(@Param("orderId") String orderId);

	@Update("update payment_info set state =#{state},payMessage=#{payMessage},platformorderId=#{platformorderId},updated=#{updated} where orderId=#{orderId} ")
	Integer updatePayInfo(PaymentInfo paymentInfo);
}