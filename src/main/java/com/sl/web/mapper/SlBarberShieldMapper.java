package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.BarberShieldInfo;
import com.sl.web.model.db.SlBarberShield;
import com.sl.web.mybatis.MyMapper;

public interface SlBarberShieldMapper extends MyMapper<SlBarberShield> {
	
	@Select("<script> select count(1) from sl_barber_shield a inner join sl_user b on a.u_id = b.u_id "
			+ " inner join sl_user_barber c on a.u_id = c.u_id and c.shop_id = #{shopId}"
			+ "<when test='uId != null'> "
			+ " and a.u_id = #{uId} "
			+ "</when>"
			+ " </script>")
	@ResultType(Long.class)
	Long getShieldCount(@Param("shopId") Long shopId, @Param("uId") Long uId);
	
	@Select("<script> select a.*, b.u_nm from sl_barber_shield a inner join sl_user b on a.u_id = b.u_id "
			+ " inner join sl_user_barber c on a.u_id = c.u_id and c.shop_id = #{shopId}"
			+ "<when test='uId != null'> "
			+ " and a.u_id = #{uId} "
			+ "</when> "
			+ " order by a.bbt_etm desc limit #{startIndex}, #{size} </script>")
	List<BarberShieldInfo> getShields(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("uId") Long uId, @Param("shopId") Long shopId);
}
