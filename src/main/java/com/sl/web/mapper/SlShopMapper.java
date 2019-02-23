package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.ShopInfo;
import com.sl.web.model.db.SlShop;
import com.sl.web.mybatis.MyMapper;

public interface SlShopMapper extends MyMapper<SlShop> {
	
	@Select("<script> select a.*, y.u_thirdid as shop_user_phone from sl_shop a inner join sl_user_shop x on a.shop_id=x.shop_id and x.role_id ='SHOP' inner join sl_user y on x.u_id = y.u_id "
			+ "<when test='bdId != null'> "
			+ " and a.bd_id=#{bdId} "
			+ "</when> "
			+ "<when test='nameLike != null'> "
			+ " and a.shop_nm like #{nameLike} "
			+ "</when> "
			+ " order by a.crt_ts desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<ShopInfo> getShops(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("bdId") String bdId, @Param("nameLike") String nameLike);
	
	@Select("select a.*, y.u_thirdid as shop_user_phone from sl_shop a "
			+ "inner join sl_user_shop x on a.shop_id=x.shop_id and x.role_id ='SHOP' "
			+ "inner join sl_user y on x.u_id = y.u_id"
			+ " where a.shop_id=#{shopId}")
	ShopInfo getShop(@Param("shopId") Long shopId);
}
