package cn.myfreecloud.shop.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author: zhangyang
 * @date: 2020/5/5 22:46
 * @description:
 */
@Mapper
public interface OrderMapper {
    @Update("update order_info set isPay=#{isPay} ,payId=#{aliPayId} where orderNumber=#{orderNumber};")
    public int updateOrder(@Param("isPay") Long isPay, @Param("aliPayId") String aliPayId, @Param("orderNumber") String orderNumber);

}
