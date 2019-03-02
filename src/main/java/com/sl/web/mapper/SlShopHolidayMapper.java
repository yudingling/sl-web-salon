package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.HolidayInfo;
import com.sl.web.model.db.SlShopHoliday;
import com.sl.web.mybatis.MyMapper;

public interface SlShopHolidayMapper extends MyMapper<SlShopHoliday> {

	@Select("<script> select a.* from sl_shop_holiday a where a.shop_id=#{shopId} "
			+ "<when test='descLike != null'> "
			+ " and a.sof_desc like #{descLike} "
			+ "</when> "
			+ " order by a.sof_stm desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<HolidayInfo> getHolidays(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("shopId") Long shopId, @Param("descLike") String descLike);
	
	@Select("<script> select count(1) from sl_shop_holiday a where a.shop_id=#{shopId} "
			+ "<when test='descLike != null'> "
			+ " and a.sof_desc like #{descLike} "
			+ "</when> "
			+ "</script>")
	@ResultType(Long.class)
	Long getHolidayCount(@Param("shopId") Long shopId, @Param("descLike") String descLike);
}
