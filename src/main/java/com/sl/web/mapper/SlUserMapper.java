package com.sl.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.sl.web.model.UserInfo;
import com.sl.web.model.db.SlUser;
import com.sl.web.mybatis.MyMapper;

public interface SlUserMapper extends MyMapper<SlUser> {
	
	@Select("<script> select y.u_id, y.u_nm, y.u_thirdid from sl_user_shop x inner join sl_user y on x.u_id = y.u_id and x.shop_id = #{shopId} and x.role_id ='SHOP_WORKER' "
			+ "<when test='phoneLike != null'> "
			+ " and y.u_thirdid like #{phoneLike} "
			+ "</when> "
			+ " order by y.crt_ts desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<SlUser> getShopWorkers(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("shopId") Long shopId, @Param("phoneLike") String phoneLike);
	
	@Select("<script> select count(1) from sl_user_shop x inner join sl_user y on x.u_id = y.u_id and x.shop_id = #{shopId} and x.role_id ='SHOP_WORKER' "
			+ "<when test='phoneLike != null'> "
			+ " and y.u_thirdid like #{phoneLike} "
			+ "</when> "
			+ "</script>")
	@ResultType(Long.class)
	Long getShopWorkerCount(@Param("shopId") Long shopId, @Param("phoneLike") String phoneLike);
	
	@Select("<script> select y.u_id, y.u_nm, y.u_thirdid from sl_user_barber x inner join sl_user y on x.u_id = y.u_id and x.shop_id = #{shopId} "
			+ "<when test='phoneLike != null'> "
			+ " and y.u_thirdid like #{phoneLike} "
			+ "</when> "
			+ " order by y.crt_ts desc limit #{startIndex}, #{size} "
			+ "</script>")
	List<SlUser> getShopBarbers(@Param("startIndex") Integer startIndex, @Param("size") Integer size, @Param("shopId") Long shopId, @Param("phoneLike") String phoneLike);
	
	@Select("<script> select count(1) from sl_user_barber x inner join sl_user y on x.u_id = y.u_id and x.shop_id = #{shopId} "
			+ "<when test='phoneLike != null'> "
			+ " and y.u_thirdid like #{phoneLike} "
			+ "</when> "
			+ "</script>")
	@ResultType(Long.class)
	Long getShopBarberCount(@Param("shopId") Long shopId, @Param("phoneLike") String phoneLike);
	
	@Select("select a.*, b.file_pfx, b.file_nm from sl_user a left join sl_file b on a.u_icon = b.file_id where a.u_id = #{uId}")
	UserInfo getUserInfo(@Param("uId") Long uId);
}
