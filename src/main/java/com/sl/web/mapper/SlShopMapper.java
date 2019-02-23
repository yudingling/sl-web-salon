package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.sl.web.model.db.SlShop;
import com.sl.web.mybatis.MyMapper;

public interface SlShopMapper extends MyMapper<SlShop> {
	
	@Select("<script> select * from sl_shop where 1=1 "
			+ "<when test='bdId != null'> "
			+ " and bd_id=#{bdId} "
			+ "</when> "
			+ "<when test='nameLike != null'> "
			+ " and shop_nm like #{nameLike} "
			+ "</when> "
			+ " order by crt_ts desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<SlShop> getShops(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("bdId") String bdId, @Param("shopName") String nameLike);
}
