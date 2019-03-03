package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.HistoryOrder;
import com.sl.web.model.db.SlOrder;
import com.sl.web.mybatis.MyMapper;

public interface SlOrderMapper extends MyMapper<SlOrder> {
	
	@Select("<script> select a.od_id, a.shop_id, b.shop_nm, a.od_uid, a.od_barber_uid, u.u_nm as od_barber_unm, a.od_stm, a.pj_id, c.pj_nm, a.od_total_price, "
			+ " a.od_discount, a.od_offer_price, a.od_voucher_price, a.od_pay_price, a.od_paied, a.od_paied_ts, a.od_paied_tp, a.od_confirm "
			+ "from sl_order a inner join sl_shop b on a.shop_id = b.shop_id inner join sl_project c on a.pj_id = c.pj_id "
			+ "inner join sl_user u on a.od_barber_uid = u.u_id "
			+ "where b.shop_id = #{shopId} "
			+ "<when test='paied != null'> "
			+ " and a.od_paied = #{paied} "
			+ "</when>"
			+ "<when test='confirmed != null'> "
			+ " and a.od_confirm = #{confirmed} "
			+ "</when>"
			+ "<when test='barberUid != null'> "
			+ " and a.od_barber_uid = #{barberUid} "
			+ "</when>"
			+ "<when test='projectId != null'> "
			+ " and a.pj_id = #{projectId} "
			+ "</when>"
			+ "<when test='stm != null'> "
			+ " and a.crt_ts >= #{stm} "
			+ "</when>"
			+ "<when test='etm != null'> "
			+ " and a.crt_ts <= #{etm} "
			+ "</when>"
			+ "order by a.crt_ts desc limit #{startIndex}, #{size} </script>")
	List<HistoryOrder> getHistoryOrders(@Param("shopId") Long shopId, @Param("paied") Integer paied, @Param("confirmed") Integer confirmed, 
			@Param("barberUid") Long barberUid, @Param("projectId") Long projectId, @Param("stm") Long stm, @Param("etm") Long etm,
			@Param("startIndex") Integer startIndex, @Param("size") Integer size);
	
	@Select("<script> select count(1) "
			+ "from sl_order a inner join sl_shop b on a.shop_id = b.shop_id inner join sl_project c on a.pj_id = c.pj_id "
			+ "inner join sl_user u on a.od_barber_uid = u.u_id "
			+ "where b.shop_id = #{shopId} "
			+ "<when test='paied != null'> "
			+ " and a.od_paied = #{paied} "
			+ "</when>"
			+ "<when test='confirmed != null'> "
			+ " and a.od_confirm = #{confirmed} "
			+ "</when>"
			+ "<when test='barberUid != null'> "
			+ " and a.od_barber_uid = #{barberUid} "
			+ "</when>"
			+ "<when test='projectId != null'> "
			+ " and a.pj_id = #{projectId} "
			+ "</when>"
			+ "<when test='stm != null'> "
			+ " and a.crt_ts >= #{stm} "
			+ "</when>"
			+ "<when test='etm != null'> "
			+ " and a.crt_ts <= #{etm} "
			+ "</when>"
			+ "</script>")
	@ResultType(Long.class)
	Long getHistoryOrderCount(@Param("shopId") Long shopId, @Param("paied") Integer paied, @Param("confirmed") Integer confirmed, 
			@Param("barberUid") Long barberUid, @Param("projectId") Long projectId, @Param("stm") Long stm, @Param("etm") Long etm);
}
